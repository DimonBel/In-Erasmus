CREATE TABLE IF NOT EXISTS faculty_students
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    faculty_id BIGINT NOT NULL
);

INSERT INTO faculty_students (student_id, faculty_id) VALUES
                                                          (1, 1),
                                                          (2, 1),
                                                          (3, 2),
                                                          (4, 3),
                                                          (5, 4),
                                                          (6, 5),
                                                          (7, 6),
                                                          (8, 7),
                                                          (9, 8);