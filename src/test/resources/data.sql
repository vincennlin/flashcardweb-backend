---- Roles
INSERT INTO roles (name)
VALUES ('ROLE_ADMIN'), ('ROLE_USER');

---- Users
INSERT INTO users (name, username, email, password)
VALUES ('admin', 'admin', 'admin@gmail.com', '$2a$10$vbL5/7oUFCyoC2/L6hNVleyhqFwPawIfZpI4mTVR8qNZl5k1bnw.C'),
       ('test1', 'test1', 'test1@gmail.com', '$2a$10$qo4VnhQC4MoNTReGQqAHROv16dqOvCLUo/OkGOsLMKZXP4yVsHdWS'),
       ('test2', 'test2', 'test2@gmail.com', '$2a$10$gBydQeoll8lYl504ayyvc.fDixMMJ9NFcASPfBdXhMl5YqQGdscQS');

---- User Roles
INSERT INTO user_roles (user_id, role_id)
VALUES ((SELECT id FROM users WHERE username = 'admin'), (SELECT id FROM roles WHERE name = 'ROLE_ADMIN')),
       ((SELECT id FROM users WHERE username = 'test1'), (SELECT id FROM roles WHERE name = 'ROLE_USER')),
       ((SELECT id FROM users WHERE username = 'test2'), (SELECT id FROM roles WHERE name = 'ROLE_USER'));

---- Notes
INSERT INTO notes (content, date_created, last_updated, user_id)
VALUES ('Test note 1', '2024-06-22 06:55:00', '2024-06-22 06:55:00', (SELECT id FROM users WHERE username = 'test1')),
       ('Test note 2', '2024-06-22 06:55:00', '2024-06-22 06:55:00', (SELECT id FROM users WHERE username = 'test1')),
       ('Test note 3', '2024-06-22 06:55:00', '2024-06-22 06:55:00', (SELECT id FROM users WHERE username = 'test1')),
       ('Test note 4', '2024-06-22 06:55:00', '2024-06-22 06:55:00', (SELECT id FROM users WHERE username = 'test2')),
       ('Test note 5', '2024-06-22 06:55:00', '2024-06-22 06:55:00', (SELECT id FROM users WHERE username = 'test2'));

---- Flashcards

-- 插入flashcards表的數據
INSERT INTO flashcards (date_created, extra_info, last_updated, note_id, question, type, user_id)
VALUES (current_timestamp(), NULL, current_timestamp(), (SELECT id FROM notes WHERE content = 'Test note 1'), 'Test question 1', 'SHORT_ANSWER', (SELECT id FROM users WHERE username = 'test1')),
       (current_timestamp(), NULL, current_timestamp(), (SELECT id FROM notes WHERE content = 'Test note 1'), 'Test question 2', 'FILL_IN_THE_BLANK', (SELECT id FROM users WHERE username = 'test1')),
       (current_timestamp(), NULL, current_timestamp(), (SELECT id FROM notes WHERE content = 'Test note 1'), 'Test question 3', 'MULTIPLE_CHOICE', (SELECT id FROM users WHERE username = 'test1')),
       (current_timestamp(), NULL, current_timestamp(), (SELECT id FROM notes WHERE content = 'Test note 1'), 'Test question 4', 'TRUE_FALSE', (SELECT id FROM users WHERE username = 'test1')),
       (current_timestamp(), NULL, current_timestamp(), (SELECT id FROM notes WHERE content = 'Test note 2'), 'Test question 5', 'SHORT_ANSWER', (SELECT id FROM users WHERE username = 'test2'));

-- 插入short_answers表的數據
INSERT INTO short_answers (id, short_answer)
VALUES ((SELECT id FROM flashcards WHERE question = 'Test question 1'), 'Test short answer 1'),
       ((SELECT id FROM flashcards WHERE question = 'Test question 5'), 'Test short answer 2');

-- 插入fill_in_the_blank表的數據
INSERT INTO fill_in_the_blank (id, full_answer)
VALUES ((SELECT id FROM flashcards WHERE question = 'Test question 2'), 'Test fill in the blank answer 1');

-- 插入multiple_choice表的數據
INSERT INTO multiple_choice (id, answer_option_id)
VALUES ((SELECT id FROM flashcards WHERE question = 'Test question 3'), NULL);

-- 插入options表的數據
INSERT INTO options (text, flashcard_id)
VALUES ('Test option A', (SELECT id FROM flashcards WHERE question = 'Test question 3')),
       ('Test option B', (SELECT id FROM flashcards WHERE question = 'Test question 3')),
       ('Test option C', (SELECT id FROM flashcards WHERE question = 'Test question 3'));

-- 更新multiple_choice表的answer_option_id
UPDATE multiple_choice
SET answer_option_id = (SELECT id FROM options WHERE text = 'Test option A')
WHERE id = (SELECT id FROM flashcards WHERE question = 'Test question 3');

-- 插入true_false_answers表的數據
INSERT INTO true_false_answers (id, true_false_answer)
VALUES ((SELECT id FROM flashcards WHERE question = 'Test question 4'), TRUE);

-- 插入in_blank_answers表的數據
INSERT INTO in_blank_answers (flashcard_id, text)
VALUES ((SELECT id FROM flashcards WHERE question = 'Test question 2'), 'Blank answer 1'),
       ((SELECT id FROM flashcards WHERE question = 'Test question 2'), 'Blank answer 2'),
       ((SELECT id FROM flashcards WHERE question = 'Test question 2'), 'Blank answer 3');
