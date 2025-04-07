
PRIMARY KEY                                  -  уникальный ключ + гарантия отсутствия дубликатов в столбце (NULL - не допускается)
                                               (ОБЫЧНО - это столбец_id, с автоинкрементом !!!)
                                                 [только один столбец в таблице может быть - PRIMARY KEY]

UNIQUE                                       -  гарантия отсутствия дубликатов в столбце (NULL - допускается)
UNIQUE NOT NULL                              -  гарантия отсутствия дубликатов в столбце (NULL - не допускается)
                                                 [несколько столбцов в таблице могут быть - UNIQUE]
DEFAULT some_values                          -  если не присвоено значение, то будет присвоено, то что после DEFAULT
CHECK (some_condition)                       -  добавление условия, после CONSTRAINT
                                                (столбец заполнится данными, ТОЛЬКО если условие - true)
EXCLUDE                                      -  ???

FOREIGN KEY (column_name) REFERENCES table_name(column_name)      -  внешний ключ: указывает на какую таблицу и на какой столбец ссылается
 ON DELETE CASCADE                                                -  если дописано, то будет каскадное удаление
 ON DELETE RESTRICT                                               -  если дописано, то удаления не будет
===================================================================================================
CREATE TABLE table_name                      -  создать таблицу

ALTER TABLE table_name                       -  изменить таблицу
    ALTER COLUMN column_name                                      -  изменить столбец
    ALTER COLUMN column_name SET                                  -  назначить столбцу...
    ALTER COLUMN column_name SET DATA TYPE data_type              -  изменить тип данных столбца
    ALTER COLUMN column_name SET DEFAULT default_values           -  назначить default
    ALTER COLUMN column_name DROP DEFAULT                         -  удалить default
    ADD COLUMN column_name data_type                              -  добавить столбец
    RENAME TO new_table_name                                      -  переименовать таблицу
    RENAME old_column_name TO new_column_name                     -  переименовать столбец
    ADD PRIMARY KEY column_name                                   -  назначить столбцу PRIMARY KEY
    DROP COLUMN column_name                                       -  удалить столбец
    DROP CONSTRAINT constraint_name                               -  удалит ограничение
    ADD CONSTRAINT constraint_name constraint_type (column_name)  -  назначить столбцу ограничение

        CONSTRAINT PK_book_book_id PRIMARY KEY (book_id),
        CONSTRAINT FK_books_publisher FOREIGN KEY (publisher_id) REFERENCES publisher(publisher_id)
        CONSTRAINT CHK_book_price CHECK (price >= 0)
        CONSTRAINT CHK_book_status CHECK (status = 'r' OR status = 'p')

DROP TABLE table_name                        -  удалить таблицу
DROP TABLE IF EXISTS table_name              -  удалить таблицу, если существует
TRUNCATE TABLE table_name                    -  удалить все данные из таблицы (кроме связанных данных)
                                                Не оставит логов данных !!!
                                                Не удалит IDENTITY(отсчёт строк[самый левый столбец])
TRUNCATE TABLE table_name RESTART IDENTITY   -  удалить все данные из таблицы (кроме связанных данных)
                                                отсчёт IDENTITY, начнётся с начала !!!

DELETE FROM "table_name";                    -  начать автоинкрементацию заново
ALTER TABLE "table_name"
  ALTER COLUMN "column_name" RESTART WITH 1;
===================================================================================================
CREATE TABLE table_name                      -  конструктор создания таблицы с ограничением
(
  column_name data_type,
  column_name data_type UNIQUE NOT NULL,     -  добавить значениям столбца уникальность
  column_name data_type DEFAULT some_values, -  если не присвоено значение, то будет присвоено, то что после DEFAULT

  CONSTRAINT constraint_name constraint_type (column_name)

)
---------------------------------------------------------------------------------------------------
CREATE TABLE publisher                       -  создаём таблицу publisher с PRIMARY KEY (publisher_id)
(
	publisher_id serial,
	publisher_name varchar(128) NOT NULL,
	adress text,

	CONSTRAINT PK_publisher_publisher_id PRIMARY KEY (publisher_id)

  -- constraint GENRES_PK primary key (GENRE_ID)  -  пример
);

CREATE TABLE book                            -  создаём таблицу book с PRIMARY KEY (book_id) и FOREIGN KEY (publisher_id),
(                                               который ссылается, REFERENCES, на таблицу со столбцом - publisher(publisher_id)
	book_id serial,
	title text NOT NULL,
	isbn text NOT NULL,
	publisher_id int,

	CONSTRAINT PK_book_book_id PRIMARY KEY (book_id),
	CONSTRAINT FK_books_publisher FOREIGN KEY (publisher_id) REFERENCES publisher(publisher_id)
);
