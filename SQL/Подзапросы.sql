                                  ПОДЗАПРОСЫ

    (Подсказка: проще вначале написать подзапрос, а потом добавить его в запрос !!!)

WHERE EXISTS (подзапрос) -  буленский тип, возвращает true/false из подзапроса
WHERE NOT EXISTS
ANY                      -  какой либо, WHERE customer_id = ANY (SELECT ...)
                            используется между конкретным столбцом и подзапросом !!!

ALL                      -  больше всех значений из подзапроса
                            WHERE quantity > ALL (SELECT)
---------------------------------------------------------------------------------------

-- показать названия компаний-поставщиков, где страны одинаковые со странами клиентов
SELECT company_name
FROM suppliers
WHERE country IN (SELECT DISTINCT country
                  FROM customers);

-- ТО ЖЕ САМОЕ !!!

    SELECT DISTINCT suppliers.company_name
    FROM suppliers
    JOIN customers USING (country);
--------------------------------------------------------------------------------------
-- подзапрос в LIMIT
SELECT category_name, SUM(units_in_stock)
FROM products
JOIN categories USING(category_id)
GROUP BY category_name
ORDER BY SUM(units_in_stock) DESC
LIMIT (SELECT MIN(product_id) + 4 FROM products)
--------------------------------------------------------------------------------------
-- вычислить товары и их кол-во, которых на складе больше чем в среднем

SELECT product_name, units_in_stock
FROM products
WHERE units_in_stock > (SELECT AVG(units_in_stock)
                        FROM products)
ORDER BY units_in_stock DESC
-------------------------------------------------------------------------------------
SELECT company_name, contact_name
FROM customers
WHERE EXISTS (SELECT customer_id FROM orders
			        WHERE customer_id = customers.customer_id
			        AND freight BETWEEN 50 AND 100)
-------------------------------------------------------------------------------------
-- выбрать компании и имена заказчиков
-- которые НЕ делали заказы между 1 и 15 февраля 1995

SELECT customers.company_name, customers.contact_name
FROM customers
WHERE NOT EXISTS (SELECT orders.order_date FROM orders
			 WHERE customers.customer_id = orders.customer_id
			 AND order_date BETWEEN '1995-02-01' AND '1995-02-15')
-------------------------------------------------------------------------------------
-- выбрать продукты, которые не покупались между 1 и 15 февраля 1995
SELECT product_name
FROM products
WHERE NOT EXISTS (SELECT orders.order_id FROM orders                    -- выбираем заказ
			        	 JOIN order_details USING (order_id)                    -- соединённый по order_id с деталями заказа
			        	 WHERE order_details.product_id = product_id            -- где в деталях заказа product_id равны
			        	 AND order_date BETWEEN '1995-02-01' AND '1995-02-15')  -- и фильтруем по дате заказа
-----------------------------------------------------------------------------------
-- выбрать все уникальные компании, которые делали заказы на более 40 единиц товаров

SELECT DISTINCT customers.company_name
FROM customers
JOIN orders ON orders.customer_id = customers.customer_id
JOIN order_details ON order_details.order_id = orders.order_id
WHERE order_details.quantity > 40;

SELECT DISTINCT customers.company_name
FROM customers
WHERE customer_id = ANY (
	         SELECT orders.customer_id FROM orders
			     JOIN order_details USING (order_id)
		     	 WHERE order_details.quantity > 40);
-------------------------------------------------------------------------------------
-- выбрать продукты количество которых больше среднего по заказам

SELECT AVG(quantity)
FROM order_details     -- 23.8

SELECT DISTINCT product_name, quantity  -- без quantity
FROM products
JOIN order_details USING (product_id)
WHERE quantity > (
		SELECT AVG(quantity)
		FROM order_details
)
ORDER BY product_name
-------------------------------------------------------------------------------------
--     Выбрать все продукты количество которых, больше среднего значения,
--  количества заказаных товаров из групп, полученных группированием по product_id

SELECT AVG(quantity)
FROM order_details      --     среднее значение, количества заказаных товаров из групп,
GROUP BY product_id     --  полученных группированием по product_id


SELECT DISTINCT product_name, quantity
FROM products
JOIN order_details USING (product_id)
WHERE quantity > ALL (
		SELECT AVG(quantity)
		FROM order_details
		GROUP BY product_id
)
--------------------------------------------------------------------------------------
