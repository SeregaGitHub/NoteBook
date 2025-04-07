
                            Построчные триггеры

CREATE TRIGGER trigger_name some_condition ON table_name  -- создание построчного триггера
FOR EACH ROW EXECUTE PROCEDURE function_name();

DROP TRIGGER IF EXISTS trigger_name;                      -- удаление триггера

ALTER TRIGGER trigger_name ON table_name                  -- переименование триггера
RENAME TO new_table_name

ALTER TABLE table_name DISABLE TRIGGER trigger_name       -- отключение триггера
                                       ALL                -- отключение всех триггеров таблицы
---------------------------------------------------------------------------------------------

Condition example:

* BEFORE
* AFTER
* INSERT
* UPDATE
* DELETE 

BEFORE INSERT, AFTER UPDATE, BEFORE INSERT OR AFTER UPDATE
---------------------------------------------------------------------------------------------

                        Создание функции для триггера

CREATE FUNCTION function_name() RETURNS trigger AS $$
    BEGIN
    -- function_logic
    END;
$$ LANGUAGE plpgsql;

* Должна возвращать NULL или запись, соответствующую структуре таблицы, на которую будет вешаться триггер
* Если BEFORE-триггер возвращает NULL -> сама операция и AFTER-триггеры будут отменены
* Если BEFORE-триггер меняет строку -> сама операция и AFTER-триггеры будут работать с изменённой строкой
* Если BEFORE-триггер не хочет менять строку -> надо вернуть NEW
* В случае BEFORE-триггера реагирующего на DELETE, возврат не имеет значения (кроме NULL -> он отменит DELETE) 
* При DELETE NEW = NULL, если BEFORE-триггер хочет дать ход DELETE -> надо вернуть OLD
* Возвращаемое значение из AFTER-триггера (или из BEFORE и AFTER триггеров на утверждение) игнорируется -> можно вернуть NULL
* Если построчный AFTER-триггер или триггер на утверждение хочет отменить операцию -> RAISE EXCEPTION

* Через аргумент NEW есть доступ к вставленным и новым модифицированным строкам (INSERT / UPDATE)
* Через аргумент OLD есть доступ к удалённым и старым модифицированным строкам (DELETE / UPDATE)

=============================================================================================

                            Триггеры на утверждение

CREATE TRIGGER trigger_name some_condition ON table_name  -- создание триггера на утверждение
REFERENCING [NEW, OLD] TABLE AS ref_table_name
FOR EACH STATEMENT EXECUTE PROCEDURE function_name();

* Необходимо задекларировать референсную таблицу (ref_table_name - дать имя) 
* В функции доступна переменная TG_OP, хранящая тин операции (INSERT, UPDATE, DELETE)
* Такие триггеры часто используются для настройки аудита