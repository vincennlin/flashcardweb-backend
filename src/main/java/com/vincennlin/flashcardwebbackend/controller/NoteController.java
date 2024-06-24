package com.vincennlin.flashcardwebbackend.controller;

import com.vincennlin.flashcardwebbackend.payload.NoteDto;
import com.vincennlin.flashcardwebbackend.payload.NotePageResponse;
import com.vincennlin.flashcardwebbackend.service.NoteService;
import com.vincennlin.flashcardwebbackend.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "筆記 API", description = "筆記的 CRUD API")
@RestController
@Validated
@RequestMapping("/api/v1/notes")
public class NoteController {

    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @Operation(
            summary = "取得所有筆記",
            description = "取得所有筆記，並且可以加入分頁、排序等參數"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功取得所有筆記"
    )
    @GetMapping
    public ResponseEntity<NotePageResponse> getAllNotes(
            @RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) @Min(0) Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) @Max(100) @Min(1) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir) {

        Pageable pageable = PageRequest.of(pageNo, pageSize,
                sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

        NotePageResponse notePageResponse = noteService.getAllNotes(pageable);

        return new ResponseEntity<>(notePageResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "取得特定筆記",
            description = "根據 noteId 取得特定筆記"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功取得特定筆記"
    )
    @GetMapping("/{id}")
    public ResponseEntity<NoteDto> getNoteById(@PathVariable Long id) {

        NoteDto note = noteService.getNoteById(id);

        return new ResponseEntity<>(note, HttpStatus.OK);
    }

    @Operation(
            summary = "新增筆記",
            description = "新增筆記"
    )
    @ApiResponse(
            responseCode = "201",
            description = "成功新增筆記"
    )
    @PostMapping
    public ResponseEntity<NoteDto> createNote(@Valid @RequestBody NoteDto noteDto) {

        NoteDto noteResponse = noteService.createNote(noteDto);

        return new ResponseEntity<>(noteResponse, HttpStatus.CREATED);
    }

    @Operation(
            summary = "更新筆記",
            description = "根據 noteId 更新筆記"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功更新筆記"
    )
    @PutMapping("/{id}")
    public ResponseEntity<NoteDto> updateNote(@PathVariable Long id,
                                              @Valid @RequestBody NoteDto noteDto) {

        NoteDto noteResponse = noteService.updateNote(id, noteDto);

        return new ResponseEntity<>(noteResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "刪除筆記",
            description = "根據 noteId 刪除筆記"
    )
    @ApiResponse(
            responseCode = "204",
            description = "成功刪除筆記"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable Long id) {

        noteService.deleteNoteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
