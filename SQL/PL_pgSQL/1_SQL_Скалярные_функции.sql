    
    language_type:
        SQL-функции
        PL\pgSQL - процедурные
        C - серверные (написанные на C)
        Собственные C-функции

    arg:
        IN            - входящие аргументы (по умолчанию)
        OUT           - исхлдящие аргументы
        INOUT         - и входящий и исходящий аргумент (не рекомендуется)
        VARIADIC      - массив входящих параметров
        DEFAULT value - значение аргумента по умолчанию
------------------------------------------------------------------------------------------------------

CREATE FUNCTION function_name(arg1, arg2, ...) RETURNS data_type AS $$       -- function_name()  -  если скобки пустые, значит функция не принимает аргументы
                                                                             -- data_type        -  указывать тип возвращаемых данных или void
	-- logic
$$ language language_type

CREATE OR REPLACE FUNCTION --...                                             -- Создать или заменить функцию

DROP FUNCTION IF EXISTS function_name(arg1, arg2, ...);                                -- Удалить функцию
--=============================================================================================================================================

SELECT * 
FROM customers;

SELECT * 
INTO tmp_customers
FROM customers;

SELECT * 
FROM tmp_customers;

CREATE OR REPLACE FUNCTION fix_customers_region() RETURNS void AS $$                             -- Создание функции без параметров и void
	UPDATE tmp_customers
	SET region = 'unknown'
	WHERE region IS NULL
$$ language SQL;

SELECT fix_customers_region();                                                                   -- Вызов функции без параметров 


CREATE OR REPLACE FUNCTION get_total_number_of_goods() RETURNS bigint AS $$                      -- Создание функции без параметров и с типом данных bigint
	SELECT SUM(units_in_stock)
	FROM products
$$ language SQL;

SELECT get_total_number_of_goods() AS total_goods;                                               -- Вызов функции с алиасом
------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION get_product_price_by_name(prod_name varchar) RETURNS real AS $$       -- Создание функции с параметрами (IN)
	SELECT unit_price
	FROM products
	WHERE product_name = prod_name
$$ language SQL;

SELECT get_product_price_by_name('Chocolade') AS product_price;                                  -- Вызов функции с параметрами
------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION get_price_boundaries(OUT max_price real, OUT min_price real) AS $$    -- Создание функции с параметрами (OUT)
    -- Важно соблюдать последовательность аргументов: в параметрах и селекте !!!
	SELECT MAX(unit_price), MIN(unit_price)
	FROM products
$$ LANGUAGE SQL;

SELECT get_price_boundaries() AS price_boundaries;         -- Ответ в одном столбце, через запятую
SELECT * FROM get_price_boundaries() AS price_boundaries;  -- Ответ двух столбцах
------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION get_price_boundaries_by_discontinuity(                                -- Создание функции с параметрами (IN и OUT)
	                is_discontinued int, OUT max_price real, OUT min_price real) AS $$
	SELECT MAX(unit_price), MIN(unit_price)
	FROM products
	WHERE discontinued = is_discontinued
$$ LANGUAGE SQL;

SELECT * FROM get_price_boundaries_by_discontinuity(1) AS price_boundaries;
------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION get_price_boundaries_by_discontinuity(                                -- Создание функции с дефолтным аргументом
	        is_discontinued int DEFAULT 1, OUT max_price real, OUT min_price real) AS $$
	SELECT MAX(unit_price), MIN(unit_price)
	FROM products
	WHERE discontinued = is_discontinued
$$ LANGUAGE SQL;

SELECT * FROM get_price_boundaries_by_discontinuity() AS price_boundaries;

--=============================================================================================================================================

