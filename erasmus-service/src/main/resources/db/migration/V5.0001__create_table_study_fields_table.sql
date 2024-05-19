CREATE TABLE IF NOT EXISTS study_fields
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO study_fields (name) VALUES
                                    ('JAVA_DEVELOPMENT'),
                                    ('JAVA_QA'),
                                    ('JS_QA'),
                                    ('DEVOPS'),
                                    ('DATA_SCIENCE'),
                                    ('CYBER_SECURITY'),
                                    ('ARTIFICIAL_INTELLIGENCE'),
                                    ('SOFTWARE_ENGINEERING'),
                                    ('INFORMATION_SYSTEMS');
