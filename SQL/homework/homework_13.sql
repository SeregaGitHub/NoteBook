CREATE OR REPLACE FUNCTION avg_freight(VARIADIC countries text[]) RETURNS float8 AS $$

	SELECT AVG(freight) FROM orders
	WHERE ship_country = ANY(countries)

$$ LANGUAGE SQL;

SELECT avg_freight('Norway', 'Poland');

------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION filter_by_operator(oper int, VARIADIC phone_numbers text[]) RETURNS SETOF text AS $$
DECLARE
	phone_number text;
BEGIN
	FOREACH phone_number IN ARRAY phone_numbers
	LOOP
		CONTINUE WHEN phone_number NOT LIKE CONCAT('__(', oper, ')%');
	    RETURN NEXT phone_number;
	END LOOP;
END;
$$ LANGUAGE plpgsql;

SELECT filter_by_operator(903, '+7(903)1901235', '+7(926)8567589', '+7(903)1532476');

