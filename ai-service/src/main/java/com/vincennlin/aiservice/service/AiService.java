package com.vincennlin.aiservice.service;

import com.vincennlin.aiservice.payload.flashcard.state.FlashcardState;
import com.vincennlin.aiservice.payload.note.NoteDto;
import com.vincennlin.aiservice.payload.flashcard.dto.AbstractFlashcardDto;

public interface AiService {

    String generate(String message);

    AbstractFlashcardDto generateFlashcard(NoteDto noteDto, FlashcardState flashcardState);
}
