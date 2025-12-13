-- leetcode
-- 607. Sales Person

-- SELECT sp.name 
-- FROM SalesPerson sp
-- WHERE sp.sales_id NOT IN (
--     SELECT o.sales_id
--     FROM Orders o
--     JOIN Company c ON c.com_id = o.com_id
--     WHERE c.name = 'RED'
-- )

SELECT sp.name
FROM Orders o
JOIN Company c ON (o.com_id = c.com_id AND c.name = 'RED')
                                   --  AND c.name = 'RED' - like WHERE c.name = 'RED'
RIGHT JOIN SalesPerson sp ON sp.sales_id = o.sales_id
WHERE c.com_id IS NULL
  --  o.sales_id