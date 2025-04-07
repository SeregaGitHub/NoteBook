
CREATE TYPE army_rank AS ENUM 
('Private', 'Corporal', 'Sergeant');

SELECT enum_range(null::army_rank);

ALTER TYPE army_rank
ADD VALUE 'Major' AFTER 'Sergeant';

CREATE TABLE personnel(
    perso_id serial PRIMARY KEY,
    first_name text,
    last_name text,
    person_rank army_rank
);

INSERT INTO personnel(first_name, last_name, person_rank)
VALUES
('Petr', 'Petrov', 'Sergeant'),
('Ivan', 'Ivanov', 'Major');

SELECT * FROM personnel;

