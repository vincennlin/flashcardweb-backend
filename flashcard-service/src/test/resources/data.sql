---- Authorities
INSERT INTO authorities (name)
VALUES ('READ'), ('CREATE'), ('UPDATE'), ('DELETE'), ('ADVANCED');

---- Roles
INSERT INTO roles (name)
VALUES ('ROLE_ADMIN'), ('ROLE_USER');

---- Users
INSERT INTO users (date_created, email, last_updated, name, password, username)
VALUES (current_timestamp(), 'admin@gmail.com', current_timestamp(), 'admin', '$2a$10$TUC2wEYYirdqy2W.pSG7JeeCEbk9GhlWx2zpTwQzvQpHSEUWKsPoy', 'admin'),
       (current_timestamp(), 'test1@gmail.com', current_timestamp(), 'test1', '$2a$10$tq7AB6BldnkCB/SNaNUJM.vjk6eqkh/jX4/VIzt7hIzsKbJKrbfUS', 'test1'),
       (current_timestamp(), 'test2@gmail.com', current_timestamp(), 'test2', '$2a$10$9ct2DFT3pDTjwsI5fFqhjeX.IxpRZR8h.FdJjG9xP57ylaYMQ375G', 'test2');

---- User Roles
INSERT INTO user_roles (user_id, role_id)
VALUES ((SELECT id FROM users WHERE username = 'admin'), (SELECT id FROM roles WHERE name = 'ROLE_ADMIN')),
       ((SELECT id FROM users WHERE username = 'test1'), (SELECT id FROM roles WHERE name = 'ROLE_USER')),
       ((SELECT id FROM users WHERE username = 'test2'), (SELECT id FROM roles WHERE name = 'ROLE_USER'));

---- Roles Authorities
INSERT INTO roles_authorities (role_id, authority_id)
VALUES ((SELECT id FROM roles WHERE name = 'ROLE_ADMIN'), (SELECT id FROM authorities WHERE name = 'READ')),
       ((SELECT id FROM roles WHERE name = 'ROLE_ADMIN'), (SELECT id FROM authorities WHERE name = 'CREATE')),
       ((SELECT id FROM roles WHERE name = 'ROLE_ADMIN'), (SELECT id FROM authorities WHERE name = 'UPDATE')),
       ((SELECT id FROM roles WHERE name = 'ROLE_ADMIN'), (SELECT id FROM authorities WHERE name = 'DELETE')),
       ((SELECT id FROM roles WHERE name = 'ROLE_ADMIN'), (SELECT id FROM authorities WHERE name = 'ADVANCED')),
       ((SELECT id FROM roles WHERE name = 'ROLE_USER'), (SELECT id FROM authorities WHERE name = 'READ')),
       ((SELECT id FROM roles WHERE name = 'ROLE_USER'), (SELECT id FROM authorities WHERE name = 'CREATE')),
       ((SELECT id FROM roles WHERE name = 'ROLE_USER'), (SELECT id FROM authorities WHERE name = 'UPDATE')),
       ((SELECT id FROM roles WHERE name = 'ROLE_USER'), (SELECT id FROM authorities WHERE name = 'DELETE'));

---- Notes
INSERT INTO notes (content, date_created, last_updated, user_id)
VALUES ('Test note 1', current_timestamp(), current_timestamp(), (SELECT id FROM users WHERE username = 'test1')),
       ('Test note 2', current_timestamp(), current_timestamp(), (SELECT id FROM users WHERE username = 'test1')),
       ('Test note 3', current_timestamp(), current_timestamp(), (SELECT id FROM users WHERE username = 'test1')),
       ('Test note 4', current_timestamp(), current_timestamp(), (SELECT id FROM users WHERE username = 'test2')),
       ('Test note 5', current_timestamp(), current_timestamp(), (SELECT id FROM users WHERE username = 'test2'));

