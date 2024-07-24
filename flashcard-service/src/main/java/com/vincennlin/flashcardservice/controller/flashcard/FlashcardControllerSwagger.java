package com.vincennlin.flashcardservice.controller.flashcard;

import com.vincennlin.flashcardservice.payload.flashcard.dto.AbstractFlashcardDto;
import com.vincennlin.flashcardservice.payload.tag.TagDto;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "字卡 API",
        description = "字卡的 CRUD API"
)
@OpenAPIDefinition(
        info = @Info(
                title = "Flashcardweb flashcard-ws API",
                version = "1.0",
                description = "/api/v1 的前面要加上 flashcard-ws ，例如 http://localhost:8765/flashcard-ws/api/v1",
                contact = @io.swagger.v3.oas.annotations.info.Contact(
                        name = "vincennlin",
                        email = "vincentagwa@gmail.com",
                        url = "https://github.com/vincennlin"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "http://www.apache.org/licenses/LICENSE-2.0"
                )
        ),
        externalDocs = @io.swagger.v3.oas.annotations.ExternalDocumentation(
                description = "Flashcard Web Backend API Documentation",
                url = "https://github.com/vincennlin/flashcardweb-backend"
        )
)
public interface FlashcardControllerSwagger {

    @Operation(
            summary = "檢查字卡服務狀態",
            description = "檢查字卡服務是否運作正常"
    )
    @ApiResponse(
            responseCode = "200",
            description = "字卡服務運作正常",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "Flashcard Service is up and running on port 53064")
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<String> status();

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
    ResponseEntity<List<AbstractFlashcardDto>> getFlashcardsByNoteId(@PathVariable(name = "note_id") @Min(1) Long noteId);

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
    ResponseEntity<AbstractFlashcardDto> getFlashcardById(@PathVariable(name = "flashcard_id") @Min(1) Long flashcardId);

    @Operation(
            summary = "[NEW] 利用標籤查詢字卡",
            description = "根據多個標籤名稱取查詢字卡",
            parameters = {
                    @Parameter(
                            name = "tag",
                            description = "要查詢的標籤名稱，可以使用一個或多個此參數來查詢標籤，例如 api/v1/flashcards/tags?tag=台科大&tag=資料結構",
                            required = true,
                            example = "台科, 資料結構"
                    )
            }
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功取得多個標籤的所有字卡",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            [
                                {
                                    "id": 8,
                                    "question": "紅黑樹的持久版本每次插入或刪除還需要___的空間。",
                                    "type": "FILL_IN_THE_BLANK",
                                    "tags": [
                                        {
                                            "id": 5,
                                            "tag_name": "資料結構"
                                        },
                                        {
                                            "id": 7,
                                            "tag_name": "樹"
                                        }
                                    ],
                                    "note_id": 1,
                                    "user_id": 2,
                                    "in_blank_answers": [
                                        {
                                            "id": 8,
                                            "text": "O(log n)"
                                        }
                                    ],
                                    "full_answer": "紅黑樹的持久版本每次插入或刪除還需要O(log n)的空間。"
                                },
                                {
                                    "id": 7,
                                    "question": "紅黑樹和AVL樹一樣，都在插入時間、刪除時間和搜尋時間方面提供了最好的___保證。這使它們在___應用中有價值。",
                                    "type": "FILL_IN_THE_BLANK",
                                    "tags": [
                                        {
                                            "id": 5,
                                            "tag_name": "資料結構"
                                        },
                                        {
                                            "id": 7,
                                            "tag_name": "樹"
                                        },
                                        {
                                            "id": 8,
                                            "tag_name": "台科"
                                        }
                                    ],
                                    "note_id": 1,
                                    "user_id": 2,
                                    "in_blank_answers": [
                                        {
                                            "id": 6,
                                            "text": "最壞情況"
                                        },
                                        {
                                            "id": 7,
                                            "text": "時間敏感"
                                        }
                                    ],
                                    "full_answer": "紅黑樹和AVL樹一樣，都在插入時間、刪除時間和搜尋時間方面提供了最好的最壞情況保證。這使它們在時間敏感應用中有價值。"
                                }
                            ]
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<List<AbstractFlashcardDto>> getFlashcardsByTagNames(@RequestParam(name = "tag") List<String> tagNames);

