

-- 1. Создайте функцию, которая делает бэкап таблицы customers (копирует все данные в другую таблицу), 
   -- предварительно стирая таблицу для бэкапа, если такая уже существует (чтобы в случае многократного запуска таблица для бэкапа перезатиралась).

CREATE OR REPLACE FUNCTION backup_customers() RETURNS void AS $$

	DROP TABLE IF EXISTS backedup_customers;
	
	CREATE TABLE backedup_customers AS
	SELECT * FROM customers;

$$ LANGUAGE SQL;

SELECT backup_customers();

SELECT COUNT(*) FROM backedup_customers;


-- 2. Создать функцию, которая возвращает средний фрахт (freight) по всем заказам

CREATE OR REPLACE FUNCTION get_avg_freight() RETURNS float8 AS $$

	SELECT AVG(freight) 
	FROM orders;

$$ LANGUAGE SQL

SELECT * FROM get_avg_freight();


-- 3. Написать функцию, которая принимает два целочисленных параметра, 
   -- используемых как нижняя и верхняя границы для генерации случайного числа в пределах этой границы (включая сами граничные значения).

-- Функция random генерирует вещественное число от 0 до 1. 

-- Необходимо вычислить разницу между границами и прибавить единицу.

-- На полученное число умножить результат функции random() и прибавить к результату значение нижней границы.

-- Применить функцию floor() к конечному результату, чтобы не "уехать" за границу и получить целое число.

CREATE OR REPLACE FUNCTION random_between(low int, high int) RETURNS int AS $$

BEGIN                                                   -- floor(some_args)  -  округление до целого числа
	RETURN floor(random() * (high - low + 1) + low);    -- random()          -  генерирует случайное число от 0.0 - 1.0
END

$$ LANGUAGE plpgsql;

SELECT random_between(1, 3)
FROM generate_series(1, 5)


-- 4. Создать функцию, которая возвращает самые низкую и высокую зарплаты среди сотрудников заданного города

-- Функция изменена на похожую по смыслу !!!

CREATE OR REPLACE FUNCTION get_unit_price_by_order_id(prod_id int, out min_price real, out max_price real) AS $$
	SELECT MIN(unit_price), MAX(unit_price)
	FROM order_details
	WHERE product_id = prod_id
$$ LANGUAGE SQL

SELECT * FROM get_unit_price_by_order_id(1);

-- 5. Создать функцию, которая корректирует зарплату на заданный процент,  
   -- но не корректирует зарплату, если её уровень превышает заданный уровень при этом верхний уровень зарплаты по умолчанию равен 70, 
   -- а процент коррекции равен 15%. 

CREATE OR REPLACE FUNCTION correct_salary(upper_boundary numeric DEFAULT 70, correction_rate numeric DEFAULT 0.15)
RETURNS void AS
$$
	UPDATE employees
	SET salary = salary + (salary * correction_rate)
	WHERE salary <= upper_boundary
$$ LANGUAGE SQL;

SELECT correct_salary();
SELECT salary FROM emploees ORDER BY salary;

-- 6. Модифицировать функцию, корректирующую зарплату таким образом, чтобы в результате коррекции, она так же выводила бы изменённые записи.

CREATE OR REPLACE FUNCTION correct_salary_2(upper_boundary numeric DEFAULT 70, correction_rate numeric DEFAULT 0.15)
RETURNS setof employees AS
$$
	UPDATE employees
	SET salary = salary + (salary * correction_rate)
	WHERE salary <= upper_boundary
	RETURNING *;
$$ LANGUAGE SQL;

SELECT correct_salary_2();
SELECT * FROM emploees ORDER BY salary;


-- 7. Модифицировать предыдущую функцию так, чтобы она возвращала только колонки last_name, first_name, title, salary

CREATE OR REPLACE FUNCTION correct_salary_3(upper_boundary numeric DEFAULT 70, correction_rate numeric DEFAULT 0.15)
RETURNS TABLE(last_name text, first_name text, title text, salary numeric) AS
$$
	UPDATE employees
	SET salary = salary + (salary * correction_rate)
	WHERE salary <= upper_boundary
	RETURNING last_name, first_name, title, salary;
$$ LANGUAGE SQL;

SELECT correct_salary_3();
SELECT * FROM emploees ORDER BY salary;


-- 8. Написать функцию, которая принимает метод доставки и возвращает записи из таблицы orders в которых freight меньше значения, определяемого по следующему алгоритму:

-- - ищем максимум фрахта (freight) среди заказов по заданному методу доставки

-- - корректируем найденный максимум на 30% в сторону понижения

-- - вычисляем среднее значение фрахта среди заказов по заданному методому доставки

-- - вычисляем среднее значение между средним найденным на предыдущем шаге и скорректированным максимумом

-- - возвращаем все заказы в которых значение фрахта меньше найденного на предыдущем шаге среднего

CREATE OR REPLACE FUNCTION get_orders_by_shipping(ship_method int) RETURNS setof orders AS $$
DECLARE
	averige numeric;
	maximum numeric;
	middle numeric;
BEGIN
	SELECT MAX(freight) INTO maximum
	FROM orders
	WHERE ship_via = ship_method;
	
	maximum = maximum - (maximum * 0.3);
	
	SELECT AVG(freight) INTO average
	FROM orders
	WHERE ship_via = ship_method;
	
	middle = (maximum + average) / 2;
	
	RETURN QUERY
	SELECT * FROM orders
	WHERE freight < middle;
END
$$ LANGUAGE plpgsql;

SELECT COUNT(*) FROM get_orders_by_shipping(1);


-- 9. Написать функцию, которая принимает:

-- уровень зарплаты, максимальную зарплату (по умолчанию 80) минимальную зарплату (по умолчанию 30), коээфициет роста зарплаты (по умолчанию 20%)

-- Если зарплата выше минимальной, то возвращает false

-- Если зарплата ниже минимальной, то увеличивает зарплату на коэффициент роста и проверяет не станет ли зарплата после повышения превышать максимальную.

-- Если превысит - возвращает false, в противном случае true.

-- Проверить реализацию, передавая следующие параметры

-- (где c - уровень з/п, max - макс. уровень з/п, min - минимальный уровень з/п, r - коэффициент):

-- c = 40, max = 80, min = 30, r = 0.2 - должна вернуть false

-- c = 79, max = 81, min = 80, r = 0.2 - должна вернуть false

-- c = 79, max = 95, min = 80, r = 0.2 - должна вернуть true

CREATE OR REPLACE FUNCTION check_salary(
	cur_salary numeric, 
	max_salary numeric DEFAULT 80, 
	mix_salary numeric DEFAULT 30, 
	increase_rate numeric DEFAULT 0.2) RETURNS bool AS $$
DECLARE
	new_salary numeric;
BEGIN
	IF cur_salary >= max_salary OR cur_salary >= min_salary THEN
		RETURN false;
	END IF;
	
	IF cur_salary < min_salary THEN
		new_salary = cur_salary + (cur_salary * increase_rate);
	END IF;
	
	IF new_salary > max_salary THEN
		RETURN false;
	ELSE 
		RETURN true;
	END IF;	
END
$$ LANGUAGE plpgsql;

SELECT check_salary(40, 80, 30, 0.2);  -- false
SELECT check_salary(79, 81, 80, 0.2);  -- false
SELECT check_salary(40, 95, 80, 0.2);  -- true