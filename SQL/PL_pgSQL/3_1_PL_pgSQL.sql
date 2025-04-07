
        Отличия от функций sql:

    - Создание переменных
    - Прогон циклов и развитой логики(исключения и т.п.)
    - Возврат значений через RETURN -- так же после пустого RETURN; происходит принудительный выход из функции (например в if, else)
    - Возврат множественных значений через RETURN QUERY

-------------------------------------------------------------------------------------------------------------------------------
      -- Оператор := и = , при присвоении (result := x + y;) это одно и то же !!!
    --  Многие используют := , чтобы отличалось от операции сравнения, которая всегда через = !!!
-------------------------------------------------------------------------------------------------------------------------------

CREATE FUNCTION function_name(arg1, arg2, ...) RETURNS data_type AS $$       -- function_name()  -  если скобки пустые, значит функция не принимает аргументы
                                                                             -- data_type        -  указывать тип возвращаемых данных или void
DECLARE                                                                      
    -- variable type;                                                        -- Объявление переменных (Необязательно: функция может работать и без переменных)
    -- ...

BEGIN
	-- logic
    ;
END;
$$ language plpgsql;  --(language_type: plpgsql)

--=============================================================================================================================================

CREATE OR REPLACE FUNCTION get_total_number_of_goods() RETURNS bigint AS $$
BEGIN

	RETURN SUM(units_in_stock)
	FROM products;
	
END;
$$ language plpgsql;

SELECT * FROM get_total_number_of_goods() AS total_number_of_goods;
-------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION get_max_price_boundaries(OUT max_price real) AS $$
BEGIN

	max_price := MAX(unit_price) FROM products;
	
END;
$$ language plpgsql;

SELECT * FROM get_max_price_boundaries() AS price_boundaries;
-------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION get_price_boundaries(OUT max_price real, OUT min_price real) AS $$
BEGIN

	-- Плохой вариант: т.к. будет два запроса !!!
	-- max_price := MAX(unit_price) FROM products;
	-- min_price := MIN(unit_price) FROM products;
	
	SELECT MAX(unit_price), MIN(unit_price)
	INTO max_price, min_price
	FROM products;
	
END;
$$ language plpgsql;

SELECT * FROM get_price_boundaries() AS price_boundaries;
-------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION get_sum(x int, y int, OUT result int) AS $$ -- IN и OUT параметры
BEGIN

	result := x + y;
	
END;
$$ language plpgsql;

SELECT * FROM get_sum(2, 3);
-------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION get_customers_by_country_3(customer_country varchar)   -- RETURN QUERY: Пример с возвратом множества значений
	RETURNS SETOF customers AS $$
	BEGIN 
		RETURN QUERY
		SELECT *
		FROM customers
		WHERE country = customer_country;		
	END;	
$$ language plpgsql;

SELECT * FROM get_customers_by_country_3('France');
-------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION get_square(ab real, bc real, ac real) RETURNS real AS $$ 
DECLARE 
	perimeter real;                                                                  -- объявление переменной 
BEGIN

	perimeter := (ab + bc + ac) / 2;
	RETURN sqrt(perimeter * (perimeter - ab) * (perimeter - bc) * (perimeter - ac)); -- sqrt: встроенная функция
	
END;
$$ language plpgsql;

SELECT * FROM get_square(2, 3, 4);
-------------------------------------------------------------------------------------------------------------------------------

-- Выявить все продукты в диапозене средних цен. 

CREATE OR REPLACE FUNCTION calc_middle_price() RETURNS SETOF products AS $$ 
DECLARE 
	avg_price real;
	low_price real;
	high_price real;
BEGIN

	SELECT AVG(unit_price) INTO avg_price
	FROM products;
	
	low_price := avg_price * 0.75;
	high_price := avg_price * 1.25;
	
	RETURN QUERY 
	SELECT * FROM products
	WHERE unit_price BETWEEN low_price AND high_price;
	
END;
$$ language plpgsql;

SELECT * FROM calc_middle_price();
-------------------------------------------------------------------------------------------------------------------------------
