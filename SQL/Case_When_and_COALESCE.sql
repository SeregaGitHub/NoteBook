SELECT all_needed_column_name,
CASE                                -  если
    WHEN condition_1 THEN result_1  -  condition_1 - true -> выполняется result_1
    WHEN condition_2 THEN result_2  -  condition_1 - false -> проверяется condition_2
    ..............................  -  все condition - false -> выполняется else_result
    ELSE else_result
END AS column_nickname
FROM table_name
-----------------------------------------------------------------------------------------

			COALESCE - обычно используется, чтобы подставить значение вместо null


COALESCE (arg1, arg2,...)           -  вернёт первый аргумент, который не null,
                                       (если все аргументы null - вернёт null)
                                       (часто используется, чтобы заполнить null)

COALESCE(SUM(int_column_name), 0)   - вернёт сумму колонки или 0 если там NULL

!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

COALESCE работает ТОЛЬКО со строками и символами, поэтому если нужн работать с цифпами:

COALESCE(TO_CHAR(NULLIF(arg1, arg2), 'FM99999999'), coalesce_arg2)
          -- coalesce_arg1  формат: число в виде стрроки  coalesce_arg2

!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

		NULLIF - обычно используется вместе с COALESCE, чтобы в ответе заменить значение на другое


NULLIF (arg1, arg2)                 -  сравнивает arg1 и arg2: если равны вернёт null,
                                       если не равны: вернёт arg1

=========================================================================================

SELECT product_name, unit_price, units_in_stock,
	CASE
	     WHEN units_in_stock >= 100 THEN 'quite a lot'
	     WHEN units_in_stock >= 50 AND units_in_stock < 100 THEN 'enough'
		 ELSE 'not enough'
	END AS amount
FROM products;

SELECT order_id, order_date,
	CASE WHEN date_part('month', order_date) BETWEEN 3 AND 5 THEN 'spring'
	     WHEN date_part('month', order_date) BETWEEN 6 AND 8 THEN 'summer'
	     WHEN date_part('month', order_date) BETWEEN 9 AND 11 THEN 'autumn'
	ELSE 'winter'
END AS season
FROM orders;

SELECT product_name, unit_price,
	CASE
	    WHEN unit_price >= 30 THEN 'expensive'
		ELSE 'inexpensive'
	END AS price_description
FROM products;
------------------------------------------------------------------------------------------

SELECT * FROM orders
LIMIT 10              -- В столбце ship_region есть значения null

SELECT order_id, order_date, COALESCE (ship_region, 'Unknown') AS ship_region
FROM orders           -- Лучше прописать псевдоним, иначе название столбца будет - coalesce
LIMIT 10              -- В ответе на селект, встолбце ship_region теперь вместо null - Unknown
                      -- В самой базе всё без изменений

SELECT * FROM customers

SELECT contact_name, COALESCE (NULLIF(city, 'Paris'), 'Парижанин') AS city
                     -- Если city=Paris вернёт null. Потом COALESCE заменит null на Парижанин
FROM customers
WHERE city LIKE 'Paris'
