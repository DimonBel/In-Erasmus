CREATE TABLE IF NOT EXISTS feedbacks
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    title      VARCHAR(255) NOT NULL,
    text       TEXT         NOT NULL,
    student_id BIGINT       NOT NULL,
    likes      INT DEFAULT 0,
    created_at DATETIME     NOT NULL,
    updated_at DATETIME     NOT NULL
);

INSERT INTO feedbacks (title, text, student_id, likes, created_at, updated_at)
VALUES ('Great Experience!',
        'I had a wonderful time during my Erasmus exchange. The university was great and I learned a lot.', 1, 10,
        '2024-04-01 00:00:00', '2024-04-01 00:00:00'),
       ('Highly Recommend',
        'The Erasmus program provided me with an excellent opportunity to grow academically and personally.', 2, 8,
        '2024-04-02 00:00:00', '2024-04-02 00:00:00'),
       ('Good Program', 'The program was well-organized, and I enjoyed my time abroad. Would definitely recommend it.',
        3, 5, '2024-04-03 00:00:00', '2024-04-03 00:00:00'),
       ('Valuable Experience',
        'Participating in the Erasmus exchange was a valuable experience for my personal and professional development.',
        4, 12, '2024-05-01 00:00:00', '2024-05-01 00:00:00'),
       ('Wonderful Opportunity',
        'Erasmus gave me a wonderful opportunity to explore a new culture and meet amazing people.', 5, 7,
        '2024-05-02 00:00:00', '2024-05-02 00:00:00'),
       ('Enriching Journey', 'The Erasmus journey enriched my knowledge and helped me grow as a person.', 6, 9,
        '2024-05-03 00:00:00', '2024-05-03 00:00:00'),
       ('Life-Changing Experience',
        'The Erasmus program was a life-changing experience, and I highly recommend it to all students.', 7, 15,
        '2024-05-04 00:00:00', '2024-05-04 00:00:00'),
       ('Fantastic Program', 'This program was fantastic, offering both academic and cultural enrichment.', 8, 6,
        '2024-05-05 00:00:00', '2024-05-05 00:00:00'),
       ('Memorable Time', 'My time with the Erasmus program was memorable and very beneficial for my studies.', 9, 11,
        '2024-05-06 00:00:00', '2024-05-06 00:00:00');

