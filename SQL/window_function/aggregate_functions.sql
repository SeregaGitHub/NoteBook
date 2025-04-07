
                                            Обычная группировка

SELECT category_id, category_name, AVG(unit_price)
FROM products
JOIN categories USING (category_id)
GROUP BY category_id, category_name
ORDER BY category_id;

SELECT category_id, category_name, product_name, unit_price
FROM products
JOIN categories USING (category_id)
WHERE category_id = 1;

                                              Оконные функции

-- Сравнить цену каждого продукта со средней ценой в его категории
SELECT category_id, category_name, product_name, unit_price, 
	AVG(unit_price) OVER (PARTITION BY category_id) AS avg_price
FROM products
JOIN categories USING (category_id)
WHERE category_id = 1;

-----------------------------------------------------------------------------------------------------

-- Выводит общую сумму товаров в заказе 
SELECT order_id, order_date, product_id, product_name, customer_id, order_details.unit_price,
	SUM(order_details.unit_price) OVER(PARTITION BY order_id) AS sale_sum
FROM orders
JOIN order_details USING (order_id)
JOIN products USING (product_id)
WHERE order_id = 10255
ORDER BY order_id;

-- Выводит возрастающий итог суммы товаров в заказе (благодаря "ORDER BY")
SELECT order_id, order_date, product_id, product_name, customer_id, order_details.unit_price,
	SUM(order_details.unit_price) OVER(PARTITION BY order_id ORDER BY product_id) AS sale_sum
FROM orders
JOIN order_details USING (order_id)
JOIN products USING (product_id)
WHERE order_id = 10255
ORDER BY order_id;
-----------------------------------------------------------------------------------------------------

-----------------------------------------------------------------------------------------------------
-- Выводит возрастающий итог только по заказам, но не общий (не по каждому продукту в заказах)
SELECT order_id, order_date, product_id, product_name, customer_id, order_details.unit_price,
	SUM(order_details.unit_price) OVER(ORDER BY order_id) AS sale_sum
FROM orders
JOIN order_details USING (order_id)
JOIN products USING (product_id)
ORDER BY order_id;

-- Выводит возрастающий итог по каждому продукту в заказе (row_number() - присваивает сквозной id(row_id) и по нему идёт [ORDER BY row_id])
SELECT row_id, order_id, order_date, product_id, product_name, customer_id, unit_price,
	SUM(unit_price) OVER(ORDER BY row_id) AS sale_sum
FROM (
	SELECT order_id, order_date, product_id, product_name, customer_id, order_details.unit_price,
		row_number() OVER() AS row_id
	FROM orders
	JOIN order_details USING (order_id)
	JOIN products USING (product_id)
) subquery
ORDER BY order_id;
-----------------------------------------------------------------------------------------------------