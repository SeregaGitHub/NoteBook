                      JOIN

INNER JOIN             -  внутреннее соединение
      JOIN
LEFT JOIN, RIGHT JOIN  -  левое и правое соединение
FULL JOIN              -  полное соединение (и LEFT JOIN, и RIGHT JOIN)
CROSS JOIN             -  декартовое (все строки левые, со всеми правыми) соединение (полное)
SELF JOIN              -  соединение на саму себя (если есть внешний ключ, ссылающийся на столбец в ЭТОЙ ЖЕ таблицы)
                          (пишется через LEFT JOIN !!!)

--------------------------------------------------------------------------------------

SELECT product_name, suppliers.company_name, units_in_stock
FROM products
INNER JOIN suppliers ON products.supplier_id = suppliers.supplier_id
ORDER BY units_in_stock DESC

  Выбрать 3 столбца - из РАЗНЫХ таблиц
  (Перед столбцом можно прописать имя таблицы)
  FROM - левая таблица
  INNER JOIN - правая таблиица, ON - далее по какому столбцу идёт соединение
------------------------------------------------------------------------------------
-- Сколько единиц товара в продаже, по категориям товаров
SELECT category_name, SUM(units_in_stock)
FROM categories
INNER JOIN products ON categories.category_id = products.category_id
GROUP BY category_name
ORDER BY SUM(units_in_stock) DESC

SELECT category_name, SUM(unit_price * units_in_stock)
FROM categories
INNER JOIN products ON categories.category_id = products.category_id
WHERE discontinued != 1
GROUP BY category_name
HAVING SUM(unit_price * units_in_stock) > 5000
ORDER BY SUM(unit_price * units_in_stock) DESC
-----------------------------------------------------------------------------------
-- дата заказа, какой товар, в какую страну доставить, цену, количество и скидку
SELECT order_date, product_name, ship_country, products.unit_price, quantity, discount
FROM orders
INNER JOIN order_details ON orders.order_id = order_details.order_id
INNER JOIN products ON order_details.product_id = products.product_id

  Выбрать столбцы
из таблицы_1
соединив с таблицей_2 по столбцам таблица_1___ = таблица_2___
соединив с таблицей_3 по столбцам таблица_2___ = таблица_3___
-----------------------------------------------------------------------------------
-- выбрать компании, у которых нет заказов (LEFT JOIN - ответ - 2)
SELECT customers.company_name, orders.order_id
FROM customers
LEFT JOIN orders ON customers.customer_id = orders.customer_id
WHERE order_id IS NULL
-- то же самое (RIGHT JOIN - ответ - 2)
SELECT customers.company_name, orders.order_id
FROM orders
RIGHT JOIN customers ON customers.customer_id = orders.customer_id
WHERE order_id IS NULL;

-- выбрать работников , у которых нет заказов (таких нет - 0)
SELECT COUNT (*)
FROM employees
LEFT JOIN orders ON orders.employee_id = employees.employee_id
WHERE orders.order_id IS NULL;
----------------------------------------------------------------------------------
-- выбрать компании, у которых нет заказов и заказы без компании (как и LEFT JOIN - ответ - 2)
SELECT customers.company_name, orders.order_id
FROM customers
FULL JOIN orders ON customers.customer_id = orders.customer_id
WHERE order_id IS NULL
----------------------------------------------------------------------------------
SELECT company_name, product_name
FROM suppliers
CROSS JOIN products   (ON - не нужен !!!)
------------------------------------------------------------------------------------
-- НЕ ИЗ northwind !!!
-- выдаст всех employee и их manager из этой же таблицы
SELECT e.first_name || ' ' || e.last_name AS employee,
       m.first_name || ' ' || m.last_name AS manager
FROM employee e
LEFT JOIN employee m ON m.employe_id = e.manager_id
ORDER BY manager
-------------------------------------------------------------------------------------
    staff              SELF JOIN

PK  employee_id --|
    first_name    |
    last_name     |
    title         |
FK  reports_to  --|

SELECT e.last_name AS employee_last_name, m.last_name AS manager_last_name
FROM staff m
LEFT JOIN staff e ON e.employee_id = m.reports_to
-------------------------------------------------------------------------------------
-- Можно делать JOIN на себя без внешних ключей по первичному ключу

SELECT s.machine_id, ROUND(AVG(e.timestamp - s.timestamp), 3) AS processing_time
FROM Activity s
JOIN Activity e ON e.machine_id = s.machine_id AND s.process_id = e.process_id
AND e.activity_type = 'end' AND s.activity_type = 'start'
GROUP BY s.machine_id