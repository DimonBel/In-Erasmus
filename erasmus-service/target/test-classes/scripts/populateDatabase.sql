DELETE FROM universities;
DELETE FROM faculties;
DELETE FROM students;

ALTER SEQUENCE universities_id_seq RESTART WITH 1;
ALTER SEQUENCE faculties_id_seq RESTART WITH 1;
ALTER SEQUENCE students_id_seq RESTART WITH 1;

INSERT INTO universities (id, name) VALUES (1, 'University of Moldova');
