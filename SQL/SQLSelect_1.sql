
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
                  /*
                        BETWEEN возвращает записи, где выражение находится в пределах диапазона value1 и value2 (включительно) !!!
                  */
                  /*
                        Если в датах есть время, то between выдаёт данные от '2011-05-25 00:00:00' до '2011-09-25 00:00:00'
                      и следовательно все данные с датой '2011-09-25" у которых время больше '00:00:00' не попадают в выборку !!!
                  */
  ORDER BY     --  сортировка
                 --по умолчанию - по алфовиту (ASC)
                 --в реверсном порядке (DESC)
  MIN(column)  -- минимальное значение столбца column
  MAX(column)  -- максимальное значение столбца column
  AVG(column)  -- среднее значение столбца column
  SUM(column)  -- сумма значений столбца
  ROUND        -- округление до ближайшего целого числа
  GROUP BY     -- группировка (располагается между WHERE и ORDER BY)
                /* (Например: посчитать среднюю сумму заказов для каждого города)
                   Сначала группируются данные по одному городу, для них вычисляется среднее значение и
                   результат помещается в таблицу. И так для каждого города.*/
---------------------------------------------------------------------------------------------------------
                 Условия с разными логическими операторами различаются приоритетом.
Первым всегда выполняется условие с оператором NOT, за ним AND и только в последнюю очередь — условие с OR
---------------------------------------------------------------------------------------------------------

SELECT *                                             - выбрать всё из таблицы products
FROM customers;

SELECT product_id, product_name, unit_price          - выбрать 3 столбца из таблицы products
FROM products;

SELECT product_name, unit_price * units_in_stock     -  выбрать столбец продукты и показать
FROM products;                                          результат умножения двух столбцов

SELECT city                                          -  выбрать столбец city, из таблицы employees
FROM employees;                                         (выберет все строки из таблицы employees)

SELECT DISTINCT city                                 -  выбрать столбец city, из таблицы employees
FROM employees;                                         (выберет все строки БЕЗ ПОВТОРОВ ОДИНАКОВЫХ !!!)

SELECT DISTINCT city, country                        -  выбрать столбцы city, country из таблицы employees
FROM employees;                                         (по максимому без повторов)
                                                        все уникальные комбинации значений в этих полях

SELECT COUNT(*)                                      -  показать число строк в таблице employees
FROM employees;

SELECT COUNT (country)                               -  показать число строк в столбце country
FROM employees;                                         в таблице employees

SELECT COUNT (DISTINCT country)                      -  показать число уникальных стран в
FROM employees;                                         столбце country таблицы employees

SELECT DISTINCT country, city                        -  выбрать все уникальные сочетания городов и стран
FROM customers;                                         в которых "зарегестрированы" заказчики

SELECT company_name, contact_name, phone             -  выбрать стороки из таблицы customers,
FROM customers                                          со столбцами company_name, contact_name, phone
WHERE country = 'USA';                                  где столбец country = 'USA'

SELECT product_name                                  -  выбрать строки из таблицы products
FROM products                                           со столбцами product_name,
WHERE unit_price > 20;                                  где цена (unit_price) > 20

SELECT order_id, customer_id, order_date             -  выбрать строки из таблицы orders
FROM orders                                             со столбцами order_id, customer_id, order_date
WHERE order_date > '1998-04-01';                        где дата позже 1998-04-01
                                                        (Формат даты ДОЛЖЕН совпадать со столбцом !!!)

SELECT product_id, product_name                      -  выбрать строки из таблицы products
FROM products                                           со столбцами product_id, product_name
WHERE unit_price > 20 AND units_in_stock > 40;          где цена > 20 и количество на складе > 40

SELECT customer_id, contact_name                     -  выбрать стороки из таблицы customers,
FROM customers                                          со столбцами customer_id, contact_name
WHERE city = 'London' OR city = 'San Francisco';        где город Лондон или Сан-Франциско

