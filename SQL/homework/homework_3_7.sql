

-- 1. Найти заказчиков и обслуживающих их заказы сотрудников таких,
--    что и заказчики и сотрудники из города London, а доставка идёт компанией Speedy Express.
--    Вывести компанию заказчика и ФИО сотрудника.

-- JOIN employees ON employees.employee_id = orders.employee_id
-- JOIN customers ON customers.customer_id = orders.customer_id

SELECT customers.company_name, CONCAT (employees.last_name, ' ', employees.first_name) AS employees_name
FROM orders
JOIN employees USING (employee_id)
JOIN customers USING (customer_id)
WHERE customers.city = 'London' AND employees.city = 'London'
GROUP BY customers.company_name, employees.last_name, employees.first_name, orders.ship_via
HAVING orders.ship_via = 1;


-- 2. Найти активные (см. поле discontinued) продукты из категории Beverages и Seafood,
--    которых в продаже менее 20 единиц. Вывести наименование продуктов, кол-во единиц в продаже,
--    имя контакта поставщика и его телефонный номер.

-- SELECT products.product_name, products.units_in_stock, suppliers.contact_name, suppliers.phone
-- FROM products
-- JOIN suppliers USING (supplier_id)
-- WHERE category_id = 1 OR category_id = 8 AND products.units_in_stock < 20
-- GROUP BY products.product_name, products.units_in_stock, suppliers.contact_name, suppliers.phone, products.discontinued
-- HAVING discontinued = 0;

-- Если не поставить () - WHERE (category_id = 1 OR category_id = 8) AND products.units_in_stock < 20
-- Запрос - НЕ СРАБОТАЕТ ПРАВИЛЬНО !!!

SELECT products.product_name, products.units_in_stock, suppliers.contact_name, suppliers.phone
FROM products
JOIN suppliers USING (supplier_id)
WHERE category_id = 1 OR category_id = 8
GROUP BY products.product_name, products.units_in_stock, suppliers.contact_name, suppliers.phone, products.discontinued
HAVING discontinued = 0 AND products.units_in_stock < 20;

SELECT products.product_name, products.units_in_stock, suppliers.contact_name, suppliers.phone
FROM products
JOIN categories USING (category_id)
JOIN suppliers USING (supplier_id)
WHERE category_name IN ('Beverages', 'Seafood') AND discontinued = 0 AND products.units_in_stock < 20;

-- 3. Найти заказчиков, не сделавших ни одного заказа. Вывести имя заказчика и order_id.
SELECT customers.contact_name, orders.order_id
FROM customers
LEFT JOIN orders USING (customer_id)
WHERE orders.order_id IS NULL

-- 4. Переписать предыдущий запрос, использовав симметричный вид джойна (подсказка: речь о LEFT и RIGHT).
SELECT customers.contact_name, orders.order_id
FROM orders
RIGHT JOIN customers USING (customer_id)
WHERE orders.order_id IS NULL
