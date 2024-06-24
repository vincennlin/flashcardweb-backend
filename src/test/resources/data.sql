-- Note
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

-- Flashcard
INSERT INTO flashcards (question, answer, extra_info, date_created, last_updated, note_id)
VALUES ('Test question 1 of note 1', 'Test answer 1', 'Test extra info 1', '2024-06-22 06:55:00', '2024-06-22 06:55:00', 1);

INSERT INTO flashcards (question, answer, extra_info, date_created, last_updated, note_id)
VALUES ('Test question 2 of note 1', 'Test answer 2', 'Test extra info 2', '2024-06-22 06:55:00', '2024-06-22 06:55:00', 1);

INSERT INTO flashcards (question, answer, extra_info, date_created, last_updated, note_id)
VALUES ('Test question 3 of note 1', 'Test answer 3', 'Test extra info 3', '2024-06-22 06:55:00', '2024-06-22 06:55:00', 1);

INSERT INTO flashcards (question, answer, extra_info, date_created, last_updated, note_id)
VALUES ('Test question 1 of note 2', 'Test answer 1', 'Test extra info 1', '2024-06-22 06:55:00', '2024-06-22 06:55:00', 2);