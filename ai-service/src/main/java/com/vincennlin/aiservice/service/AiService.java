package com.vincennlin.aiservice.service;

import com.vincennlin.aiservice.payload.flashcard.dto.FlashcardDto;
import com.vincennlin.aiservice.payload.request.GenerateFlashcardRequest;
import com.vincennlin.aiservice.payload.request.GenerateFlashcardsRequest;

import java.util.List;

public interface AiService {

    String generate(String message);

    FlashcardDto generateFlashcard(GenerateFlashcardRequest request);

    List<FlashcardDto> generateFlashcards(GenerateFlashcardsRequest request);
}
