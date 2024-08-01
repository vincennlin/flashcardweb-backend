package com.vincennlin.noteservice.client;

import com.vincennlin.noteservice.payload.flashcard.dto.FlashcardDto;
import com.vincennlin.noteservice.payload.request.GenerateFlashcardRequest;
import com.vincennlin.noteservice.payload.request.GenerateFlashcardsRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "ai-ws")
public interface AiServiceClient {

    @PostMapping("/api/v1/ai/generate/flashcards")
    ResponseEntity<FlashcardDto> generateFlashcard(@RequestBody GenerateFlashcardRequest request,
                                                   @RequestHeader("Authorization") String authorization);

    @PostMapping("/api/v1/ai/generate/flashcards/bulk")
    ResponseEntity<List<FlashcardDto>> generateFlashcards(@RequestBody GenerateFlashcardsRequest request,
                                                          @RequestHeader("Authorization") String authorization);
}
