
                                         SELECT

SELECT       --  выбрать
FROM         --  из (откуда)
DISTINCT     --  только уникальные
COUNT ()     --  количество
WHERE        --  где (первичный фильтр)  -  (операторы: <, <=, >, >=, =, [!= или <>])
HAVING       --  где (вторичный фильтр)  -  -----""-----""-----""-----""-----""-----
AND          --  И    (true и true)
OR           --  ИЛИ  (true и false)
NOT          --  НЕ
BETWEEN      --  между
ORDER BY     --  сортировка
--по умолчанию - по алфовиту (ASC)
--в реверсном порядке (DESC)
MIN(column)  -- минимальное значение столбца column
MAX(column)  -- максимальное значение столбца column
AVG(column)  -- среднее значение столбца column
SUM(column)  -- сумма значений столбца
ROUND        -- округление до ближайшего целого числа
GROUP BY     -- группировка (располагается между WHERE и ORDER BY)
---------------------------------------------------------------------------------------------------------
UNION        -- объединение значений из разных таблиц
               -- (располагается между SELECT и SELECT)
               -- (выбирает ТОЛЬКО уникальные значения, как DISTINCT)
UNION ALL    -- объединение значений из разных таблиц, С ПОВТОРАМИ !!!
INTERSECT    -- ищет пересечения (одинаковые значения) в разных таблицах
               -- (располагается между SELECT и SELECT)
EXCEPT       -- ищет отсутствие пересечений (одинаковых значений) в разных таблицах
               -- накладывает DISTINCT на первый SELECT !!!
               -- (показывает отличия первого столбца от второго)
EXCEPT ALL   -- то же что и EXCEPT + без DISTINCT,
               -- если 1 - 1  -  одна 1 будет найдена !!!
               --      1 - 2
               --      1 - 1
---------------------------------------------------------------------------------------------------------
Условия с разными логическими операторами различаются приоритетом.
Первым всегда выполняется условие с оператором NOT, за ним AND и только в последнюю очередь — условие с OR
---------------------------------------------------------------------------------------------------------

SELECT country                             - показать страны из таблицы customers
FROM customers                               и страны из таблицы employees,
UNION                                        без повторов
SELECT country                               (имена столбцов НЕ ОБЯЗАНЫ совпадать !!!)
FROM employees

SELECT country                             - показать страны,
FROM customers                               с повторами
UNION ALL                                    из таблиц customers и employees
SELECT country                               (имена столбцов НЕ ОБЯЗАНЫ совпадать !!!)
FROM employees

SELECT country                             - показать одинаковые страны (пересечения)
FROM customers                               в таблице customers
INTERSECT                                    и в таблице suppliers
SELECT country
FROM suppliers

SELECT country                             - показать отличия в значениях стран (отсутствие пересечений)
FROM customers                               в таблице customers и в таблице suppliers,
EXCEPT                                       (отличия первого столбца от второго)
SELECT country                               наложив DISTINCT на первый SELECT
FROM suppliers                               country из таблицы customers

SELECT country                             - то же что и EXCEPT + без DISTINCT,
FROM customers                               если Canada - Canada  -  одна Canada будет найдена !!!
EXCEPT ALL                                        Canada - Poland
SELECT country                                    Canada - Canada
FROM suppliers

----------------------------------------------------------------------------------------------------------------

          -- выбор предыдущей и следующей даты

(SELECT i.ITEM_ID, i.ITEM_NAME, i.ITEM_DESCRIPTION, i.ITEM_AVAILABLE, i.USER_ID AS owner_id, b.START_TIME, b.USER_ID AS booker_id
FROM ITEM i, BOOKING b
WHERE i.ITEM_ID = 2
AND b.ITEM_ID = 2
AND b.START_TIME < NOW()
ORDER BY START_TIME DESC
LIMIT 1)
UNION
(SELECT i.ITEM_ID, i.ITEM_NAME, i.ITEM_DESCRIPTION, i.ITEM_AVAILABLE, i.USER_ID AS owner_id, b.START_TIME, b.USER_ID AS booker_id
FROM ITEM i, BOOKING b
WHERE i.ITEM_ID = 2
AND b.ITEM_ID = 2
AND b.START_TIME > NOW()
ORDER BY START_TIME
LIMIT 1)
ORDER BY START_TIME;
