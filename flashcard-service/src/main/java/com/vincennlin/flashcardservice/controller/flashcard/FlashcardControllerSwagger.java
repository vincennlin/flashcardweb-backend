package com.vincennlin.flashcardservice.controller.flashcard;

import com.vincennlin.flashcardservice.constant.AppConstants;
import com.vincennlin.flashcardservice.payload.deck.FlashcardCountInfo;
import com.vincennlin.flashcardservice.payload.flashcard.dto.FlashcardDto;
import com.vincennlin.flashcardservice.payload.flashcard.page.FlashcardPageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Flashcard Controller",
        description = "字卡相關的 API"
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
            summary = "[NEW] 取得特定牌組的所有字卡",
            description = "根據 deck_id 取得特定牌組的所有字卡，取得的字卡會包含所有子牌組的字卡"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功取得特定牌組的所有字卡",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            [
                                {
                                    "id": 1,
                                    "question": "什麼是紅黑樹？",
                                    "type": "SHORT_ANSWER",
                                    "tags": [],
                                    "note_id": 1,
                                    "user_id": 2,
                                    "review_info": {
                                        "review_level": 0,
                                        "review_interval": 0,
                                        "last_reviewed": null,
                                        "next_review": "2024-08-02T23:10:16.283893"
                                    },
                                    "short_answer": "紅黑樹是一種自平衡的二叉搜尋樹，提供最好的最壞情況保證的插入、刪除和搜尋時間。"
                                },
                                {
                                    "id": 2,
                                    "question": "紅黑樹的時間複雜度為 O(___ n) 的插入和刪除操作。",
                                    "type": "FILL_IN_THE_BLANK",
                                    "tags": [],
                                    "note_id": 1,
                                    "user_id": 2,
                                    "review_info": {
                                        "review_level": 0,
                                        "review_interval": 0,
                                        "last_reviewed": null,
                                        "next_review": "2024-08-02T23:10:16.424523"
                                    },
                                    "in_blank_answers": [
                                        {
                                            "id": 1,
                                            "text": "log"
                                        }
                                    ],
                                    "full_answer": "紅黑樹的時間複雜度為 O(log n) 的插入和刪除操作。"
                                },
                                {
                                    "id": 3,
                                    "question": "紅黑樹的持久版本在每次插入或刪除後需要額外的空間複雜度為 O(n)。",
                                    "type": "MULTIPLE_CHOICE",
                                    "tags": [],
                                    "options": [
                                        {
                                            "id": 1,
                                            "text": "對"
                                        },
                                        {
                                            "id": 2,
                                            "text": "錯"
                                        },
                                        {
                                            "id": 3,
                                            "text": "無法確定"
                                        },
                                        {
                                            "id": 4,
                                            "text": "不適用"
                                        }
                                    ],
                                    "note_id": 1,
                                    "user_id": 2,
                                    "review_info": {
                                        "review_level": 0,
                                        "review_interval": 0,
                                        "last_reviewed": null,
                                        "next_review": "2024-08-02T23:10:16.455829"
                                    },
                                    "answer_option": {
                                        "id": 2,
                                        "text": "錯"
                                    }
                                },
                                {
                                    "id": 4,
                                    "question": "紅黑樹與 AVL 樹相比，提供更好的最壞時間複雜度保證。",
                                    "type": "TRUE_FALSE",
                                    "tags": [],
                                    "note_id": 1,
                                    "user_id": 2,
                                    "review_info": {
                                        "review_level": 0,
                                        "review_interval": 0,
                                        "last_reviewed": null,
                                        "next_review": "2024-08-02T23:10:16.475803"
                                    },
                                    "true_false_answer": false
                                },
                                {
                                    "id": 9,
                                    "question": "紅黑樹的主要特性是什麼？",
                                    "type": "SHORT_ANSWER",
                                    "tags": [],
                                    "note_id": 2,
                                    "user_id": 2,
                                    "review_info": {
                                        "review_level": 0,
                                        "review_interval": 0,
                                        "last_reviewed": null,
                                        "next_review": "2024-08-02T23:26:59.036686"
                                    },
                                    "short_answer": "紅黑樹在插入、刪除和搜尋時間方面提供了最好的最壞情況保證。"
                                }
                            ]
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<List<FlashcardDto>> getFlashcardsByDeckId(@PathVariable(name = "deck_id") @Min(1) Long deckId);

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
    ResponseEntity<List<FlashcardDto>> getFlashcardsByNoteId(@PathVariable(name = "note_id") @Min(1) Long noteId);

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
    ResponseEntity<FlashcardDto> getFlashcardById(@PathVariable(name = "flashcard_id") @Min(1) Long flashcardId);

    @Operation(
            summary = "利用標籤查詢字卡",
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
    ResponseEntity<List<FlashcardDto>> getFlashcardsByTagNames(@RequestParam(name = "tag") List<String> tagNames);

    @Operation(
            summary = "取得字卡數量資訊",
            description = "取得字卡數量資訊，包含每個筆記的所有字卡數量、待複習字卡數量"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功取得字卡數量資訊",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "noteIdTotalFlashcardCountMap": {
                                    "1": 4,
                                    "2": 1
                                },
                                "noteIdReviewFlashcardCountMap": {
                                    "1": 4,
                                    "2": 1
                                }
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<FlashcardCountInfo> getFlashcardCountInfo();

    @Operation(
            summary = "[NEW] 搜尋字卡",
            description = "根據關鍵字搜尋字卡，並且可以加入分頁、排序等參數"
    )
    ResponseEntity<FlashcardPageResponse> findFlashcardsByKeyword(
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) @Min(0) Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) @Max(100) @Min(1) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir);

    @Operation(
            summary = "[EDITED][路由名稱] 新增字卡",
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
    ResponseEntity<FlashcardDto> createFlashcard(@PathVariable(name = "note_id") @Min(1) Long noteId,
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
                                                                ) FlashcardDto flashcardDto);

    @Operation(
            summary = "[EDITED][路由名稱] 新增多張字卡",
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
    ResponseEntity<List<FlashcardDto>> createFlashcards(@PathVariable(name = "note_id") @Min(1) Long noteId,
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
                                                                       ) List<FlashcardDto> flashcardDtoList);

    @Operation(
            summary = "[EDITED][路由名稱] 更新字卡",
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
    ResponseEntity<FlashcardDto> updateFlashcard(@PathVariable(name = "flashcard_id") @Min(1) Long flashcardId,
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
                                                                ) FlashcardDto flashcardDto);

    @Operation(
            summary = "[EDITED][路由名稱] 刪除字卡",
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
