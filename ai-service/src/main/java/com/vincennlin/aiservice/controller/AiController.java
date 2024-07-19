package com.vincennlin.aiservice.controller;

import com.vincennlin.aiservice.payload.flashcard.state.impl.FillInTheBlank;
import com.vincennlin.aiservice.payload.flashcard.state.impl.MultipleChoice;
import com.vincennlin.aiservice.payload.flashcard.state.impl.ShortAnswer;
import com.vincennlin.aiservice.payload.flashcard.state.impl.TrueFalse;
import com.vincennlin.aiservice.payload.note.NoteDto;
import com.vincennlin.aiservice.payload.flashcard.dto.AbstractFlashcardDto;
import com.vincennlin.aiservice.service.AiService;
import lombok.AllArgsConstructor;
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

    @PostMapping("/ai/generate/flashcard/short-answer")
    public  ResponseEntity<AbstractFlashcardDto> generateShortAnswerFlashcard(@RequestBody NoteDto noteDto) {

        AbstractFlashcardDto generatedFlashcard = aiService.generateFlashcard(noteDto, new ShortAnswer());

        return new ResponseEntity<>(generatedFlashcard, HttpStatus.OK);
    }

    @PostMapping("/ai/generate/flashcard/fill-in-the-blank")
    public  ResponseEntity<AbstractFlashcardDto> generateFillInTheBlankFlashcard(@RequestBody NoteDto noteDto) {

        AbstractFlashcardDto generatedFlashcard = aiService.generateFlashcard(noteDto, new FillInTheBlank());

        return new ResponseEntity<>(generatedFlashcard, HttpStatus.OK);
    }

    @PostMapping("/ai/generate/flashcard/multiple-choice")
    public  ResponseEntity<AbstractFlashcardDto> generateMultipleChoiceFlashcard(@RequestBody NoteDto noteDto) {

        AbstractFlashcardDto generatedFlashcard = aiService.generateFlashcard(noteDto, new MultipleChoice());

        return new ResponseEntity<>(generatedFlashcard, HttpStatus.OK);
    }

    @PostMapping("/ai/generate/flashcard/true-false")
    public  ResponseEntity<AbstractFlashcardDto> generateTrueFalseFlashcard(@RequestBody NoteDto noteDto) {

        AbstractFlashcardDto generatedFlashcard = aiService.generateFlashcard(noteDto, new TrueFalse());

        return new ResponseEntity<>(generatedFlashcard, HttpStatus.OK);
    }
}
