                            СОЗДАНИЕ ТАБЛИЦЫ С РАНДОМНЫМИ ЗНАЧЕНИЯМИ (10 000 000 записей)

CREATE TABLE perf_test(
	id int,
	reason text COLLATE "C",
	annotation text COLLATE "C"
);

INSERT INTO perf_test(id, reason, annotation)
SELECT s.id, md5(random()::text), NULL
FROM generate_series(1, 10000000) AS s(id)
ORDER BY random();

UPDATE perf_test
SET annotation = UPPER(md5(random()::text));

==========================================================================================================================

                                        СОЗДАНИЕ ИНДЕКСА

CREATE INDEX idx_perf_test_id ON perf_test(id);                                 -- создание индекса
CREATE INDEX idx_perf_test_reason_annotation ON perf_test(reason, annotation);  -- создание индекса по двум столбцам
                                                                                -- (так же можно будет бытро искать по первому столбцу (reason) !!!)

SELECT * FROM perf_test LIMIT 10;

==========================================================================================================================

                                    СОЗДАНИЕ ИНДЕКСА ДЛЯ ТЕКСТА (gin)

  Обычный индекс ищет - 'some_pattern%', но не ищет - '%some_pattern%'

CREATE EXTENSION pg_trgm;                                                    -- подключение расширения
CREATE INDEX index_name ON table_name USING gin (column_name gin_trgm_ops);  -- создание индекса для текста

EXPLAIN ANALYZE
SELECT * FROM table_name
WHERE column_name LIKE '%some_pattern%';	