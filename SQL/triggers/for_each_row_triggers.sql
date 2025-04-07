
                                            Построчные триггеры

CREATE OR REPLACE FUNCTION update_chess_player_timestamp() RETURNS trigger AS $$         -- создание триггерной функции
BEGIN
	NEW.last_updated = CURRENT_TIMESTAMP;                                                -- ф-я записывает текущее время обновления строки
	NEW.user_changed = session_user;                                                     -- ф-я записывает пользователя, внёсшего изменения
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;


DROP TRIGGER IF EXISTS update_chess_player_last_updated ON chess_player;                 -- проверка, что такого триггера нет

CREATE TRIGGER update_chess_player_last_updated BEFORE INSERT OR UPDATE ON chess_player  -- создание триггера
	FOR EACH ROW EXECUTE PROCEDURE update_chess_player_timestamp();

---------------------------------------------------------------------------------------------------------------------------------------------------

SELECT player_id, first_name, last_name, title, last_updated, user_changed
FROM chess_player;

INSERT INTO chess_player                                                                 -- при INSERT или UPDATE последнии колонки запишет триггер
VALUES
(5, 'Pol', 'Morphy', 'Grand Master', NULL, NULL);

===================================================================================================================================================

                                        Триггеры на утверждение

DROP TABLE IF EXISTS chess_player_audit;

CREATE TABLE chess_player_audit (
	operation    char(1)      NOT NULL,  -- тип операции (INSERT - I, UPDATE - U, DELETE - D)
	-- колонки таблицы "chess_player"
	player_id    int,
	first_name   text,
	last_name    text,
	title        chess_title,
	-- last_updated и user_changed, в случаи аудита находятся только в таблице аудита
	last_updated timestamp    NOT NULL,
	user_changed varchar(64)  NOT NULL
);

CREATE OR REPLACE FUNCTION chess_player_audit_bilding() RETURNS trigger AS $$
BEGIN
	IF TG_OP = 'INSERT' THEN 
		INSERT INTO chess_player_audit
		SELECT 'I', nt.* FROM new_table AS nt;  -- nt.* (все поля из таблицы с алиасом - nt)
	ELSEIF TG_OP = 'UPDATE' THEN
		INSERT INTO chess_player_audit
		SELECT 'U', nt.* FROM new_table AS nt;
	ELSEIF TG_OP = 'DELETE' THEN
		INSERT INTO chess_player_audit
		SELECT 'D', ot.* FROM old_table AS ot;
	END IF;
	RETURN NULL;
END;
$$ LANGUAGE plpgsql;


DROP TRIGGER IF EXISTS chess_player_audit_insert ON chess_player;

CREATE TRIGGER chess_player_audit_insert AFTER INSERT ON chess_player
REFERENCING NEW TABLE AS new_table
FOR EACH STATEMENT EXECUTE PROCEDURE chess_player_audit_bilding();


DROP TRIGGER IF EXISTS chess_player_audit_update ON chess_player;

CREATE TRIGGER chess_player_audit_update AFTER UPDATE ON chess_player
REFERENCING NEW TABLE AS new_table
FOR EACH STATEMENT EXECUTE PROCEDURE chess_player_audit_bilding();


DROP TRIGGER IF EXISTS chess_player_audit_delete ON chess_player;

CREATE TRIGGER chess_player_audit_delete AFTER DELETE ON chess_player
REFERENCING OLD TABLE AS old_table
FOR EACH STATEMENT EXECUTE PROCEDURE chess_player_audit_bilding();


SELECT * FROM chess_player;

INSERT INTO chess_player
VALUES
(6, 'Hikaru', 'Nakamura', 'Grand Master', NULL, NULL);

SELECT * FROM chess_player_audit;

UPDATE chess_player
SET first_name = 'HIKARU'
WHERE player_id = 6;

DELETE FROM chess_player
WHERE player_id = 6;

