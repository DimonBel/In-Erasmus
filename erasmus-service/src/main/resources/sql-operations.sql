-- CRUD
INSERT INTO universities (id, name) VALUES
                                            (1, 'University of Engineering'),
                                            (2, 'University of Grid');

INSERT INTO study_fields (id, name)
VALUES (1, 'Computer Science'),
       (2, 'Data Science'),
       (3, 'Software Engineering'),
       (4, 'Information Technology');


INSERT INTO faculties (id, university_id, study_field_id, name, abbreviation)
VALUES (1, 1, 1, 'Engineering', 'ENG'),
       (2, 2, 2, 'Development', 'DEV'),
       (3, 2, 3, 'Quality Assurance', 'QA');


INSERT INTO university_faculty (id, university_id, faculty_id) VALUES (1, 1, 1);


SELECT * FROM faculties WHERE id = 1;

UPDATE faculties
SET name = 'Engineering and Technology', abbreviation = 'E&T'
WHERE id = 1;

DELETE FROM faculties WHERE id = 1;

-- Search Query with Dynamic Filters, Pagination, and Sorting
INSERT INTO students (id, faculty_id, first_name, last_name, email,
                      date_of_birth, is_graduated, enrollment_date, created_at, updated_at)
VALUES (1, 1, 'John', 'Doe', 'test@mail.ru', '1990-01-01', false, '2019-01-01', '2019-01-01', '2019-01-01'),
       (2, 1, 'John', 'Doe', 'jane.doe@example.com', '1990-01-01', false, '2019-01-01', '2019-01-01', '2019-01-01');

SELECT * FROM students
WHERE (first_name = 'John' OR last_name = 'Doe' OR email = 'john.doe@example.com')
  AND faculty_id = 1
ORDER BY last_name ASC, first_name ASC
LIMIT 10 OFFSET 0;

SELECT * FROM students
WHERE faculty_id = (SELECT id FROM faculties WHERE abbreviation = 'ENG');

UPDATE students
SET is_graduated = TRUE
WHERE email = 'jane.doe@example.com';


SELECT f.* FROM faculties f
JOIN study_fields sf ON f.study_field_id = sf.id
WHERE sf.name = 'Computer Science';


-- Search Query with Joined Data
SELECT s.first_name, s.last_name, f.name AS faculty_name, u.name AS university_name
FROM students s
         JOIN faculties f ON s.faculty_id = f.id
         JOIN universities u ON f.university_id = u.id
WHERE s.id = 1;

-- Statistics Query
SELECT f.name, COUNT(s.id) AS number_of_students
FROM faculties f
         JOIN students s ON f.id = s.faculty_id
GROUP BY f.name;
-- Top Faculties by Student Count
SELECT f.name, COUNT(s.id) AS student_count
FROM faculties f
         JOIN students s ON f.id = s.faculty_id
GROUP BY f.name
ORDER BY student_count DESC
LIMIT 5;

-- Display The number of students per Faculty

SELECT f.name, COUNT(s.id) AS student_count
FROM faculties f
         LEFT JOIN students s ON f.id = s.faculty_id
GROUP BY f.name;

-- Find Top 3 Popular Study Fields Based on Student Enrollment
SELECT sf.name, COUNT(s.id) AS student_count
FROM study_fields sf
         JOIN faculties f ON sf.id = f.study_field_id
         JOIN students s ON f.id = s.faculty_id
GROUP BY sf.name
ORDER BY student_count DESC
LIMIT 3;
