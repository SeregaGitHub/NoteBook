
* Набор столбцов в "GROUP BY" - это и есть "GROUPING SET"

* GROUP BY GROUPING SETS ((col_a), (col_a, col_b), (col_a, col_b, col_c))
  вернёт группировку по (col_a), (col_a, col_b) и по (col_a, col_b, col_c)

* ROLLUP - сокращённый вариант GROUPING SET
  ROLLUP(col_a, col_b, col_c) - эквивалетн предыдущей группировки

* CUBE генерирует агрегированный набор для всех комбинаций значений 
  в столбцах, указанных в скобках (все варианты группировок (col_a, col_b, col_c)) 

========================================================================================================

SELECT * FROM products LIMIT 10;

SELECT supplier_id, units_in_stock
FROM products
WHERE supplier_id = 1;

===============================================================================
-------------------------------------------------------------------------------
SELECT supplier_id, SUM(units_in_stock)
FROM products
GROUP BY ROLLUP(supplier_id)
ORDER BY supplier_id NULLS FIRST;
-- Запрос как ниже, но без общей суммы !!!
-------------------------------------------------------------------------------

SELECT supplier_id, SUM(units_in_stock)
FROM products
GROUP BY supplier_id
ORDER BY supplier_id;

SELECT supplier_id, category_id, SUM(units_in_stock)
FROM products
GROUP BY supplier_id, category_id
ORDER BY supplier_id;
-------------------------------------------------------------------------------
				-- Объединение двух предыдущих запросов
SELECT supplier_id, category_id, SUM(units_in_stock)
FROM products
GROUP BY GROUPING SETS ((supplier_id), (supplier_id, category_id))
ORDER BY supplier_id, category_id NULLS FIRST;

-- Эквивалент предыдущему запросу (но более короткий)
SELECT supplier_id, category_id, SUM(units_in_stock)
FROM products
GROUP BY ROLLUP (supplier_id, category_id)
ORDER BY supplier_id, category_id NULLS FIRST;


-- Вариант с тремя столбцами
SELECT supplier_id, category_id, reorder_level, SUM(units_in_stock)
FROM products
GROUP BY ROLLUP (supplier_id, category_id, reorder_level)
ORDER BY supplier_id, category_id NULLS FIRST;

===============================================================================

-- Все возможные группировки
SELECT supplier_id, category_id, SUM(units_in_stock)
FROM products
GROUP BY CUBE (supplier_id, category_id)
ORDER BY supplier_id, category_id NULLS FIRST;

