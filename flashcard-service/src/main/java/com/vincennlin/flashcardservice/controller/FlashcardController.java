package com.vincennlin.flashcardservice.controller;

import com.vincennlin.flashcardservice.payload.flashcard.dto.AbstractFlashcardDto;
import com.vincennlin.flashcardservice.payload.flashcard.dto.impl.FillInTheBlankFlashcardDto;
import com.vincennlin.flashcardservice.payload.flashcard.dto.impl.MultipleChoiceFlashcardDto;
import com.vincennlin.flashcardservice.payload.flashcard.dto.impl.ShortAnswerFlashcardDto;
import com.vincennlin.flashcardservice.payload.flashcard.dto.impl.TrueFalseFlashcardDto;
import com.vincennlin.flashcardservice.service.FlashcardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "字卡 API",
        description = "字卡的 CRUD API"
)
@AllArgsConstructor
@RestController
@Validated
@RequestMapping("/api/v1")
public class FlashcardController {

    private Environment env;

    private FlashcardService flashcardService;

    @GetMapping("/status/check")
    public ResponseEntity<String> status() {
        return new ResponseEntity<>("Flashcard Service is up and running on port " + env.getProperty("local.server.port"), HttpStatus.OK);
    }

    @Operation(
            summary = "取得特定筆記的所有字卡",
            description = "根據 note_id 取得特定筆記的所有字卡"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功取得特定筆記的所有字卡",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            [
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
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/notes/{note_id}/flashcards")
    public ResponseEntity<List<AbstractFlashcardDto>> getFlashcardsByNoteId(@PathVariable(name = "note_id") @Min(1) Long noteId) {

        List<AbstractFlashcardDto> flashcardsResponse = flashcardService.getFlashcardsByNoteId(noteId);

        return new ResponseEntity<>(flashcardsResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "取得特定字卡",
            description = "根據 flashcard_id 取得特定字卡"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功取得特定字卡",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "id": 1,
                                "question": "What is Java?",
                                "type": "SHORT_ANSWER",
                                "note_id": 1,
                                "short_answer": "Java is a programming language."
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('READ')")
//    @PostAuthorize("returnObject.body.userId == principal or hasAuthority('ADVANCED')")
    @GetMapping("/flashcards/{flashcard_id}")
    public ResponseEntity<AbstractFlashcardDto> getFlashcardById(@PathVariable(name = "flashcard_id") @Min(1) Long flashcardId) {

        AbstractFlashcardDto flashcardResponse = flashcardService.getFlashcardById(flashcardId);

        return new ResponseEntity<>(flashcardResponse, HttpStatus.OK);
    }



    @Operation(
            summary = "新增問答題字卡",
            description = "根據 note_id 新增問答題字卡"
    )
    @ApiResponse(
            responseCode = "201",
            description = "成功新增問答題字卡",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "id": 1,
                                "question": "What is Java?",
                                "type": "SHORT_ANSWER",
                                "note_id": 1,
                                "short_answer": "Java is a programming language."
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('CREATE')")
    @PostMapping("/notes/{note_id}/flashcards/short-answer")
    public ResponseEntity<AbstractFlashcardDto> createShortAnswerFlashcard(@PathVariable(name = "note_id") @Min(1) Long noteId,
                                                                           @Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                content = @Content(
                                                                        mediaType = "application/json",
                                                                        examples = @ExampleObject(value = """
                                                                                {
                                                                                    "question": "What is Java?",
                                                                                    "short_answer": "Java is a programming language."
                                                                                }
                                                                                """)
                                                                )
                                                        ) ShortAnswerFlashcardDto shortAnswerFlashcardDto){

        AbstractFlashcardDto flashcardResponse = flashcardService.createFlashcard(noteId, shortAnswerFlashcardDto);

        return new ResponseEntity<>(flashcardResponse, HttpStatus.CREATED);
    }

    @Operation(
            summary = "新增填空題字卡",
            description = "根據 note_id 新增填空題字卡"
    )
    @ApiResponse(
            responseCode = "201",
            description = "成功新增填空題字卡",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
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
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('CREATE')")
    @PostMapping("/notes/{note_id}/flashcards/fill-in-the-blank")
    public ResponseEntity<AbstractFlashcardDto> createFillInTheBlankFlashcard(@PathVariable(name = "note_id") @Min(1) Long noteId,
                                                                              @Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                content = @Content(
                                                                        mediaType = "application/json",
                                                                        examples = @ExampleObject(value = """
                                                                                {
                                                                                    "question": "In Java, ___ allows an object to take on many forms, enabling a single method to have ___ behaviors depending on the object's ___.",
                                                                                    "in_blank_answers": [
                                                                                        {
                                                                                            "text": "polymorphism"
                                                                                        },
                                                                                        {
                                                                                            "text": "different"
                                                                                        },
                                                                                        {
                                                                                            "text": "type"
                                                                                        }
                                                                                    ],
                                                                                    "full_answer": "In Java, polymorphism allows an object to take on many forms, enabling a single method to have different behaviors depending on the object's type.",
                                                                                    "type": "FILL_IN_THE_BLANK"
                                                                                }
                                                                                """)
                                                                )
                                                        ) FillInTheBlankFlashcardDto fillInTheBlankFlashcardDto){

        AbstractFlashcardDto flashcardResponse = flashcardService.createFlashcard(noteId, fillInTheBlankFlashcardDto);

        return new ResponseEntity<>(flashcardResponse, HttpStatus.CREATED);
    }

    @Operation(
            summary = "新增選擇題字卡",
            description = "根據 note_id 新增選擇題字卡"
    )
    @ApiResponse(
            responseCode = "201",
            description = "成功新增選擇題字卡",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
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
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('CREATE')")
    @PostMapping("/notes/{note_id}/flashcards/multiple-choice")
    public ResponseEntity<AbstractFlashcardDto> createMultipleChoiceFlashcard(@PathVariable(name = "note_id") @Min(1) Long noteId,
                                                                              @Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                content = @Content(
                                                                        mediaType = "application/json",
                                                                        examples = @ExampleObject(value = """
                                                                                {
                                                                                    "question": "What is Java??",
                                                                                    "options": [
                                                                                        {
                                                                                            "text": "Java is a programming language."
                                                                                        },
                                                                                        {
                                                                                            "text": "Java is a type of coffee."
                                                                                        },
                                                                                        {
                                                                                            "text": "Java is a type of tea."
                                                                                        },
                                                                                        {
                                                                                            "text": "Java is a type of fruit."
                                                                                        }
                                                                                    ],
                                                                                    "answer_index": 1
                                                                                }
                                                                                """)
                                                                )
                                                        ) MultipleChoiceFlashcardDto multipleChoiceFlashcardDto){

        AbstractFlashcardDto flashcardResponse = flashcardService.createFlashcard(noteId, multipleChoiceFlashcardDto);

        return new ResponseEntity<>(flashcardResponse, HttpStatus.CREATED);
    }

    @Operation(
            summary = "新增是非題字卡",
            description = "根據 note_id 新增是非題字卡"
    )
    @ApiResponse(
            responseCode = "201",
            description = "成功新增是非題字卡",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "id": 4,
                                "question": "Java is an object-oriented programming language.",
                                "type": "TRUE_FALSE",
                                "note_id": 1,
                                "true_false_answer": true
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('CREATE')")
    @PostMapping("/notes/{note_id}/flashcards/true-false")
    public ResponseEntity<AbstractFlashcardDto> createTrueFalseFlashcard(@PathVariable(name = "note_id") @Min(1) Long noteId,
                                                                         @Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                         content = @Content(
                                                                                 mediaType = "application/json",
                                                                                 examples = @ExampleObject(value = """
                                                                                         {
                                                                                             "question": "Java is an object-oriented programming language.",
                                                                                             "true_false_answer": true
                                                                                         }
                                                                                         """)
                                                                         )
                                                                 ) TrueFalseFlashcardDto trueFalseFlashcardDto){

        AbstractFlashcardDto flashcardResponse = flashcardService.createFlashcard(noteId, trueFalseFlashcardDto);

        return new ResponseEntity<>(flashcardResponse, HttpStatus.CREATED);
    }

    @Operation(
            summary = "更新問答題字卡",
            description = "根據 flashcard_id 更新問答題字卡"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功更新問答題字卡",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "id": 1,
                                "question": "(Updated) What is Java?",
                                "type": "SHORT_ANSWER",
                                "note_id": 1,
                                "short_answer": "(Updated) A programming language!"
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('UPDATE')")
    @PutMapping("/flashcards/{flashcard_id}/short-answer")
    public ResponseEntity<AbstractFlashcardDto> updateShortAnswerFlashcard(@PathVariable(name = "flashcard_id") @Min(1) Long flashcardId,
                                                                           @Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                content = @Content(
                                                                        mediaType = "application/json",
                                                                        examples = @ExampleObject(value = """
                                                                                {
                                                                                    "question": "(Updated) What is Java?",
                                                                                    "short_answer": "(Updated) A programming language!"
                                                                                }
                                                                                """)
                                                                )
                                                        ) ShortAnswerFlashcardDto shortAnswerFlashcardDto) {

        AbstractFlashcardDto flashcardResponse = flashcardService.updateFlashcard(flashcardId, shortAnswerFlashcardDto);

        return new ResponseEntity<>(flashcardResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "更新填空題字卡",
            description = "根據 flashcard_id 更新填空題字卡"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功更新填空題字卡",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "id": 2,
                                "question": "(Updated) In Java, ___ allows an object to take on many forms, enabling a single method to have ___ behaviors depending on the object's ___.",
                                "type": "FILL_IN_THE_BLANK",
                                "note_id": 1,
                                "in_blank_answers": [
                                    {
                                        "id": 1,
                                        "text": "(Updated) polymorphism"
                                    },
                                    {
                                        "id": 2,
                                        "text": "(Updated) different"
                                    },
                                    {
                                        "id": 3,
                                        "text": "(Updated) type"
                                    }
                                ],
                                "full_answer": "(Updated) In Java, polymorphism allows an object to take on many forms, enabling a single method to have different behaviors depending on the object's type."
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('UPDATE')")
    @PutMapping("/flashcards/{flashcard_id}/fill-in-the-blank")
    public ResponseEntity<AbstractFlashcardDto> updateFillInTheBlankFlashcard(@PathVariable(name = "flashcard_id") @Min(1) Long flashcardId,
                                                                              @Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                content = @Content(
                                                                        mediaType = "application/json",
                                                                        examples = @ExampleObject(value = """
                                                                                {
                                                                                    "question": "(Updated) In Java, ___ allows an object to take on many forms, enabling a single method to have ___ behaviors depending on the object's ___.",
                                                                                    "in_blank_answers": [
                                                                                        {
                                                                                            "text": "(Updated) polymorphism"
                                                                                        },
                                                                                        {
                                                                                            "text": "(Updated) different"
                                                                                        },
                                                                                        {
                                                                                            "text": "(Updated) type"
                                                                                        }
                                                                                    ],
                                                                                    "full_answer": "(Updated) In Java, polymorphism allows an object to take on many forms, enabling a single method to have different behaviors depending on the object's type."
                                                                                }
                                                                                """)
                                                                )
                                                        ) FillInTheBlankFlashcardDto fillInTheBlankFlashcardDto) {

        AbstractFlashcardDto flashcardResponse = flashcardService.updateFlashcard(flashcardId, fillInTheBlankFlashcardDto);

        return new ResponseEntity<>(flashcardResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "更新選擇題字卡",
            description = "根據 flashcard_id 更新選擇題字卡"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功更新選擇題字卡",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "id": 3,
                                "question": "(Updated) What is Java??",
                                "type": "MULTIPLE_CHOICE",
                                "options": [
                                    {
                                        "id": 1,
                                        "text": "(Updated) Java is a programming language."
                                    },
                                    {
                                        "id": 2,
                                        "text": "(Updated) Java is a type of coffee."
                                    },
                                    {
                                        "id": 3,
                                        "text": "(Updated) Java is a type of tea."
                                    },
                                    {
                                        "id": 4,
                                        "text": "(Updated) Java is a type of fruit."
                                    }
                                ],
                                "note_id": 1,
                                "answer_option": {
                                    "id": 2,
                                    "text": "(Updated) Java is a type of coffee."
                                }
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('UPDATE')")
    @PutMapping("/flashcards/{flashcard_id}/multiple-choice")
    public ResponseEntity<AbstractFlashcardDto> updateMultipleChoiceFlashcard(@PathVariable(name = "flashcard_id") @Min(1) Long flashcardId,
                                                                              @Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                content = @Content(
                                                                        mediaType = "application/json",
                                                                        examples = @ExampleObject(value = """
                                                                                {
                                                                                    "question": "(Updated) What is Java??",
                                                                                    "options": [
                                                                                        {
                                                                                            "text": "(Updated) Java is a programming language."
                                                                                        },
                                                                                        {
                                                                                            "text": "(Updated) Java is a type of coffee."
                                                                                        },
                                                                                        {
                                                                                            "text": "(Updated) Java is a type of tea."
                                                                                        },
                                                                                        {
                                                                                            "text": "(Updated) Java is a type of fruit."
                                                                                        }
                                                                                    ],
                                                                                    "answer_index": 2
                                                                                }
                                                                                """)
                                                                )
                                                        ) MultipleChoiceFlashcardDto multipleChoiceFlashcardDto) {

        AbstractFlashcardDto flashcardResponse = flashcardService.updateFlashcard(flashcardId, multipleChoiceFlashcardDto);

        return new ResponseEntity<>(flashcardResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "更新是非題字卡",
            description = "根據 flashcard_id 更新是非題字卡"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功更新是非題字卡",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "id": 4,
                                "question": "(Updated) Java is an object-oriented programming language.",
                                "type": "TRUE_FALSE",
                                "note_id": 1,
                                "true_false_answer": false
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('UPDATE')")
    @PutMapping("/flashcards/{flashcard_id}/true-false")
    public ResponseEntity<AbstractFlashcardDto> updateTrueFalseFlashcard(@PathVariable(name = "flashcard_id") @Min(1) Long flashcardId,
                                                                         @Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                content = @Content(
                                                                        mediaType = "application/json",
                                                                        examples = @ExampleObject(value = """
                                                                                {
                                                                                    "question": "(Updated) Java is an object-oriented programming language.",
                                                                                    "true_false_answer": false
                                                                                }
                                                                                """)
                                                                )
                                                        ) TrueFalseFlashcardDto trueFalseFlashcardDto) {

        AbstractFlashcardDto flashcardResponse = flashcardService.updateFlashcard(flashcardId, trueFalseFlashcardDto);

        return new ResponseEntity<>(flashcardResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "刪除字卡",
            description = "根據 flashcard_id 刪除字卡"
    )
    @ApiResponse(
            responseCode = "204",
            description = "成功刪除字卡"
    )
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('DELETE')")
    @DeleteMapping("/flashcards/{flashcard_id}")
    public ResponseEntity<Void> deleteFlashcardById(@PathVariable(name = "flashcard_id") @Min(1) Long flashcardId) {

        flashcardService.deleteFlashcardById(flashcardId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
