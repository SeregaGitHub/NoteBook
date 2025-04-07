CREATE TABLE chess_game(
	white_player text,
	black_player text,
	moves text[],
	final_state text[][]
);

INSERT INTO chess_game
VALUES (
	'Caruana', 'Nakamura', '{"d4", "d5", "c4", "c6"}',
	'{{"Ra8", "Qe8", "x", "x", "x", "x", "x", "x"}, 
	  {"a7", "x", "x", "x", "x", "x", "x", "x"},
	  {"Kb5", "Bc5", "d5", "x", "x", "x", "x", "x"}}'
);

SELECT * FROM chess_game;
SELECT moves[2:4] FROM chess_game;

SELECT array_dims(moves), array_length(moves, 1)    -- [1:4] | 4   -- [одна размерность : 4 элемента] | 4 элемента
FROM chess_game;

SELECT array_dims(final_state), 
       array_length(final_state, 2)                 -- [1:3][1:8] | 8   -- [одна размерность : 3 элемента][: 8 элементов] | 8 элемента во второй размерности
FROM chess_game;

UPDATE chess_game
SET moves = ARRAY['e4', 'd6', 'd4', 'Kf6'];         -- обновление данных в массиве

UPDATE chess_game
SET moves[4] = 'g6';                                -- обновление одной ячейки в массиве

SELECT * FROM chess_game                            -- фильтрация данных (поиск массива в котором есть 'g6')
WHERE 'g6' = ANY(moves);

-------------------------------------------------------------------------------------------------------------------------------------------------------------

SELECT ARRAY[1, 2, 3, 4] = ARRAY[1, 2, 3, 4];    -- true
SELECT ARRAY[1, 2, 4, 3] = ARRAY[1, 2, 3, 4];    -- false

SELECT ARRAY[1, 2, 4, 3] > ARRAY[1, 2, 3, 4];    -- true
SELECT ARRAY[1, 2, 4, 3] < ARRAY[1, 2, 3, 4];    -- false

SELECT ARRAY[1, 2, 3, 4] @> ARRAY[1, 2];         -- true
SELECT ARRAY[1, 2, 3, 4] @> ARRAY[1, 2, 5];      -- false
SELECT ARRAY[1, 2] <@ ARRAY[1, 2, 5];            -- true

SELECT ARRAY[1, 2, 4, 3] && ARRAY[5, 1, 6, 7];   -- true
SELECT ARRAY[1, 2, 4, 3] && ARRAY[5, 8, 6, 7];   -- false

SELECT * FROM chess_game                         --   выдать все записи из таблицы "moves"
WHERE moves && ARRAY['d4'];                      -- где есть хотя бы одно совпадение из массива

-------------------------------------------------------------------------------------------------------------------------------------------------------------

												VARIADIC + FOREACH


CREATE OR REPLACE FUNCTION filter_even(VARIADIC numbers int[]) RETURNS SETOF int AS $$
BEGIN
	FOR counter IN 1..array_upper(numbers, 1)
	LOOP
		CONTINUE WHEN counter % 2 != 0;
		RETURN NEXT counter;
	END LOOP;
END;
$$ LANGUAGE plpgsql;


				-- FOREACH

CREATE OR REPLACE FUNCTION filter_even(VARIADIC numbers int[]) RETURNS SETOF int AS $$
DECLARE 
	counter int;
BEGIN
	--FOR counter IN 1..array_upper(numbers, 1)
	FOREACH counter IN ARRAY numbers
	LOOP
		CONTINUE WHEN counter % 2 != 0;
		RETURN NEXT counter;
	END LOOP;
END;
$$ LANGUAGE plpgsql;

SELECT * FROM filter_even(1, 2, 3, 4, 5, 6, 7, 8);

