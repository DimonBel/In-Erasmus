-- Insert 2 million rows into the students table
INSERT INTO students (faculty_id, first_name, last_name, email, date_of_birth, enrollment_date)
SELECT (random() * 100 + 1)::int AS faculty_id,
       'FirstName' || s.id,
       'LastName' || s.id,
       'email' || s.id || '@example.com',
       '1990-01-01'::date + (random() * 10000)::int,
       '2010-09-01'::date + (random() * 3000)::int
FROM generate_series(1, 2000000) AS s(id);


-- analyze the time required to count the number of students with faculty_id = 50
EXPLAIN ANALYZE
SELECT COUNT(*)
FROM students
WHERE faculty_id = 50;

-- create index on faculty_id
CREATE INDEX idx_faculty_id ON students (faculty_id);

-- analyze the time required to count the number of students with faculty_id = 50
EXPLAIN ANALYZE
SELECT COUNT(*)
FROM students
WHERE faculty_id = 50;

-- create composite index on faculty_id and enrollment_date
CREATE INDEX idx_faculty_enrollment ON students (faculty_id, enrollment_date);

-- analyze the time required to count the number of students with faculty_id = 50
EXPLAIN ANALYZE
SELECT *
FROM students
WHERE faculty_id = 50
  AND enrollment_date >= '2021-01-01'
  AND enrollment_date <= '2021-12-31';

-- analyze without index leftmost index
EXPLAIN ANALYZE
SELECT *
FROM students
WHERE enrollment_date >= '2021-01-01'
  AND enrollment_date <= '2021-12-31';

