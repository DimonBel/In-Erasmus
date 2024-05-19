CREATE TABLE IF NOT EXISTS university_faculty
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    university_id BIGINT NOT NULL,
    faculty_id BIGINT NOT NULL
);

INSERT INTO university_faculty (university_id, faculty_id) VALUES
                                                               (1, 1),
                                                               (1, 2),
                                                               (1, 3),
                                                               (2, 4),
                                                               (2, 5),
                                                               (3, 6),
                                                               (3, 7),
                                                               (4, 8),
                                                               (5, 9);
