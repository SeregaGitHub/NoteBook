
WHILE expression            -- Пока expression = true: цикл выполняется
    LOOP 
      -- logic
    END LOOP;

LOOP
    EXIT WHEN expression    -- Когда expression = true: цикл останавливается (аналог break)
      -- logic
END LOOP;

FOR count IN a..b BY x      -- count    - 0       (счётчик - количество итераций)
    LOOP                    -- IN a..b  - IN 1..5 (диапозон)
      -- logic              -- BY x   - BY 2  (шаг счётчика - необязательный аргумент)
    END LOOP;

CONTINUE WHEN expression    -- Когда expression = true: цикл переходит на новую итерацию

--=============================================================================================================================================

DO $$                                             -- DO - анонимный блок кода (не функция)
BEGIN
	FOR counter IN 1..5
	LOOP
		RAISE NOTICE 'Counter: %', counter;
	END LOOP;
END$$;

-- ЗАМЕЧАНИЕ:  Counter: 1
-- ЗАМЕЧАНИЕ:  Counter: 2
-- ЗАМЕЧАНИЕ:  Counter: 3
-- ЗАМЕЧАНИЕ:  Counter: 4
-- ЗАМЕЧАНИЕ:  Counter: 5
-- DO
-- 
-- Query returned successfully in 70 msec.

DO $$
BEGIN
	FOR counter IN REVERSE 5..1                   -- REVERSE - обратный порядок
	LOOP
		RAISE NOTICE 'Counter: %', counter;
	END LOOP;
END$$;

-- ЗАМЕЧАНИЕ:  Counter: 5
-- ЗАМЕЧАНИЕ:  Counter: 4
-- ЗАМЕЧАНИЕ:  Counter: 3
-- ЗАМЕЧАНИЕ:  Counter: 2
-- ЗАМЕЧАНИЕ:  Counter: 1
-- DO
-- 
-- Query returned successfully in 68 msec.
-----------------------------------------------------------------------------------------------

-- Числа Фибоначчи
-- 0  1  2  3  4  5  6  7  8  9  
-- 1  1  2  3  5  8  13 21 34 55

CREATE OR REPLACE FUNCTION fib(n int) RETURNS int AS $$
DECLARE 
	counter int := 0;
	i int := 0;
	j int := 1;
BEGIN
	IF n < 1 THEN
		RETURN 0;
	END IF;
	
	WHILE counter < n
	LOOP
		counter := counter + 1;
		SELECT j, i + j INTO i, j; 
	END LOOP;
	
	RETURN i;
	
END;
$$ language plpgsql;

SELECT fib(10);

        -- ИЛИ

CREATE OR REPLACE FUNCTION fib_2(n int) RETURNS int AS $$
DECLARE 
	counter int := 0;
	i int := 0;
	j int := 1;
BEGIN
	IF n < 1 THEN
		RETURN 0;
	END IF;
	
	WHILE counter < n
	LOOP
	EXIT WHEN counter = n;
		counter := counter + 1;
		SELECT j, i + j INTO i, j; 
	END LOOP;
	
	RETURN i;
	
END;
$$ language plpgsql;

SELECT fib_2(10);
-------------------------------------------------------------------------------------------------------------------------------

