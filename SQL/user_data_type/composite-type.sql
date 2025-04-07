
                                            СОСТАВНОЙ ТИП ДАННЫХ

* Составной тип      - тип данных, состоящий из нескольких полей.
* Составному типу    - НЕЛЬЗЯ задавать ограничения !!!
* Часто используется - для возврата данных из функции.
* Чаще всего         - таблица лучше составного типа !!!

-------------------------------------------------------------------------------------------------------

CREATE TYPE type_name AS (
    field_1 field_type,
    field_2 field_type
)

DROP IF EXISTS TYPE type_name [CASCADE]

ROW(1, 2, 'some_text');
-- Создание экземпляра составного типа

=======================================================================================================

CREATE TYPE price_bounds AS (
	max_price real,
	min_price real
);


CREATE OR REPLACE FUNCTION get_price_boundaries_with_composite_type() RETURNS SETOF price_bounds AS $$   
	SELECT MAX(unit_price), MIN(unit_price)
	FROM products	
$$ LANGUAGE SQL;

SELECT * FROM get_price_boundaries_with_composite_type();

-------------------------------------------------------------------------------------------------------

CREATE TYPE complex AS (     -- Создание составного типа данных
	r float8,
	i float8
);

CREATE TABLE math_calcs (    -- Создание таблицы с этим типом (complex)
	math_id serial,
	val complex
);

INSERT INTO math_calcs(val)  -- Заполнение таблицы 
VALUES
(ROW(3.0, 4.0)),
(ROW(2.0, 1.0));

                             -- Варианты выборки:
							 
SELECT * FROM math_calcs;        -- Общий выбор всех полей таблицы

SELECT (val).* FROM math_calcs;  -- Выбор всех полей составного типа (каждый в ртдельной колонке)

SELECT (val).r FROM math_calcs;  -- Выбор первого поля составного типа

                             -- Обновление: 

UPDATE math_calcs
SET val = ROW(5.0, 4.0)
WHERE math_id = 1;

UPDATE math_calcs
SET val.r = 3.0
WHERE math_id = 1;

UPDATE math_calcs
SET val.r = (val).r + 1.0
WHERE math_id = 1;