---- Flashcards
INSERT INTO flashcards (date_created, extra_info, last_updated, note_id, question, type, user_id)
VALUES (current_timestamp(), NULL, current_timestamp(), (SELECT id FROM notes WHERE content = 'Test note 1'), 'Test question 1', 'SHORT_ANSWER', (SELECT id FROM users WHERE username = 'test1')),
       (current_timestamp(), NULL, current_timestamp(), (SELECT id FROM notes WHERE content = 'Test note 1'), 'Test question 2', 'FILL_IN_THE_BLANK', (SELECT id FROM users WHERE username = 'test1')),
       (current_timestamp(), NULL, current_timestamp(), (SELECT id FROM notes WHERE content = 'Test note 1'), 'Test question 3', 'MULTIPLE_CHOICE', (SELECT id FROM users WHERE username = 'test1')),
       (current_timestamp(), NULL, current_timestamp(), (SELECT id FROM notes WHERE content = 'Test note 1'), 'Test question 4', 'TRUE_FALSE', (SELECT id FROM users WHERE username = 'test1')),
       (current_timestamp(), NULL, current_timestamp(), (SELECT id FROM notes WHERE content = 'Test note 4'), 'Test question 5', 'SHORT_ANSWER', (SELECT id FROM users WHERE username = 'test2'));

---- Short Answers
INSERT INTO short_answers (id, short_answer)
VALUES ((SELECT id FROM flashcards WHERE question = 'Test question 1'), 'Test short answer 1'),
       ((SELECT id FROM flashcards WHERE question = 'Test question 5'), 'Test short answer 5');

---- Fill in the Blank
INSERT INTO fill_in_the_blank (id, full_answer)
VALUES ((SELECT id FROM flashcards WHERE question = 'Test question 2'), 'Test fill in the blank answer 1');

---- Multiple Choice
INSERT INTO multiple_choice (id, answer_option_id)
VALUES ((SELECT id FROM flashcards WHERE question = 'Test question 3'), NULL);

---- Options
INSERT INTO options (text, flashcard_id)
VALUES ('Test option A', (SELECT id FROM flashcards WHERE question = 'Test question 3')),
       ('Test option B', (SELECT id FROM flashcards WHERE question = 'Test question 3')),
       ('Test option C', (SELECT id FROM flashcards WHERE question = 'Test question 3'));

-- 更新multiple_choice表的answer_option_id
UPDATE multiple_choice
SET answer_option_id = (SELECT id FROM options WHERE text = 'Test option A')
WHERE id = (SELECT id FROM flashcards WHERE question = 'Test question 3');

---- True False Answers
INSERT INTO true_false_answers (id, true_false_answer)
VALUES ((SELECT id FROM flashcards WHERE question = 'Test question 4'), TRUE);

---- In Blank Answers
INSERT INTO in_blank_answers (flashcard_id, text)
VALUES ((SELECT id FROM flashcards WHERE question = 'Test question 2'), 'Blank answer 1'),
       ((SELECT id FROM flashcards WHERE question = 'Test question 2'), 'Blank answer 2'),
       ((SELECT id FROM flashcards WHERE question = 'Test question 2'), 'Blank answer 3');

---- Tags
INSERT INTO tags (tag_name, user_id)
VALUES ('Tag1', (SELECT id FROM users WHERE username = 'test1')),
       ('Tag2', (SELECT id FROM users WHERE username = 'test1')),
       ('Tag3', (SELECT id FROM users WHERE username = 'test1')),
       ('Tag4', (SELECT id FROM users WHERE username = 'test1'));

---- Flashcards Tags
INSERT INTO flashcards_tags (flashcard_id, tag_id)
VALUES ((SELECT id FROM flashcards WHERE question = 'Test question 1'), (SELECT id FROM tags WHERE tag_name = 'Tag1')),
       ((SELECT id FROM flashcards WHERE question = 'Test question 1'), (SELECT id FROM tags WHERE tag_name = 'Tag2')),
       ((SELECT id FROM flashcards WHERE question = 'Test question 1'), (SELECT id FROM tags WHERE tag_name = 'Tag3')),
       ((SELECT id FROM flashcards WHERE question = 'Test question 2'), (SELECT id FROM tags WHERE tag_name = 'Tag1'));

INSERT INTO review_infos (last_reviewed, next_review, review_interval, review_level, flashcard_id)
VALUES (current_timestamp() - INTERVAL '2' DAY, current_timestamp() - INTERVAL '1' DAY, 1, 2, (SELECT id FROM flashcards WHERE question = 'Test question 1'));

---- Review States
INSERT INTO review_states (date_reviewed, last_reviewed, next_review, review_interval, review_level, review_option, review_info_id)
VALUES (current_timestamp() - INTERVAL '3' DAY, null, current_timestamp() - INTERVAL '2' DAY, 1, 1, 'HARD', 1),
       (current_timestamp() - INTERVAL '2' DAY, current_timestamp() - INTERVAL '3' DAY, current_timestamp() - INTERVAL '1' DAY, 1, 2, 'GOOD', 1);
