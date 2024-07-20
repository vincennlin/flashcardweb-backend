package com.vincennlin.noteservice.controller;

import com.vincennlin.noteservice.constant.AppConstants;
import com.vincennlin.noteservice.payload.note.NoteDto;
import com.vincennlin.noteservice.payload.note.NotePageResponse;
import com.vincennlin.noteservice.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "筆記 API",
        description = "筆記的 CRUD API"
)
@AllArgsConstructor
@RestController
@Validated
@RequestMapping("/api/v1")
public class NoteController {

    private Environment env;

    private NoteService noteService;

    @GetMapping("/notes/status/check")
    public ResponseEntity<String> status() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<>("Note Service is up and running on port " + env.getProperty("local.server.port"), HttpStatus.OK);
    }

    @Operation(
            summary = "取得所有筆記",
            description = "取得所有筆記，並且可以加入分頁、排序等參數"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功取得所有筆記",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "content": [
                                    {
                                        "id": 1,
                                        "content": "This is a note about Java",
                                        "date_created": "2024-07-03T22:16:07.129041",
                                        "last_updated": "2024-07-03T22:16:07.129134",
                                        "flashcards": [
                                            {
                                                "id": 1,
                                                "question": "What is Java?",
                                                "type": "SHORT_ANSWER",
                                                "note_id": 1,
                                                "short_answer": "Java is a programming language."
                                            },
                                            {
                                                "id": 2,
                                                "question": "In Java, ___ allows an object to take on many forms, enabling a single method to have ___ behaviors depending on the object's ___.",
                                                "type": "FILL_IN_THE_BLANK",
                                                "note_id": 1,
                                                "in_blank_answers": [
                                                    {
                                                        "id": 1,
                                                        "text": "polymorphism"
                                                    },
                                                    {
                                                        "id": 2,
                                                        "text": "different"
                                                    },
                                                    {
                                                        "id": 3,
                                                        "text": "type"
                                                    }
                                                ],
                                                "full_answer": "In Java, polymorphism allows an object to take on many forms, enabling a single method to have different behaviors depending on the object's type."
                                            },
                                            {
                                                "id": 3,
                                                "question": "What is Java??",
                                                "type": "MULTIPLE_CHOICE",
                                                "options": [
                                                    {
                                                        "id": 1,
                                                        "text": "Java is a programming language."
                                                    },
                                                    {
                                                        "id": 2,
                                                        "text": "Java is a type of coffee."
                                                    },
                                                    {
                                                        "id": 3,
                                                        "text": "Java is a type of tea."
                                                    },
                                                    {
                                                        "id": 4,
                                                        "text": "Java is a type of fruit."
                                                    }
                                                ],
                                                "note_id": 1,
                                                "answer_option": {
                                                    "id": 1,
                                                    "text": "Java is a programming language."
                                                }
                                            },
                                            {
                                                "id": 4,
                                                "question": "Java is an object-oriented programming language.",
                                                "type": "TRUE_FALSE",
                                                "note_id": 1,
                                                "true_false_answer": true
                                            }
                                        ]
                                    }
                                ],
                                "pageNo": 0,
                                "pageSize": 10,
                                "totalElements": 1,
                                "totalPages": 1,
                                "last": true
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/notes")
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
            summary = "取得特定使用者的筆記",
            description = "根據 user_id 取得特定使用者的筆記，並且可以加入分頁、排序等參數"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功取得特定使用者的筆記",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "content": [
                                    {
                                        "id": 1,
                                        "content": "This is a note about Java",
                                        "date_created": "2024-07-03T22:16:07.129041",
                                        "last_updated": "2024-07-03T22:16:07.129134",
                                        "flashcards": [
                                            {
                                                "id": 1,
                                                "question": "What is Java?",
                                                "type": "SHORT_ANSWER",
                                                "note_id": 1,
                                                "short_answer": "Java is a programming language."
                                            },
                                            {
                                                "id": 2,
                                                "question": "In Java, ___ allows an object to take on many forms, enabling a single method to have ___ behaviors depending on the object's ___.",
                                                "type": "FILL_IN_THE_BLANK",
                                                "note_id": 1,
                                                "in_blank_answers": [
                                                    {
                                                        "id": 1,
                                                        "text": "polymorphism"
                                                    },
                                                    {
                                                        "id": 2,
                                                        "text": "different"
                                                    },
                                                    {
                                                        "id": 3,
                                                        "text": "type"
                                                    }
                                                ],
                                                "full_answer": "In Java, polymorphism allows an object to take on many forms, enabling a single method to have different behaviors depending on the object's type."
                                            },
                                            {
                                                "id": 3,
                                                "question": "What is Java??",
                                                "type": "MULTIPLE_CHOICE",
                                                "options": [
                                                    {
                                                        "id": 1,
                                                        "text": "Java is a programming language."
                                                    },
                                                    {
                                                        "id": 2,
                                                        "text": "Java is a type of coffee."
                                                    },
                                                    {
                                                        "id": 3,
                                                        "text": "Java is a type of tea."
                                                    },
                                                    {
                                                        "id": 4,
                                                        "text": "Java is a type of fruit."
                                                    }
                                                ],
                                                "note_id": 1,
                                                "answer_option": {
                                                    "id": 1,
                                                    "text": "Java is a programming language."
                                                }
                                            },
                                            {
                                                "id": 4,
                                                "question": "Java is an object-oriented programming language.",
                                                "type": "TRUE_FALSE",
                                                "note_id": 1,
                                                "true_false_answer":
                                            }
                                        ]
                                    }
                                ],
                                "pageNo": 0,
                                "pageSize": 10,
                                "totalElements": 1,
                                "totalPages": 1,
                                "last": true
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    @PostAuthorize("principal == #userId or hasAuthority('ADVANCED')")
    @GetMapping("/notes/user/{user_id}")
    public ResponseEntity<NotePageResponse> getNotesByUserId(
            @PathVariable(name = "user_id") @Min(1) Long userId,
            @RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) @Min(0) Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) @Max(100) @Min(1) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir) {

        Pageable pageable = PageRequest.of(pageNo, pageSize,
                sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

        NotePageResponse notePageResponse = noteService.getNotesByUserId(userId, pageable);

        return new ResponseEntity<>(notePageResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "取得特定筆記",
            description = "根據 note_id 取得特定筆記"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功取得特定筆記",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "id": 1,
                                "content": "This is a note about Java",
                                "date_created": "2024-07-03T22:16:07.129041",
                                "last_updated": "2024-07-03T22:16:07.129134",
                                "flashcards": [
                                    {
                                        "id": 1,
                                        "question": "What is Java?",
                                        "type": "SHORT_ANSWER",
                                        "note_id": 1,
                                        "short_answer": "Java is a programming language."
                                    },
                                    {
                                        "id": 2,
                                        "question": "In Java, ___ allows an object to take on many forms, enabling a single method to have ___ behaviors depending on the object's ___.",
                                        "type": "FILL_IN_THE_BLANK",
                                        "note_id": 1,
                                        "in_blank_answers": [
                                            {
                                                "id": 1,
                                                "text": "polymorphism"
                                            },
                                            {
                                                "id": 2,
                                                "text": "different"
                                            },
                                            {
                                                "id": 3,
                                                "text": "type"
                                            }
                                        ],
                                        "full_answer": "In Java, polymorphism allows an object to take on many forms, enabling a single method to have different behaviors depending on the object's type."
                                    },
                                    {
                                        "id": 3,
                                        "question": "What is Java??",
                                        "type": "MULTIPLE_CHOICE",
                                        "options": [
                                            {
                                                "id": 1,
                                                "text": "Java is a programming language."
                                            },
                                            {
                                                "id": 2,
                                                "text": "Java is a type of coffee."
                                            },
                                            {
                                                "id": 3,
                                                "text": "Java is a type of tea."
                                            },
                                            {
                                                "id": 4,
                                                "text": "Java is a type of fruit."
                                            }
                                        ],
                                        "note_id": 1,
                                        "answer_option": {
                                            "id": 1,
                                            "text": "Java is a programming language."
                                        }
                                    },
                                    {
                                        "id": 4,
                                        "question": "Java is an object-oriented programming language.",
                                        "type": "TRUE_FALSE",
                                        "note_id": 1,
                                        "true_false_answer": true
                                    }
                                ]
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('READ')")
    @PostAuthorize("returnObject.body.userId == principal or hasAuthority('ADVANCED')")
    @GetMapping("/notes/{note_id}")
    public ResponseEntity<NoteDto> getNoteById(@PathVariable(name = "note_id") @Min(1) Long id) {

        NoteDto noteResponse = noteService.getNoteById(id);

        return new ResponseEntity<>(noteResponse, HttpStatus.OK);
    }

    @GetMapping("/notes/{note_id}/is-owner")
    @SecurityRequirement(name = "Bear Authentication")
    public ResponseEntity<Boolean> isNoteOwner(@PathVariable(name = "note_id") @Min(1) Long id) {
        return new ResponseEntity<>(noteService.isNoteOwner(id), HttpStatus.OK);
    }

    @Operation(
            summary = "新增筆記",
            description = "新增筆記"
    )
    @ApiResponse(
            responseCode = "201",
            description = "成功新增筆記",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "id": 1,
                                "content": "This is a note about Java",
                                "date_created": "2024-07-03T22:16:07.129041",
                                "last_updated": "2024-07-03T22:16:07.129134",
                                "flashcards": null
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('CREATE')")
    @PostMapping("/notes")
    public ResponseEntity<NoteDto> createNote(@Valid @RequestBody
                                                  @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                          content = @Content(
                                                                    mediaType = "application/json",
                                                                    examples = @ExampleObject(value = """
                                                                            {
                                                                                "content": "This is a note about Java"
                                                                            }
                                                                            """)
                                                                    )
                                                  ) NoteDto noteDto) {

        NoteDto noteResponse = noteService.createNote(noteDto);

        return new ResponseEntity<>(noteResponse, HttpStatus.CREATED);
    }

    @Operation(
            summary = "更新筆記",
            description = "根據 note_id 更新筆記"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功更新筆記",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                 "id": 1,
                                 "content": "(Updated) This is a note about Java.",
                                 "date_created": "2024-07-03T22:16:07.129041",
                                 "last_updated": "2024-07-03T22:19:34.485403",
                                 "flashcards": [
                                     {
                                         "id": 1,
                                         "question": "What is Java?",
                                         "type": "SHORT_ANSWER",
                                         "note_id": 1,
                                         "short_answer": "Java is a programming language."
                                     },
                                     {
                                         "id": 2,
                                         "question": "In Java, ___ allows an object to take on many forms, enabling a single method to have ___ behaviors depending on the object's ___.",
                                         "type": "FILL_IN_THE_BLANK",
                                         "note_id": 1,
                                         "in_blank_answers": [
                                             {
                                                 "id": 1,
                                                 "text": "polymorphism"
                                             },
                                             {
                                                 "id": 2,
                                                 "text": "different"
                                             },
                                             {
                                                 "id": 3,
                                                 "text": "type"
                                             }
                                         ],
                                         "full_answer": "In Java, polymorphism allows an object to take on many forms, enabling a single method to have different behaviors depending on the object's type."
                                     },
                                     {
                                         "id": 3,
                                         "question": "What is Java??",
                                         "type": "MULTIPLE_CHOICE",
                                         "options": [
                                             {
                                                 "id": 1,
                                                 "text": "Java is a programming language."
                                             },
                                             {
                                                 "id": 2,
                                                 "text": "Java is a type of coffee."
                                             },
                                             {
                                                 "id": 3,
                                                 "text": "Java is a type of tea."
                                             },
                                             {
                                                 "id": 4,
                                                 "text": "Java is a type of fruit."
                                             }
                                         ],
                                         "note_id": 1,
                                         "answer_option": {
                                             "id": 1,
                                             "text": "Java is a programming language."
                                         }
                                     },
                                     {
                                         "id": 4,
                                         "question": "Java is an object-oriented programming language.",
                                         "type": "TRUE_FALSE",
                                         "note_id": 1,
                                         "true_false_answer": true
                                     }
                                 ]
                             }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('UPDATE')")
    @PutMapping("/notes/{note_id}")
    public ResponseEntity<NoteDto> updateNote(@PathVariable(name = "note_id") @Min(1) Long id,
                                              @RequestHeader("Authorization") String authorization,
                                              @Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                      content = @Content(
                                                              mediaType = "application/json",
                                                              examples = @ExampleObject(value = """
                                                                      {
                                                                          "content":"(Updated) This is a note about Java."
                                                                      }
                                                                      """)
                                                      )
                                              ) NoteDto noteDto) {

        NoteDto noteResponse = noteService.updateNote(id, noteDto);

        return new ResponseEntity<>(noteResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "刪除筆記",
            description = "根據 note_id 刪除筆記"
    )
    @ApiResponse(
            responseCode = "204",
            description = "成功刪除筆記"
    )
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('DELETE')")
    @DeleteMapping("/notes/{note_id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable(name = "note_id") @Min(1) Long id) {

        noteService.deleteNoteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
