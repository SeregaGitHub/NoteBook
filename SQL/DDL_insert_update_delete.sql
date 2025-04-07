
INSERT INTO table_name                                  -  вставка значений во все столбцы таблицы
VALUES (..., ..., ...)

INSERT INTO table_name (column_name, column_name)       -  вставка значений в конкретные столбцы таблицы
VALUES
(values_, values_),
(values_, values_),
(values_, values_)

SELECT * INTO new_table_name                            -  создание новой таблицы и занесение в неё
FROM table_name                                            отфильтрованных данных из старой
WHERE some_filters

INSERT INTO new_table_name                              -  добавление отфильтрованных данных
SELECT * FROM table_name                                   из старой таблицы в существующую новую
WHERE some_filters

---------------------------------------------------------------------------------------------------------------------------------------

INSERT INTO products (id, name, price, category)        -  При добавлении данных можно с помощью инструкции ON CONFLICT указать,
VALUES (1, 'Шампунь 2', 1200, 'косметика')                 что делать в случае возникновения конфликта.
ON CONFLICT DO NOTHING;

INSERT INTO clients (id, name, phone, email)            -  После предложения ON CONFLICT можно написать DO UPDATE.
VALUES (9, 'Анна Васильевна Орешкина'                      Это гарантирует, что либо новое значение будет добавлено в таблицу,
        , '805565423422', 'anna@mail.com')                то есть выполнится команда INSERT, либо значение в таблице будет обновлено.
ON CONFLICT (id) DO UPDATE SET name = EXCLUDED.name        Эта операция также известна как UPSERT — «UPDATE или INSERT».
    , phone = EXCLUDED.phone, email = EXCLUDED.email;      (Для обращения к значениям, изначально предлагаемым для добавления,
                                                            используется специальная таблица excluded)

INSERT INTO user_filmorate (user_id, user_email, user_login, user_name, user_birthday)          -  без специальной таблицы.
VALUES (2, 'sanya.zubov@yandex.ru', 'SASHA', 'Sanya', '1980-01-01')
ON CONFLICT (user_id) DO UPDATE SET user_id = 2, user_email = 'sanya.zubov@yandex.ru',
                      user_login = 'SASHA', user_name = 'Sanya', user_birthday = '1980-01-01';

Более подробно ознакомиться с предложением ON CONFLICT можно здесь:
https://postgrespro.ru/docs/postgrespro/12/sql-insert

---------------------------------------------------------------------------------------------------------------------------------------

                                        -- Подзапросы внутри INSERT

-- Создание новой таблицы и заполнение её данными из других таблиц может помочь ускорить работу с базой

CREATE TABLE old_orders (
    id integer PRIMARY KEY,
    client_id integer NOT NULL REFERENCES clients(id),
    date date NOT NULL,
    status character varying(100) NOT NULL CHECK (status IN ('done', 'in progress', 'delivery')),
    address character varying(100) NOT NULL
);

INSERT INTO old_orders (id, client_id, date, status, address)
SELECT id, client_id, date, status, address
FROM orders
WHERE date < '2020-01-01';
                            -- ИЛИ
INSERT INTO old_orders SELECT * FROM orders WHERE date < '2020-01-01';

--  Если таблицы не идентичны, как вышло у нас, можно перечислить названия столбцов, в которые нужно добавить значения.
--Количество и типы столбцов в таблице, которую возвращает запрос, должны совпадать с количеством и типами перечисленных столбцов
---------------------------------------------------------------------------------------------------------------------------------------

UPDATE table_name                                       -  изменение значений в таблице
SET column_name = new_values, column_name = new_values
WHERE column_name(обычно id) = column_values


---------------------------------------------------------------------------------------------------------------------------------------

                                                      -- INSERT если конфликт => UPDATE 
-- Добавление лайка
INSERT INTO likes (count, post_id)
VALUES
(1, 4)
ON CONFLICT (post_id) DO UPDATE
SET count = likes.count + 1
WHERE likes.post_id = 4;

---------------------------------------------------------------------------------------------------------------------------------------

/*
      Если вы используете предложение FROM, целевая строка не должна соединяться с более чем одной строкой из других таблиц.
    При нарушении этого условия только одна из строк соединения будет использоваться для изменения целевой строки,
    но какая именно — предсказать нельзя. По этой причине лучше использовать подзапросы,
    хотя они часто хуже читаются и работают медленнее, чем соединение таблиц через FROM.
*/

