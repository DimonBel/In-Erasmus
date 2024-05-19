CREATE TABLE IF NOT EXISTS achievements
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description TEXT,
    image_url   VARCHAR(255),
    student_id  BIGINT       NOT NULL
);


INSERT INTO achievements (name, description, image_url, student_id)
VALUES ('Erasmus Participation', 'Successfully participated in the Erasmus exchange program.',
        'https://www.svgrepo.com/show/415747/achievement-award-badge.svg', 1),
       ('Language Proficiency', 'Gained proficiency in a new language during Erasmus.',
        'https://www.svgrepo.com/show/415753/chat-communication-conversation.svg', 4),
       ('Research Excellence', 'Conducted outstanding research during the Erasmus program.',
        'https://www.svgrepo.com/show/415756/find-info-information.svg', 5),
       ('Innovation Award', 'Developed an innovative project during the Erasmus exchange.',
        'https://www.svgrepo.com/show/415741/rocket-spaceship-start.svg', 7),
       ('Leadership Skills', 'Demonstrated exceptional leadership skills during the Erasmus program.',
        'https://www.svgrepo.com/show/415755/analytics-chart-diagram.svg', 8),
       ('Volunteer Service', 'Completed extensive volunteer work during the Erasmus exchange.',
        'https://cdn.pixabay.com/photo/2017/08/30/01/05/award-2693678_960_720.png', 9),
       ('Global Networking', 'Built a strong global network of peers and professionals.',
        'https://www.svgrepo.com/show/415745/adventurer-discover-explorer.svg', 2),
       ('Intercultural Competence', 'Developed strong intercultural communication skills.',
        'https://www.svgrepo.com/show/415753/chat-communication-conversation.svg', 3);
