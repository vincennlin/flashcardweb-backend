package com.vincennlin.aiservice.controller;

import com.vincennlin.aiservice.payload.flashcard.type.FlashcardType;
import com.vincennlin.aiservice.payload.note.NoteDto;
import com.vincennlin.aiservice.payload.flashcard.dto.AbstractFlashcardDto;
import com.vincennlin.aiservice.payload.request.GenerateFlashcardsRequest;
import com.vincennlin.aiservice.service.AiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Operation(
            summary = "生成一個簡答題字卡",
            description = "根據收到的筆記，生成一個簡答題字卡"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功生成簡答題字卡",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            
                            """)
            )
    )
    @PostMapping("/ai/generate/flashcard/short-answer")
    public  ResponseEntity<AbstractFlashcardDto> generateShortAnswerFlashcard(@RequestBody
                                                                                  @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                                          content = @Content(
                                                                                                  mediaType = "application/json",
                                                                                                  examples = @ExampleObject(value = """
                                                                                                          {
                                                                                                              "type": "SHORT_ANSWER"
                                                                                                          }
                                                                                                          """)
                                                                                          )
                                                                                  ) NoteDto noteDto) {

        AbstractFlashcardDto generatedFlashcard = aiService.generateFlashcard(noteDto, FlashcardType.SHORT_ANSWER);

        return new ResponseEntity<>(generatedFlashcard, HttpStatus.OK);
    }

    @PostMapping("/ai/generate/flashcard/fill-in-the-blank")
    public  ResponseEntity<AbstractFlashcardDto> generateFillInTheBlankFlashcard(@RequestBody NoteDto noteDto) {

        AbstractFlashcardDto generatedFlashcard = aiService.generateFlashcard(noteDto, FlashcardType.FILL_IN_THE_BLANK);

        return new ResponseEntity<>(generatedFlashcard, HttpStatus.OK);
    }

    @PostMapping("/ai/generate/flashcard/multiple-choice")
    public  ResponseEntity<AbstractFlashcardDto> generateMultipleChoiceFlashcard(@RequestBody NoteDto noteDto) {

        AbstractFlashcardDto generatedFlashcard = aiService.generateFlashcard(noteDto, FlashcardType.MULTIPLE_CHOICE);

        return new ResponseEntity<>(generatedFlashcard, HttpStatus.OK);
    }

    @PostMapping("/ai/generate/flashcard/true-false")
    public  ResponseEntity<AbstractFlashcardDto> generateTrueFalseFlashcard(@RequestBody NoteDto noteDto) {

        AbstractFlashcardDto generatedFlashcard = aiService.generateFlashcard(noteDto, FlashcardType.TRUE_FALSE);

        return new ResponseEntity<>(generatedFlashcard, HttpStatus.OK);
    }

    @PostMapping("/ai/generate/flashcards")
    public ResponseEntity<List<AbstractFlashcardDto>> generateFlashcards(@RequestBody GenerateFlashcardsRequest request) {

        List<AbstractFlashcardDto> generatedFlashcards = aiService.generateFlashcards(request);

        return new ResponseEntity<>(generatedFlashcards, HttpStatus.OK);
    }
}
