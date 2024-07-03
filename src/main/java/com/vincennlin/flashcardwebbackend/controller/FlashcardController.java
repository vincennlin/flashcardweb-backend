package com.vincennlin.flashcardwebbackend.controller;

import com.vincennlin.flashcardwebbackend.payload.flashcard.FlashcardDto;
import com.vincennlin.flashcardwebbackend.payload.flashcard.concrete.FillInTheBlankFlashcardDto;
import com.vincennlin.flashcardwebbackend.payload.flashcard.concrete.MultipleChoiceFlashcardDto;
import com.vincennlin.flashcardwebbackend.payload.flashcard.concrete.ShortAnswerFlashcardDto;
import com.vincennlin.flashcardwebbackend.payload.flashcard.concrete.TrueFalseFlashcardDto;
import com.vincennlin.flashcardwebbackend.service.FlashcardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "字卡 API",
        description = "字卡的 CRUD API"
)
@RestController
@Validated
@RequestMapping("/api/v1")
public class FlashcardController {

    private FlashcardService flashcardService;

    public FlashcardController(FlashcardService flashcardService) {
        this.flashcardService = flashcardService;
    }

    @Operation(
            summary = "取得特定筆記的所有字卡",
            description = "根據 noteId 取得特定筆記的所有字卡"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功取得特定筆記的所有字卡"
    )
    @GetMapping("/notes/{noteId}/flashcards")
    public ResponseEntity<List<FlashcardDto>> getFlashcardsByNoteId(@PathVariable @Min(1) Long noteId) {

        List<FlashcardDto> flashcardsResponse = flashcardService.getFlashcardsByNoteId(noteId);

        return new ResponseEntity<>(flashcardsResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "取得特定字卡",
            description = "根據 flashcardId 取得特定字卡"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功取得特定字卡"
    )
    @GetMapping("/flashcards/{flashcardId}")
    public ResponseEntity<FlashcardDto> getFlashcardById(@PathVariable @Min(1) Long flashcardId) {

        FlashcardDto flashcardResponse = flashcardService.getFlashcardById(flashcardId);

        return new ResponseEntity<>(flashcardResponse, HttpStatus.OK);
    }

    @PostMapping("/notes/{noteId}/flashcards/short-answer")
    public ResponseEntity<FlashcardDto> createShortAnswerFlashcard(@PathVariable @Min(1) Long noteId,
                                                        @Valid @RequestBody ShortAnswerFlashcardDto shortAnswerFlashcardDto){

        FlashcardDto flashcardResponse = flashcardService.createFlashcard(noteId, shortAnswerFlashcardDto);

        return new ResponseEntity<>(flashcardResponse, HttpStatus.CREATED);
    }

    @PostMapping("/notes/{noteId}/flashcards/fill-in-the-blank")
    public ResponseEntity<FlashcardDto> createFillInTheBlankFlashcard(@PathVariable @Min(1) Long noteId,
                                                        @Valid @RequestBody FillInTheBlankFlashcardDto fillInTheBlankFlashcardDto){

        FlashcardDto flashcardResponse = flashcardService.createFlashcard(noteId, fillInTheBlankFlashcardDto);

        return new ResponseEntity<>(flashcardResponse, HttpStatus.CREATED);
    }

    @PostMapping("/notes/{noteId}/flashcards/multiple-choice")
    public ResponseEntity<FlashcardDto> createMultipleChoiceFlashcard(@PathVariable @Min(1) Long noteId,
                                                        @Valid @RequestBody MultipleChoiceFlashcardDto multipleChoiceFlashcardDto){

        FlashcardDto flashcardResponse = flashcardService.createFlashcard(noteId, multipleChoiceFlashcardDto);

        return new ResponseEntity<>(flashcardResponse, HttpStatus.CREATED);
    }

    @PostMapping("/notes/{noteId}/flashcards/true-false")
    public ResponseEntity<FlashcardDto> createTrueFalseFlashcard(@PathVariable @Min(1) Long noteId,
                                                                 @Valid @RequestBody TrueFalseFlashcardDto trueFalseFlashcardDto){

        FlashcardDto flashcardResponse = flashcardService.createFlashcard(noteId, trueFalseFlashcardDto);

        return new ResponseEntity<>(flashcardResponse, HttpStatus.CREATED);
    }

    @PutMapping("/flashcards/{flashcardId}/short-answer")
    public ResponseEntity<FlashcardDto> updateShortAnswerFlashcard(@PathVariable @Min(1) Long flashcardId,
                                                        @Valid @RequestBody ShortAnswerFlashcardDto shortAnswerFlashcardDto) {

        FlashcardDto flashcardResponse = flashcardService.updateFlashcard(flashcardId, shortAnswerFlashcardDto);

        return new ResponseEntity<>(flashcardResponse, HttpStatus.OK);
    }

    @PutMapping("/flashcards/{flashcardId}/fill-in-the-blank")
    public ResponseEntity<FlashcardDto> updateFillInTheBlankFlashcard(@PathVariable @Min(1) Long flashcardId,
                                                        @Valid @RequestBody FillInTheBlankFlashcardDto fillInTheBlankFlashcardDto) {

        FlashcardDto flashcardResponse = flashcardService.updateFlashcard(flashcardId, fillInTheBlankFlashcardDto);

        return new ResponseEntity<>(flashcardResponse, HttpStatus.OK);
    }

    @PutMapping("/flashcards/{flashcardId}/multiple-choice")
    public ResponseEntity<FlashcardDto> updateMultipleChoiceFlashcard(@PathVariable @Min(1) Long flashcardId,
                                                        @Valid @RequestBody MultipleChoiceFlashcardDto multipleChoiceFlashcardDto) {

        FlashcardDto flashcardResponse = flashcardService.updateFlashcard(flashcardId, multipleChoiceFlashcardDto);

        return new ResponseEntity<>(flashcardResponse, HttpStatus.OK);
    }

    @PutMapping("/flashcards/{flashcardId}/true-false")
    public ResponseEntity<FlashcardDto> updateTrueFalseFlashcard(@PathVariable @Min(1) Long flashcardId,
                                                        @Valid @RequestBody TrueFalseFlashcardDto trueFalseFlashcardDto) {

        FlashcardDto flashcardResponse = flashcardService.updateFlashcard(flashcardId, trueFalseFlashcardDto);

        return new ResponseEntity<>(flashcardResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "刪除字卡",
            description = "根據 flashcardId 刪除字卡"
    )
    @ApiResponse(
            responseCode = "204",
            description = "成功刪除字卡"
    )
    @DeleteMapping("/flashcards/{flashcardId}")
    public ResponseEntity<Void> deleteFlashcardById(@PathVariable @Min(1) Long flashcardId) {

        flashcardService.deleteFlashcardById(flashcardId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
