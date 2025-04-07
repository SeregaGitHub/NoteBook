
RETURNS SETOF data_type                      -  возврат n-значений data_type
RETURNS SETOF table                          -  возврат всех столбцов из таблицы или пользовательского типа
RETURNS SETOF record                         -  используется, когда типы колонок в результирующим наборе заранее неизвестны
RETURNS TABLE (column_name, data_type, ...)  -  тоже что и RETURNS SETOF table с явно указанными возвращаемыми столбцами

--==============================================================================================================================

CREATE OR REPLACE FUNCTION get_average_prices_by_prod_categories() RETURNS SETOF double precision AS $$
	SELECT AVG(unit_price)
	FROM products
	GROUP BY category_id
$$ language SQL;

SELECT * FROM get_average_prices_by_prod_categories() AS average_prices;

-------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION get_sum_and_average_prices_by_prod_categories(OUT sum_price real, OUT avg_price float8) 
	RETURNS SETOF RECORD AS $$
		SELECT SUM(unit_price), AVG(unit_price)
		FROM products
		GROUP BY category_id
$$ language SQL;

SELECT sum_price FROM get_sum_and_average_prices_by_prod_categories() AS sum_prices;      -- можно выбирать только часть селекта !!!
SELECT * FROM get_sum_and_average_prices_by_prod_categories() AS sum_and_average_prices;



CREATE OR REPLACE FUNCTION get_sum_and_average_prices_by_prod_categories_2()              -- если не указать OUT в при создании функции,  
	RETURNS SETOF RECORD AS $$                                                          -- его придётся указывать в селете !!!
		SELECT SUM(unit_price), AVG(unit_price)
		FROM products
		GROUP BY category_id
$$ language SQL;

SELECT * FROM get_sum_and_average_prices_by_prod_categories_2() AS (sum_price real, avg_price float8);  -- !!!
-------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------

-------------------------------------------------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION get_customers_by_country(customer_country varchar) 
	RETURNS TABLE (char_code char, company_name varchar) AS $$
		SELECT customer_id, company_name
		FROM customers
		WHERE country = customer_country
$$ language SQL;

SELECT * FROM get_customers_by_country('France');
-------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION get_customers_by_country_2(customer_country varchar) 
	RETURNS SETOF customers AS $$
		-- if SETOF, will not work: SELECT customer_id, company_name
		SELECT *
		FROM customers
		WHERE country = customer_country
$$ language SQL;

SELECT * FROM get_customers_by_country_2('France');
-------------------------------------------------------------------------------------------------------------------------------
