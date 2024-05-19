CREATE TABLE IF NOT EXISTS universities
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

INSERT INTO universities (name) VALUES
                                    ('University of Moldova'),
                                    ('Technical University of Munich'),
                                    ('University of Oxford'),
                                    ('Sorbonne University'),
                                    ('University of Barcelona');

