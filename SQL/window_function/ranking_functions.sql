
* ROW_NUMBER  - присваивание уникального значения строкам (вымышленный id)  
* RANK        - присваивание ранга строкам с пропусками
* DENSE_RANK  - присваивание ранга строкам без пропусков
* LAG         - присваивание значения текущей строке, основанное на значении в предыдущей
* LEAD        - присваивание значения текущей строке, основанное на значении в следующей
* offset      - смещение, передаваемое в LAG и LEAD

=========================================================================================================

SELECT * FROM products
--ORDER BY unit_price DESC
LIMIT 5;

-----------------------------------------------------------------------------------------------------
SELECT product_id, product_name, units_in_stock,
	ROW_NUMBER() OVER(ORDER BY product_id)
FROM products
LIMIT 5;

-- В данном случаи RANK() сработает так же как ROW_NUMBER() т.к. даёт ранг по (ORDER BY product_id)
SELECT product_id, product_name, units_in_stock,
	RANK() OVER(ORDER BY product_id)
FROM products
LIMIT 5;

-- В данном случаи RANK() будет одинаковый т.к. (ORDER BY product_id) или другой указатель отсутствует
SELECT product_id, product_name, units_in_stock,
	RANK() OVER()
FROM products
LIMIT 5;

-- Ранг будет присвоен с промежутками (если у первых пяти ранг=1, то у следующего ранг=6 !!!)
SELECT product_id, product_name, units_in_stock,
	RANK() OVER(ORDER BY units_in_stock)
FROM products
LIMIT 10;

-- Ранг будет присвоен без промежутков (если у первых пяти ранг=1, то у следующего ранг=2 !!!)
SELECT product_id, product_name, units_in_stock,
	DENSE_RANK() OVER(ORDER BY units_in_stock)
FROM products
LIMIT 10;
-----------------------------------------------------------------------------------------------------

-- Задать ранг продуктам по дипозону цен (80 < price < 50)
SELECT product_id, product_name, units_in_stock, unit_price,
	DENSE_RANK() OVER(ORDER BY 
		CASE 
			WHEN unit_price > 80 THEN 1
			WHEN unit_price > 50 AND unit_price <= 80 THEN 2
			WHEN unit_price <= 50 THEN 3
		END
			)
		    AS product_rank
FROM products
ORDER BY unit_price DESC
LIMIT 10;

-- Выбрать продукты с высоким рангом
SELECT * FROM (
	SELECT product_id, product_name, units_in_stock, unit_price,
		DENSE_RANK() OVER(ORDER BY 
			CASE 
				WHEN unit_price > 80 THEN 1
				WHEN unit_price > 50 AND unit_price <= 80 THEN 2
				WHEN unit_price <= 50 THEN 3
			END
				) AS product_rank
	FROM products
	ORDER BY unit_price DESC
) AS pr
WHERE product_rank < 3;

-----------------------------------------------------------------------------------------------------

-- Просмотреть предыдущие значения цены товаров
SELECT product_name, unit_price,
	LAG(unit_price) OVER(ORDER BY unit_price DESC) AS price_lag
FROM products
ORDER BY unit_price DESC;

-- Просмотреть отставание цены товаров
SELECT product_name, unit_price,
	LAG(unit_price) OVER(ORDER BY unit_price DESC) - unit_price AS price_lag
FROM products
ORDER BY unit_price DESC;

-- Просмотреть увеличение цены товаров
SELECT product_name, unit_price,
	LEAD(unit_price) OVER(ORDER BY unit_price) - unit_price AS price_lag
FROM products
ORDER BY unit_price;

-- Просмотреть увеличение цены товаров через 2 (offset)
SELECT product_name, unit_price,
	LEAD(unit_price, 2) OVER(ORDER BY unit_price) - unit_price AS price_lag
FROM products
ORDER BY unit_price;
