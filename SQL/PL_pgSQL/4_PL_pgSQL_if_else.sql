IF expression THEN
-- logic;
ELSEIF expression THEN
-- logic;                   -- ELSEIF и ELSIF: равнозначно !!!
ELSIF expression THEN
-- logic;
ELSE
-- logic;
END IF;

--=============================================================================================================================================

CREATE OR REPLACE FUNCTION convert_temp_to(temperature real, to_celsius bool DEFAULT true) RETURNS real AS $$
DECLARE 
result_temp real;
BEGIN

	IF to_celsius THEN
		result_temp := (5.0 / 9.0) * (temperature-32);
	ELSE 
		result_temp := (9 * temperature + (32 * 5)) / 5.0;
	END IF;
	
	RETURN result_temp;
	
END;
$$ language plpgsql;

SELECT * FROM convert_temp_to(80) AS temp_is;
SELECT * FROM convert_temp_to(26.7, false) AS temp_is;
-------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION get_season(month_number int) RETURNS text AS $$
DECLARE 
season text;
BEGIN

	IF month_number BETWEEN 3 AND 5 THEN
		season := 'Spring';
	ELSEIF month_number BETWEEN 6 AND 8 THEN
		season := 'Summer';
	ELSIF month_number BETWEEN 9 AND 11 THEN
	season := 'Autumn';
	ELSE 
		season := 'Winter';
	END IF;
	
	RETURN season;
	
END;
$$ language plpgsql;

SELECT * FROM get_season(4) AS season;
-------------------------------------------------------------------------------------------------------------------------------

