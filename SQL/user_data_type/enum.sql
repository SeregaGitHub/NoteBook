
CREATE TYPE enum_name AS ENUM ('value_1', 'value_2', ...);

========================================================================================================

									-- Вариант без ENUM - необходим JOIN для выборки !!!

											-- Вариант с ENUM

CREATE TYPE chess_title AS ENUM        -- Создание enum
('Candidate Master', 'FIDE Master', 'International Master');

SELECT enum_range(null::chess_title);  -- Выборка всех значений enum

ALTER TYPE chess_title                 -- Изменение enum (AFTER - необязательно)
ADD VALUE 'Grand Master' AFTER 'International Master';
                                       -- Можно BEFORE вместо AFTER

CREATE TABLE chess_player (            -- Создание таблицы с полем типа enum
	player_id serial,
	first_name text,
	last_name text,
	title chess_title
);
                                       -- Внесение данных в таблицу с полем типа enum
INSERT INTO chess_player(first_name, last_name, title)
VALUES
('Wesley', 'So', 'Grand Master');

SELECT * FROM chess_player;            -- Теперь при выборке JOIN не нужен

                                       -- Добавлять данные в такую таблицу можно только со значениями из enum
INSERT INTO chess_player(first_name, last_name, title)
VALUES
('Vlad', 'Kramnik', 'some_text');      -- 'some_text' - ERROR !!!

INSERT INTO chess_player(first_name, last_name, title)
VALUES
('Vlad', 'Kramnik', 1);                -- id enum - использовать нельзя !!!