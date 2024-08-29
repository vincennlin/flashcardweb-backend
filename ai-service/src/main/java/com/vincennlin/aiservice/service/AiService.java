package com.vincennlin.aiservice.service;

import com.vincennlin.aiservice.payload.evaluate.EvaluateShortAnswerResponse;
import com.vincennlin.aiservice.payload.flashcard.dto.FlashcardDto;
import com.vincennlin.aiservice.payload.evaluate.EvaluateShortAnswerRequest;
import com.vincennlin.aiservice.payload.request.GenerateFlashcardRequest;
import com.vincennlin.aiservice.payload.request.GenerateFlashcardsRequest;
import com.vincennlin.aiservice.payload.request.GenerateSummaryRequest;

import java.util.List;

public interface AiService {

    String generate(String message);

    String generateSummary(GenerateSummaryRequest request);

    FlashcardDto generateFlashcard(GenerateFlashcardRequest request);

    List<FlashcardDto> generateFlashcards(GenerateFlashcardsRequest request);

    EvaluateShortAnswerResponse evaluateShortAnswer(EvaluateShortAnswerRequest request);
}