UPDATE positions
SET amount = amount + 1
WHERE order_id IN (
  SELECT id
  FROM orders
  WHERE orders.client_id = (SELECT id FROM clients WHERE name = 'Бук Василий Петрович')
);
                              --ИЛИ
UPDATE positions
SET amount = amount + 1
FROM orders
WHERE positions.order_id = orders.id
  AND orders.client_id = (
      SELECT id
      FROM clients
      WHERE name = 'Бук Василий Петрович'
  );
---------------------------------------------------------------------------------------------------------------------------------------

DELETE FROM table_name                                  -  удаление строки из таблицы
WHERE column_name = column_values                          (помимо = можно >, <, <=, IS NULL и т.д.)

DELETE FROM table_name                                  - удаление всех строк (логи остаются)

INSERT INTO table_name                                  - добавит строку и выведет на консоль id строки
VALUES
(column_values, column_values)
RETURNING column_id
          *  -  выведет на консоль всю строку

DELETE FROM table_name                                  - удалит строку и выведет на консоль удалённую строку
WHERE column_name = column_values
RETURNING *
====================================================================================================

CREATE TABLE IF NOT EXISTS authors
(
	author_id int PRIMARY KEY,
	full_name text NOT NULL,
	raiting real
);

INSERT INTO authors
VALUES (1, 'John Silver', 4.7);

SELECT * FROM authors

INSERT INTO authors (author_id, full_name)
VALUES (2, 'Bob Gray')

INSERT INTO authors (author_id, full_name)
VALUES
(3, 'Aleksandr Dyma'),
(4, 'Rafael Sabatiny'),
(5, 'Jak London')

SELECT * INTO best_authors
FROM authors
WHERE raiting >= 4.5 OR author_id = 3

SELECT * FROM best_authors

INSERT INTO best_authors
SELECT * FROM authors
WHERE authors.author_id = 2

---------------------------------------------------------------------------------------------------

-- Вставить значение в таблицу, если его там нет. Если есть - ничего !!!

                  -- ЛУЧШЕ

MERGE INTO "table_name" KEY ("column_name(id)") VALUES (1, 'some_value');
MERGE INTO "table_name" KEY ("column_name(id)") VALUES (2, 'some_value');

MERGE INTO "genre" KEY ("genre_id") VALUES (1, 'Комедия');
MERGE INTO "genre" KEY ("genre_id") VALUES (2, 'Драма');


                  -- ХУЖЕ

INSERT INTO "table_name" ("column_name")
SELECT 'value_name' FROM dual WHERE NOT EXISTS (SELECT 1 FROM "table_name" WHERE "column_name" = 'value_name')
                                    -- оба value_name - одинаковые !!!

INSERT INTO "rating" ("rating_name")
SELECT 'PG' FROM dual WHERE NOT EXISTS (SELECT 1 FROM "rating" WHERE "rating_name" = 'PG')

-- Несколько значений !!!
INSERT INTO "rating" ("rating_name")
SELECT 'PG' FROM dual WHERE NOT EXISTS (SELECT 1 FROM "rating" WHERE "rating_name" = 'PG')
UNION
SELECT 'PG-13' FROM dual WHERE NOT EXISTS (SELECT 1 FROM "rating" WHERE "rating_name" = 'PG-13')
UNION
SELECT 'G' FROM dual WHERE NOT EXISTS (SELECT 1 FROM "rating" WHERE "rating_name" = 'G')

---------------------------------------------------------------------------------------------------

SELECT * FROM authors

UPDATE authors
SET full_name = 'Jack London'
WHERE authors.author_id = 5

UPDATE authors
SET raiting = 4.4
WHERE authors.author_id = 5

DELETE FROM authors
WHERE raiting IS NULL

DELETE FROM authors
WHERE raiting < 4.0

DELETE FROM authors
WHERE author_id = 1

DELETE FROM authors            -- удаление всех строк (остаются логи)

INSERT INTO authors            -- добавит строку и выведет на консоль id строки
VALUES
(2, 'Edgar Berrous', 4.2)
RETURNING author_id

INSERT INTO authors            -- добавит строку и выведет на консоль всю строку
VALUES
(1, 'Aleksandr Pushkin', 5.0)
RETURNING *

DELETE FROM authors            -- удалит строку и выведет на консоль удалённую строку
WHERE full_name = 'Uncknoun'
RETURNING *
