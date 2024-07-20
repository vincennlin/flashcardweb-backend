package com.vincennlin.aiservice.service;

import com.vincennlin.aiservice.payload.flashcard.type.FlashcardType;
import com.vincennlin.aiservice.payload.note.NoteDto;
import com.vincennlin.aiservice.payload.flashcard.dto.AbstractFlashcardDto;

public interface AiService {

    String generate(String message);

    AbstractFlashcardDto generateFlashcard(NoteDto noteDto, FlashcardType flashcardType);
}