SELECT COUNT (*)                                     -  показать количество строк из таблицы orders
FROM orders                                             где
WHERE freight BETWEEN 20 AND 40;                        вес от 20 до 40 (BETWEEN - включает 20 и 40 !!!)

SELECT *                                             -  выбрать стороки из таблицы customers,
FROM customers                                          где страна В списке:
WHERE country IN ('Mexico', 'Germany', 'USA');          ('Mexico', 'Germany', 'USA')

SELECT *                                             -  выбрать стороки из таблицы customers,
FROM customers                                          где страна НЕ В списке:
WHERE country NOT IN ('Mexico', 'Germany', 'USA');      ('Mexico', 'Germany', 'USA'

SELECT *
FROM customers
WHERE NOT country = 'Mexico';

SELECT DISTINCT country                              -  выбрать стороки из таблицы customers,
FROM customers                                          столбец country, где будет выполнена
ORDER BY country                                        сортировка по алфовиту - country.

SELECT DISTINCT country, city                        -  выбрать стороки из таблицы customers,
FROM customers                                          столбецы country и city, отсортированные:
ORDER BY country, city                                  вначале по country, потом по city !!!

SELECT product_id, product_name                      -  выбрать строки из таблицы products
FROM products                                           со столбцами product_id, product_name
WHERE unit_price > 80                                   где цена > 80 и
ORDER BY product_id                                     отсортировать по product_id

SELECT MIN(order_date)                               -  выбрать строку из таблицы orders
FROM orders                                             с минимальным -MIN- значением в столбце order_date,
WHERE ship_city = 'London'                              и где ship_city = 'London'.

SELECT AVG(unit_price)                               -  показать, из таблицы products
FROM products                                           среднее значение столбца unit_price

SELECT SUM(units_in_stock)                           -  показать, из таблицы products
FROM products                                           сумму всех значений столбца units_in_stock

SELECT ship_country, COUNT (*)                       -  показать, из таблицы orders
FROM orders                                             страны + количество заказов
WHERE freight > 50                                      вес которых больше 50
GROUP BY ship_country                                   сгруппировав по странам и
ORDER BY COUNT (*) DESC                                 отсортировав по количеству, по убыванию

SELECT category_id, SUM(units_in_stock)              -  показать, из таблицы products
FROM products                                           все категории + сумма товаров
GROUP BY category_id                                    сгруппировав по категориям
ORDER BY SUM(units_in_stock) DESC                       отсортировав по сумме, по убыванию

SELECT category_id, SUM(units_in_stock * unit_price) -  показать категорию и сумму товаров * на цену
FROM products                                           из таблицы products
WHERE discontinued = 0                                  где discontinued = 0
GROUP BY category_id                                    сгруппированные по category_id
HAVING SUM(units_in_stock * unit_price) > 5000          где SUM(units_in_stock * unit_price) > 5000
ORDER BY SUM(units_in_stock * unit_price) DESC          отсортированные

ROUND ((SUM (total)) / COUNT (DISTINCT (user_id)))   -  средняя выручка на уникального пользователя, округлённая до ближайшего целого числа
ROUND (AVG (total), 2)                               -  средняя выручка округлённая до двух знаков после запятой

SELECT product_name, unit_price, units_in_stock      -  показать, отсортированные по убыванию цены
FROM products                                           продукты, где цена больше 20
WHERE unit_price > 20                                   и где их более 30 в продаже
GROUP BY product_name, unit_price, units_in_stock       сгруппированные по трём столбцам
HAVING units_in_stock > 30
ORDER BY unit_price DESC, product_name               -- сортировка по двум столбцам
---------------------------------------------------------------------------------------------------
SELECT ship_country, SUM(freight)
FROM orders
--WHERE SUM(freight) > 2750      (так НЕЛЬЗЯ !!!)
--WHERE ship_region IS NOT NULL  (так можно !!!)
GROUP BY ship_country
HAVING SUM(freight) > 2750    -- (так можно и без WHERE !!!)
ORDER BY SUM(freight) DESC;
---------------------------------------------------------------------------------------------------
