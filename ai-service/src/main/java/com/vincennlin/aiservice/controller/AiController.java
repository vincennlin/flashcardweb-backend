package com.vincennlin.aiservice.controller;

import com.vincennlin.aiservice.payload.NoteDto;
import com.vincennlin.aiservice.payload.flashcard.FlashcardDto;
import com.vincennlin.aiservice.service.AiService;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class AiController {

    private AiService aiService;

    @GetMapping("/ai/generate")
    public ResponseEntity<String> generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {

        String generation = aiService.generate(message);

        return new ResponseEntity<>(generation, HttpStatus.OK);
    }

    @PostMapping("/ai/generate-flashcard")
    public  ResponseEntity<FlashcardDto> generateFlashcard(@RequestBody NoteDto noteDto) {

        FlashcardDto generatedFlashcard = aiService.generateFlashcard(noteDto);

        return new ResponseEntity<>(generatedFlashcard, HttpStatus.OK);
    }
}
