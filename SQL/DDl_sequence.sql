
CREATE SEQUENCE sequence_name                -  создание последовательности
CREATE SEQUENCE IF NOT EXISTS sequence_name  -  создание последовательности (если она ещё не существует)
DROP SEQUENCE sequence_name                  -  удаление последовательности
ALTER SEQUENCE oldName RENAME TO newName;    -  переименование последовательности
ALTER SEQUENCE sequence_name RESTART WITH 0  -  рестарт последовательности с 0 (можно указать другое значение рестарта)

SELECT currval('sequence_name')              -  показывает текущее значение, не увеличивая его !!!
SELECT nextval('sequence_name');             -  увелечивает текущее значение на инкремент (по умолчанию на 1)
SELECT lastval();                            -  показывает текущее значение последней последовательности в текущей сессии

CREATE SEQUENCE IF NOT EXISTS sequence_name
INCREMENT 16                                 -  указывает значение инкремента
MINVALUE 0                                   -  указывает минимально возможное значение
MAXVALUE 128                                 -  указывает максимально возможное значение
START WITH 0                                 -  указывает с какого значения начинать
-------------------------------------------------------------------------------------------------------------------------
                Создание последовательности в существующей таблице book у столбца book_id

CREATE SEQUENCE IF NOT EXISTS book_book_id_seq
START WITH 1 OWNED BY book.book_id

ALTER TABLE book
ALTER COLUMN book_id SET DEFAULT nextval('book_book_id_seq')
-------------------------------------------------------------------------------------------------------------------------
               ЧТО ЛУЧШЕ ПИСАТЬ ВМЕСТО serial

CREATE TABLE book
(
    book_id int GENERATED ALWAYS AS IDENTITY NOT NULL,
               (или BY DEFAULT, вместо ALWAYS)
    ...
    ...
    ...
)

                   Можно

book_id int GENERATED ALWAYS AS IDENTITY (START WITH 10 INCREMENT 2) NOT NULL

              добавлять условия

------------------------------------------------------------------------------------------------------------------------

                  Это можно обойти

INSERT INTO book
OVERRIDING SYSTEM VALUE
VALUES (3, ..., ..., ...)  -  3 принудительно вставится

      ЭТОГО ЛУЧШЕ - НЕ ДЕЛАТЬ !!!
-------------------------------------------------------------------------------------------------------------------------

===================================================================================================
CREATE SEQUENCE seq1;            -- создание последовательности с именем: seq1
                IF NOT EXISTS    -- создание последовательности с именем: seq1 (если она ещё не существует)

SELECT nextval('seq1');          -- при выборе следующего значения (nextval),
                                  -- по умолчанию оно будет увеличиваться на 1.

SELECT currval('seq1');          -- показывает текущее значение, не увеличивая его !!!

SELECT lastval();                -- показывает текущее значение последней последовательности
                                  -- в текущей сессии.

SELECT setval('seq1', 16, true)  -- установить для последовательности 'seq1' начальное значение 16
                                  -- true устанавливается по умолчанию, его можно НЕ ПИСАТЬ !!!

---------------------------------------------------------------------------------------------------
SELECT setval('seq1', 16, true)   -- если true: значение seq1 СРАЗУ будет равно указанному
SELECT currval('seq1');           -- 16
SELECT nextval('seq1');           -- 17

SELECT nextval('seq1');           -- 18
SELECT currval('seq1');           -- 18
SELECT setval('seq1', 26, false)  -- если false: значение seq1 будет равно указанному ПОСЛЕ nextval
SELECT currval('seq1');           -- 18
SELECT nextval('seq1');           -- 26
SELECT nextval('seq1');           -- 27
---------------------------------------------------------------------------------------------------

CREATE SEQUENCE IF NOT EXISTS seq2 INCREMENT 16;  -- задаст инкремент с шагом - 16.

SELECT currval('seq2');  -- значение ещё не определено !!!
SELECT nextval('seq2');  -- 1
SELECT currval('seq2');  -- 17
SELECT nextval('seq2');  -- 33

CREATE SEQUENCE IF NOT EXISTS seq3
INCREMENT 16
MINVALUE 0
MAXVALUE 128
START WITH 0
SELECT currval('seq3');  -- значение ещё не определено !!!
SELECT nextval('seq3');  -- 0
SELECT nextval('seq3');  -- 16
SELECT nextval('seq3');  -- 32, и так до 128, потом - ошибка !!!

--------------------------------------------------------------------------------------------------

ALTER SEQUENCE seq3 RENAME TO seq4;   -- переименование последовательности
SELECT currval('seq4');               -- 32
ALTER SEQUENCE seq4 RESTART WITH 78;  -- рестарт с 78
SELECT currval('seq4');               -- 32
SELECT nextval('seq4');               -- 78
SELECT nextval('seq4');               -- 94

DROP SEQUENCE seq4;                   -- удаление последовательности
