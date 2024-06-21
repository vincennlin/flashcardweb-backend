package com.vincennlin.flashcardwebbackend.controller;

import com.vincennlin.flashcardwebbackend.payload.FlashcardDto;
import com.vincennlin.flashcardwebbackend.service.FlashcardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<FlashcardDto>> getFlashcardsByNoteId(@PathVariable Long noteId) {

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
    public ResponseEntity<FlashcardDto> getFlashcardById(@PathVariable Long flashcardId) {

        FlashcardDto flashcardResponse = flashcardService.getFlashcardById(flashcardId);

        return new ResponseEntity<>(flashcardResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "新增字卡",
            description = "根據 noteId 新增字卡到特定筆記"
    )
    @ApiResponse(
            responseCode = "201",
            description = "成功新增字卡"
    )
    @PostMapping("/notes/{noteId}/flashcards")
    public ResponseEntity<FlashcardDto> createFlashcard(@PathVariable Long noteId,
                                                        @Valid @RequestBody FlashcardDto flashcardDto) {

        FlashcardDto flashcardResponse = flashcardService.createFlashcard(noteId, flashcardDto);

        return new ResponseEntity<>(flashcardResponse, HttpStatus.CREATED);
    }

    @Operation(
            summary = "更新字卡",
            description = "根據 flashcardId 更新字卡"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功更新字卡"
    )
    @PutMapping("/flashcards/{flashcardId}")
    public ResponseEntity<FlashcardDto> updateFlashcard(@PathVariable Long flashcardId,
                                                        @Valid @RequestBody FlashcardDto flashcardDto) {

        FlashcardDto flashcardResponse = flashcardService.updateFlashcard(flashcardId, flashcardDto);

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
    public ResponseEntity<Void> deleteFlashcardById(@PathVariable Long flashcardId) {

        flashcardService.deleteFlashcardById(flashcardId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
