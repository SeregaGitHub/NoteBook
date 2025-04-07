
                                    DOMAIN - собственный тип даных  

CREATE DOMAIN domain_name AS data_type CONSTRAINTS;

DROP DOMAIN IF EXISTS domain_name;

--------------------------------------------------------------------------------------------

                                    СОЗДАНИЕ ДОМЕНА

CREATE DOMAIN text_no_space_null AS text NOT NULL CHECK (value ~ '^(?!\s*$).+');

CREATE TABLE agent(
	first_name text_no_space_null,
	last_name text_no_space_null
);

INSERT INTO agent
VALUES ('bob', 'taylor');         -- OK

SELECT * FROM agent;

INSERT INTO agent
VALUES ('', 'taylor');            -- ERROR

INSERT INTO agent
VALUES (NULL, 'taylor');          -- ERROR

INSERT INTO agent
VALUES ('   ', 'taylor');         -- ERROR

INSERT INTO agent
VALUES ('bob junior', 'taylor');  -- OK

SELECT * FROM agent;

--------------------------------------------------------------------------------------------

                                    ИЗМЕНЕНИЕ ДОМЕНА

ALTER DOMAIN text_no_space_null ADD CONSTRAINT text_no_space_null_length32 CHECK (length(value) <= 32) NOT VALID;
-- Изменяем домен, добавляя в него новое ограничение, но вначале делаем не валидным !!! (NOT VALID)

ALTER DOMAIN text_no_space_null VALIDATE CONSTRAINT text_no_space_null_length32;
-- Проверяем, что в таблице нет невалидных новому ограничению данных

UPDATE agent
SET first_name = '1234567890'
WHERE length(first_name) > 32;
-- Изменяем или удалям невалидные данные

ALTER DOMAIN text_no_space_null VALIDATE CONSTRAINT text_no_space_null_length32;
-- Убеждаемся, что невалидные данные отсутствуют и одновременно утверждаем новые изменения домена

