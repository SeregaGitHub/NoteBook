-- RAISE [level] `message (%)`, arg_name

--   level     - уровень ошибки:
--
-- * DEBUG     - отладка
-- * LOG       - лог
-- * INFO      - информация
-- * NOTICE    - замечание
-- * WARNING   - потенциальная опасность
-- * EXCEPTION - исключение / ошибка  (откатит транзакцию)

-----------------------------------------------------------------------------------------------------

-- log_min_messages     -  регулирует уровень сообщений сервера (WARNING - по умолчанию)
-- client_min_messages  -  регулирует уровень сообщений клиента (NOTICE - по умолчанию)
--
-- HINT  ERRCODE        -  важные параметры
--
-- Параметры присоединяются с помощью - USING:
-- 
-- RAISE 'invalid billing number=%'', number USING HINT='Check out the billing number', ERRCODE=12881
--
-----------------------------------------------------------------------------------------------------
--
--        Чтобы поймать исключение нужен блок EXCEPTION WHEN:
--  
--  EXCEPTION WHEN condition [others] THEN handling_logic
--
-----------------------------------------------------------------------------------------------------

DROP FUNCTION IF EXISTS get_season;

CREATE OR REPLACE FUNCTION get_season(month_number int) RETURNS text AS $$
DECLARE 
	season text;
BEGIN

	IF month_number NOT BETWEEN 1 AND 12 THEN
		RAISE EXCEPTION 'Invalid month. You passed : (%)', month_number USING HINT='Allowed from 1 up to 12 !!!', ERRCODE=12882;
	END IF;
	
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

SELECT * FROM get_season(14) AS season;


CREATE OR REPLACE FUNCTION get_season_caller(month_number int) RETURNS text AS $$
DECLARE 
    err_ctx text;
    err_msg text;
    err_details text;
    err_code text;
BEGIN
	RETURN get_season(month_number);
	EXCEPTION WHEN SQLSTATE '12882' THEN

    GET STACKED DIAGNOSTICS
        err_ctx = PG_EXCEPTION_CONTEXT,
        err_msg = MESSAGE_TEXT,
        err_details = PG_EXCEPTION_DETAIL, 
        err_code = RETURNED_SQLSTATE;

    RAISE INFO 'My custom handler:';
    RAISE INFO 'Error ctx: %', err_ctx;
    RAISE INFO 'Error msg: %', err_msg;
    RAISE INFO 'Error details: %', err_details;
    RAISE INFO 'Error code: %', err_code;
    RAISE INFO 'Error msg-2: %', SQLERRM;
    RAISE INFO 'Error code-2: %', SQLSTATE;

	RAISE INFO 'There is some problem with month number !!!';
	RETURN NULL;

    WHEN OTHERS THEN
        RAISE INFO 'Some others problems...'
        RETURN NULL;
END;
$$ LANGUAGE plpgsql;

SELECT get_season_caller(15);

-----------------------------------------------------------------------------------------------------

