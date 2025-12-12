-- delete all duplicate emails, keeping only one unique email with the smallest id

DELETE FROM Person p
USING Person d
WHERE
    p.email = d.email
    AND p.id > d.id;