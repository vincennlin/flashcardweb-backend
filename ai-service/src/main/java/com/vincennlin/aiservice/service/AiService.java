package com.vincennlin.aiservice.service;

import com.vincennlin.aiservice.payload.flashcard.type.FlashcardType;
import com.vincennlin.aiservice.payload.note.NoteDto;
import com.vincennlin.aiservice.payload.flashcard.dto.AbstractFlashcardDto;
import com.vincennlin.aiservice.payload.request.GenerateFlashcardRequest;
import com.vincennlin.aiservice.payload.request.GenerateFlashcardsRequest;

import java.util.List;

public interface AiService {

    String generate(String message);

    AbstractFlashcardDto generateFlashcard(GenerateFlashcardRequest request);

    List<AbstractFlashcardDto> generateFlashcards(GenerateFlashcardsRequest request);
}
