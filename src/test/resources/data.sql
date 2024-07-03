---- Note
INSERT INTO notes (content, date_created, last_updated)
VALUES ('Test note 1', '2024-06-22 06:55:00', '2024-06-22 06:55:00');

INSERT INTO notes (content, date_created, last_updated)
VALUES ('Test note 2', '2024-06-22 06:55:00', '2024-06-22 06:55:00');

INSERT INTO notes (content, date_created, last_updated)
VALUES ('Test note 3', '2024-06-22 06:55:00', '2024-06-22 06:55:00');

INSERT INTO notes (content, date_created, last_updated)
VALUES ('Test note 4', '2024-06-22 06:55:00', '2024-06-22 06:55:00');

INSERT INTO notes (content, date_created, last_updated)
VALUES ('Test note 5', '2024-06-22 06:55:00', '2024-06-22 06:55:00');

---- Flashcard

-- Short Answer
-- 插入flashcards表的數據
INSERT INTO flashcards (date_created, extra_info, last_updated, note_id, question, type)
VALUES (current_timestamp(), NULL, current_timestamp(), 1, 'Test question 1', 'SHORT_ANSWER'),
       (current_timestamp(), NULL, current_timestamp(), 1, 'Test question 2', 'FILL_IN_THE_BLANK'),
       (current_timestamp(), NULL, current_timestamp(), 1, 'Test question 3', 'MULTIPLE_CHOICE'),
       (current_timestamp(), NULL, current_timestamp(), 1, 'Test question 4', 'TRUE_FALSE');

-- 插入short_answers表的數據
INSERT INTO short_answers (id, short_answer)
VALUES (1, 'Test short answer 1');

-- 插入fill_in_the_blank表的數據
INSERT INTO fill_in_the_blank (id, full_answer)
VALUES (2, 'Test fill in the blank answer 1');

-- 插入multiple_choice表的數據
INSERT INTO multiple_choice (id, answer_option_id)
VALUES (3, NULL);

-- 插入options表的數據
INSERT INTO options (text, flashcard_id)
VALUES ('Test option A', 3),
       ('Test option B', 3),
       ('Test option C', 3);

-- 更新multiple_choice表的answer_option_id
UPDATE multiple_choice SET answer_option_id = 1 WHERE id = 3;

-- 插入true_false_answers表的數據
INSERT INTO true_false_answers (id, true_false_answer)
VALUES (4, TRUE);

-- 插入in_blank_answers表的數據
INSERT INTO in_blank_answers (flashcard_id, text)
VALUES (2, 'Blank answer 1'),
       (2, 'Blank answer 2'),
       (2, 'Blank answer 3');
