CREATE TABLE IF NOT EXISTS universities
(
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS `faculties`
(
    id               BIGINT PRIMARY KEY,
    university_id    BIGINT       NOT NULL,
    study_field_id  BIGINT       NOT NULL,
    name           VARCHAR(255) NOT NULL,
    abbreviation   VARCHAR(255) NOT NULL,
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS `students`
(
    id BIGINT PRIMARY KEY,
    faculty_id BIGINT NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    date_of_birth DATE NOT NULL,
    is_graduated BOOLEAN NOT NULL DEFAULT FALSE,
    enrollment_date DATE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS `study_fields`
(
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS `university_faculty`
(
    id BIGINT PRIMARY KEY,
    university_id BIGINT,
    faculty_id BIGINT
);

CREATE TABLE IF NOT EXISTS `faculty_students`
(
    id BIGINT PRIMARY KEY,
    student_id BIGINT,
    faculty_id BIGINT
);