    @Operation(
            summary = "新增字卡",
            description = "根據 note_id 新增字卡"
    )
    @ApiResponse(
            responseCode = "201",
            description = "成功新增字卡",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "id": 1,
                                "question": "紅黑樹的主要特點是什麼？",
                                "type": "SHORT_ANSWER",
                                "note_id": 1,
                                "user_id": 1,
                                "short_answer": "紅黑樹在插入、刪除和搜尋時間方面提供最壞情況保證，並且是持久資料結構，能保持歷史版本。"
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<AbstractFlashcardDto> createFlashcard(@PathVariable(name = "note_id") @Min(1) Long noteId,
                                                                @Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                        content = @Content(
                                                                                mediaType = "application/json",
                                                                                examples = @ExampleObject(value = """
                                                                                {
                                                                                    "type": "SHORT_ANSWER",
                                                                                    "question": "紅黑樹的主要特點是什麼？",
                                                                                    "short_answer": "紅黑樹在插入、刪除和搜尋時間方面提供最壞情況保證，並且是持久資料結構，能保持歷史版本。"
                                                                                }
                                                                                """)
                                                                        )
                                                                ) AbstractFlashcardDto flashcardDto);

    @Operation(
            summary = "新增多張字卡",
            description = "根據 note_id 新增多張字卡"
    )
    @ApiResponse(
            responseCode = "201",
            description = "成功新增多張字卡",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            [
                                 {
                                     "id": 5,
                                     "question": "紅黑樹的主要特點是什麼？",
                                     "type": "SHORT_ANSWER",
                                     "note_id": 1,
                                     "user_id": 1,
                                     "short_answer": "紅黑樹在插入、刪除和搜尋時間方面提供最壞情況保證，並且是持久資料結構，能保持歷史版本。"
                                 },
                                 {
                                     "id": 6,
                                     "question": "紅黑樹和AVL樹都提供了最好的最壞情況保證，這使得它們有價值於___、___和___等時間敏感的應用。",
                                     "type": "FILL_IN_THE_BLANK",
                                     "note_id": 1,
                                     "user_id": 1,
                                     "in_blank_answers": [
                                         {
                                             "id": 4,
                                             "text": "即時應用"
                                         },
                                         {
                                             "id": 5,
                                             "text": "計算幾何"
                                         },
                                         {
                                             "id": 6,
                                             "text": "持久資料結構"
                                         }
                                     ],
                                     "full_answer": "紅黑樹和AVL樹都提供了最好的最壞情況保證，這使得它們有價值於即時應用、計算幾何和持久資料結構等時間敏感的應用。"
                                 },
                                 {
                                     "id": 7,
                                     "question": "紅黑樹在函數式程式設計中不常用。這句話是？",
                                     "type": "TRUE_FALSE",
                                     "note_id": 1,
                                     "user_id": 1,
                                     "true_false_answer": false
                                 },
                                 {
                                     "id": 8,
                                     "question": "以下哪一個是紅黑樹的特性？",
                                     "type": "MULTIPLE_CHOICE",
                                     "options": [
                                         {
                                             "id": 5,
                                             "text": "每次操作需要 O(log n) 的時間"
                                         },
                                         {
                                             "id": 6,
                                             "text": "無法保持為以前的版本"
                                         },
                                         {
                                             "id": 7,
                                             "text": "不支持持久資料結構"
                                         },
                                         {
                                             "id": 8,
                                             "text": "只能用於靜態資料結構"
                                         }
                                     ],
                                     "note_id": 1,
                                     "user_id": 1,
                                     "answer_option": {
                                         "id": 5,
                                         "text": "每次操作需要 O(log n) 的時間"
                                     }
                                 }
                             ]
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<List<AbstractFlashcardDto>> createFlashcards(@PathVariable(name = "note_id") @Min(1) Long noteId,
                                                                       @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                               content = @Content(
                                                                                       mediaType = "application/json",
                                                                                       examples = @ExampleObject(value = """
                                                                                               [
                                                                                                   {
                                                                                                       "question": "紅黑樹的主要特點是什麼？",
                                                                                                       "type": "SHORT_ANSWER",
                                                                                                       "short_answer": "紅黑樹在插入、刪除和搜尋時間方面提供最壞情況保證，並且是持久資料結構，能保持歷史版本。"
                                                                                                   },
                                                                                                   {
                                                                                                       "question": "紅黑樹和AVL樹都提供了最好的最壞情況保證，這使得它們有價值於___、___和___等時間敏感的應用。",
                                                                                                       "type": "FILL_IN_THE_BLANK",
                                                                                                       "in_blank_answers": [
                                                                                                           {
                                                                                                               "text": "即時應用"
                                                                                                           },
                                                                                                           {
                                                                                                               "text": "計算幾何"
                                                                                                           },
                                                                                                           {
                                                                                                               "text": "持久資料結構"
                                                                                                           }
                                                                                                       ],
                                                                                                       "full_answer": "紅黑樹和AVL樹都提供了最好的最壞情況保證，這使得它們有價值於即時應用、計算幾何和持久資料結構等時間敏感的應用。"
                                                                                                   },
                                                                                                   {
                                                                                                       "question": "紅黑樹在函數式程式設計中不常用。這句話是？",
                                                                                                       "type": "TRUE_FALSE",
                                                                                                       "true_false_answer": false
                                                                                                   },
                                                                                                   {
                                                                                                       "question": "以下哪一個是紅黑樹的特性？",
                                                                                                       "type": "MULTIPLE_CHOICE",
                                                                                                       "options": [
                                                                                                           {
                                                                                                               "text": "每次操作需要 O(log n) 的時間"
                                                                                                           },
                                                                                                           {
                                                                                                               "text": "無法保持為以前的版本"
                                                                                                           },
                                                                                                           {
                                                                                                               "text": "不支持持久資料結構"
                                                                                                           },
                                                                                                           {
                                                                                                               "text": "只能用於靜態資料結構"
                                                                                                           }
                                                                                                       ],
                                                                                                       "answer_index": 1
                                                                                                   }
                                                                                               ]
                                                                                               """)
                                                                               )
                                                                       ) List<AbstractFlashcardDto> flashcardDtoList);

