CREATE TABLE IF NOT EXISTS faculties
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    university_id  BIGINT       NOT NULL,
    study_field_id BIGINT       NOT NULL,
    name           VARCHAR(255) NOT NULL,
    abbreviation   VARCHAR(255) NOT NULL,
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO faculties (university_id, study_field_id, name, abbreviation) VALUES
                                                                              (1, 1, 'Faculty of Computer Science', 'FCS'),
                                                                              (1, 2, 'Faculty of Software Engineering', 'FSE'),
                                                                              (1, 3, 'Faculty of Information Systems', 'FIS'),
                                                                              (2, 4, 'Faculty of DevOps', 'FDV'),
                                                                              (2, 5, 'Faculty of Data Science', 'FDS'),
                                                                              (3, 6, 'Faculty of Cyber Security', 'FCS'),
                                                                              (3, 7, 'Faculty of Artificial Intelligence', 'FAI'),
                                                                              (4, 8, 'Faculty of Software Engineering', 'FSE'),
                                                                              (5, 9, 'Faculty of Information Systems', 'FIS');
