package com.vincennlin.aiservice.controller;

import com.vincennlin.aiservice.payload.flashcard.dto.FlashcardDto;
import com.vincennlin.aiservice.payload.request.GenerateFlashcardRequest;
import com.vincennlin.aiservice.payload.request.GenerateFlashcardsRequest;
import com.vincennlin.aiservice.service.AiService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class AiController implements AiControllerSwagger{

    private AiService aiService;

    @GetMapping("/ai/generate")
    public ResponseEntity<String> generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {

        String generation = aiService.generate(message);

        return new ResponseEntity<>(generation, HttpStatus.OK);
    }

    @PostMapping("/ai/generate/flashcard")
    public  ResponseEntity<FlashcardDto> generateFlashcard(@RequestBody GenerateFlashcardRequest request) {

        FlashcardDto generatedFlashcard = aiService.generateFlashcard(request);

        return new ResponseEntity<>(generatedFlashcard, HttpStatus.OK);
    }

    @PostMapping("/ai/generate/flashcards")
    public ResponseEntity<List<FlashcardDto>> generateFlashcards(@RequestBody GenerateFlashcardsRequest request) {

        List<FlashcardDto> generatedFlashcards = aiService.generateFlashcards(request);

        return new ResponseEntity<>(generatedFlashcards, HttpStatus.OK);
    }
}
