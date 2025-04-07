--1.   Создать представление, которое выводит следующие колонки:
--   order_date, required_date, shipped_date, ship_postal_code, company_name, contact_name, phone,
--   last_name, first_name, title из таблиц orders, customers и employees.

CREATE VIEW firstView AS
SELECT order_date, required_date, shipped_date, ship_postal_code, company_name, contact_name,
       phone, last_name, first_name, title  -- из таблиц orders, customers, employees
FROM orders
JOIN customers USING (customer_id)
JOIN employees USING (employee_id);

-- Сделать select к созданному представлению, выведя все записи, где order_date больше 1го января 1997 года.

SELECT *
FROM firstView
WHERE order_date > '1997-01-01';  -- 676

-- 2.   Создать представление, которое выводит следующие колонки:
--    order_date, required_date, shipped_date, ship_postal_code, ship_country, company_name, contact_name,
--    phone, last_name, first_name, title из таблиц orders, customers, employees.
--      Попробовать добавить к представлению (после его создания) колонки
--    ship_country, postal_code и reports_to. Убедиться, что проихсодит ошибка.
--      Переименовать представление и создать новое уже с дополнительными колонками.
--      Сделать к нему запрос, выбрав все записи, отсортировав их по ship_county.
--      Удалить переименованное представление

CREATE VIEW secondView AS
SELECT order_date, required_date, shipped_date, ship_postal_code, ship_country, company_name, contact_name,
       phone, last_name, first_name, title
FROM orders
JOIN customers USING (customer_id)
JOIN employees USING (employee_id);


CREATE OR REPLACE VIEW secondView AS
SELECT ship_country, postal_code, reports_to
FROM orders
JOIN customers USING (customer_id)
JOIN employees USING (employee_id);

ALTER VIEW secondView
RENAME TO secondViewNew;

CREATE VIEW secondView AS
SELECT order_date, required_date, shipped_date, ship_postal_code, ship_country, company_name, contact_name,
       phone, last_name, first_name, title, customers.postal_code, reports_to
FROM orders
JOIN customers USING (customer_id)
JOIN employees USING (employee_id);

SELECT * FROM secondView  -- 830
ORDER BY ship_country;

DROP VIEW IF EXISTS secondViewNew;

-- 3. Создать представление "активных" (discontinued = 0) продуктов, содержащее все колонки.
--    Представление должно быть защищено от вставки записей, в которых discontinued = 1.
--    Попробовать сделать вставку записи с полем discontinued = 1 - убедиться, что не проходит.

CREATE VIEW activProducts AS
SELECT * FROM products
WHERE discontinued = 0
WITH LOCAL CHECK OPTION;

SELECT * FROM activProducts
LIMIT 5;

INSERT INTO activProducts
VALUES
(1000, 'Russian Bear', 1, 2, '500 ml', 8, 20, 50, 30, 1);  -- no insert

INSERT INTO activProducts
VALUES
(1000, 'Russian Bear', 1, 2, '500 ml', 8, 20, 50, 30, 0);  -- insert-OK
