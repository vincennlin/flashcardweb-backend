package com.vincennlin.noteservice.client;

import com.vincennlin.noteservice.payload.flashcard.dto.FlashcardDto;
import com.vincennlin.noteservice.payload.flashcard.request.GenerateFlashcardRequest;
import com.vincennlin.noteservice.payload.flashcard.request.GenerateFlashcardsRequest;
import com.vincennlin.noteservice.payload.note.request.GenerateSummaryRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "ai-ws")
public interface AiServiceClient {

    @PostMapping("/api/v1/ai/generate/summary")
    ResponseEntity<String> generateSummary(@RequestBody GenerateSummaryRequest request,
                                           @RequestHeader("Authorization") String authorization);

    @PostMapping("/api/v1/ai/generate/flashcards")
    ResponseEntity<FlashcardDto> generateFlashcard(@RequestBody GenerateFlashcardRequest request,
                                                   @RequestHeader("Authorization") String authorization);

    @PostMapping("/api/v1/ai/generate/flashcards/bulk")
    ResponseEntity<List<FlashcardDto>> generateFlashcards(@RequestBody GenerateFlashcardsRequest request,
                                                          @RequestHeader("Authorization") String authorization);
}
