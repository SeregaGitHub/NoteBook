
                              Views - Представления

 - Временные
 - Рекурсивные
 - Обновляемые
 - Материализуемые

  View - сохранённый запрос в виде объекта БД (виртуальная таблица)

При изменении view:
  Можно:
    - добавить новые столбцы
    - переименовать view
    - удалить view
  Нельзя:
    - удалить существующие столбцы
    - поменять имена столбцов
    - поменять порядок следования столбцов

  Модификация данных через view

    - Только одна таблица в FROM
    - Нет DISTINCT, GROUP BY, HAVING, UNION, INTERSECT, EXCEPT, LIMIT
    - Нет оконных функций, MIN, MAX, SUM, COUNT, AVG
    - WHERE не под запретом.

=======================================================================================

CREATE VIEW view_name AS               -  создание пердставления
SELECT select_statement

CREATE OR REPLACE VIEW view_name AS    -  изменение пердставления
SELECT select_statement

ALTER VIEW old_name                    -  переименование представления
RENAME TO new_name

DROP VIEW [IF EXISTS] view_name        -  удаление представления

---------------------------------------------------------------------------------------
                      После селекта ЛУЧШЕ прописать:

WITH LOCAL CHECK OPTION    -  чтобы через view нельзя было вставлять в основную таблицу
                              данные, которые не соответствуют фильтрам этого view.

WITH CASCADE CHECK OPTION  -  то же, только теперь CHECK распространяется
                              на все наследуемые от этой view другие view.
=======================================================================================

CREATE VIEW products_suppliers_categories AS
SELECT product_name, quantity_per_unit, unit_price, units_in_stock,
	   company_name, contact_name, phone, category_name, description
FROM products
JOIN suppliers USING (supplier_id)
JOIN categories USING (category_id);
WITH LOCAL CHECK OPTION

SELECT * FROM products_suppliers_categories;

--------------------------------------------------------------------------------------
