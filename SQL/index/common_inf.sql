

SELECT amname FROM pg_am;    -- список возможных индексов

heap 
btree   (<, >, <=, >=, =, BETWEEN, IN, LIKE('abc%') но не ('%abc'), NULL, [O(logN)]) - по-умолчанию (самый частый)
hash    (=) [в случае сбоя придётся делать реиндекс !!!] [O(1)]
gist    (для текста)
gin     (для массивов и текста)
spgist  (для двоичных пространств: телефонные номера - страны не по natural order, а остальная часть номера natural order)
brin    (блочно-диапозонный: большой набор упорядоченных данных)

==========================================================================================================================

CREATE INDEX index_name ON table_name(column_name)               [btree - по-умолчанию]

CREATE INDEX index_name ON table_name USING HASH(column_name)    [hash - принудительно]

==========================================================================================================================

                                        МЕТОДЫ СКАНИРОВАНИЯ

- Индексное (index scan)                                  -  малый результирующий набор (1 из 1 000 000)
- Исключетельно индексное сканирование (index only scan)  -  если индекс хранит сами проиндексированные значения
- Сканирование по битовой карте (bitmap scan)             -  средний результирующий набор
- Последовательное сканирование (sequential scan)         -  большой результирующий набор

==========================================================================================================================

                                            EXPLAIN

EXPLAIN some_SELECT...            - позволяет посмотреть на план выполнения запроса 

EXPLAIN ANALYZE some_SELECT...    - позволяет посмотреть на план выполнения запроса и реальные замеры его выполнения

EXPLAIN ANALYZE SELECT * FROM orders
WHERE order_id < 10253

==========================================================================================================================

                        Для получения списка всех колонок с индексами примените следующий запрос:

SELECT
    tbl.relname AS "table",
    idx.relname AS "index",
    col.attname AS "column"
FROM
    pg_index i
JOIN
    pg_attribute col ON col.attrelid = i.indrelid AND col.attnum = ANY(i.indkey)
JOIN
    pg_class idx ON idx.oid = i.indexrelid
JOIN
    pg_class tbl ON tbl.oid = i.indrelid
WHERE
    tbl.relkind = 'r' 
ORDER BY
    "table",
    "index";

                        Чтобы увидеть подробную информацию об индексах определенной таблицы, выполните:

SELECT * FROM pg_indexes WHERE tablename = 'table_name';

==========================================================================================================================

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

CREATE INDEX idx_perf_test_id ON perf_test(id);                                 -- создание индекса
CREATE INDEX idx_perf_test_reason_annotation ON perf_test(reason, annotation);  -- создание индекса по двум столбцам
                                                                                -- (так же можно будет бытро искать по первому столбцу (reason) !!!)

SELECT * FROM perf_test LIMIT 10;

==========================================================================================================================