    @Operation(
            summary = "更新字卡",
            description = "根據 flashcard_id 更新字卡"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功更新字卡",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "id": 1,
                                "question": "（已更新）紅黑樹的主要特點是什麼？",
                                "type": "SHORT_ANSWER",
                                "note_id": 1,
                                "user_id": 1,
                                "short_answer": "(已更新) 紅黑樹在插入、刪除和搜尋時間方面提供最壞情況保證，並且是持久資料結構，能保持歷史版本。"
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<AbstractFlashcardDto> updateFlashcard(@PathVariable(name = "flashcard_id") @Min(1) Long flashcardId,
                                                                @Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                        content = @Content(
                                                                                mediaType = "application/json",
                                                                                examples = @ExampleObject(value = """
                                                                                {
                                                                                    "question": "（已更新）紅黑樹的主要特點是什麼？",
                                                                                    "short_answer": "(已更新) 紅黑樹在插入、刪除和搜尋時間方面提供最壞情況保證，並且是持久資料結構，能保持歷史版本。",
                                                                                    "type": "SHORT_ANSWER"
                                                                                }
                                                                                """)
                                                                        )
                                                                ) AbstractFlashcardDto flashcardDto);

    @Operation(
            summary = "刪除字卡",
            description = "根據 flashcard_id 刪除字卡"
    )
    @ApiResponse(
            responseCode = "204",
            description = "成功刪除字卡"
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<Void> deleteFlashcardById(@PathVariable(name = "flashcard_id") @Min(1) Long flashcardId);

    @Operation(
            summary = "刪除特定筆記的所有字卡",
            description = "根據 note_id 刪除特定筆記的所有字卡"
    )
    @ApiResponse(
            responseCode = "204",
            description = "成功刪除特定筆記的所有字卡"
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<Void> deleteFlashcardsByNoteId(@PathVariable(name = "note_id") @Min(1) Long noteId);
}
