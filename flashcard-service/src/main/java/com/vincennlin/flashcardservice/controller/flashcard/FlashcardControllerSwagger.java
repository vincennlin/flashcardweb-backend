package com.vincennlin.flashcardservice.controller.flashcard;

import com.vincennlin.flashcardservice.constant.AppConstants;
import com.vincennlin.flashcardservice.payload.deck.FlashcardCountInfo;
import com.vincennlin.flashcardservice.payload.flashcard.dto.FlashcardDto;
import com.vincennlin.flashcardservice.payload.flashcard.evaluate.EvaluateShortAnswerRequest;
import com.vincennlin.flashcardservice.payload.flashcard.evaluate.EvaluateShortAnswerResponse;
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
import org.springframework.http.HttpStatus;
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
            summary = "[EDITED] 取得特定牌組的所有字卡",
            description = "根據 deck_id 取得特定牌組的所有字卡，取得的字卡會包含所有子牌組的字卡，並且可以加入分頁、排序等參數，例如想要每頁數量為 100，可以送出 /api/v1/flashcards/decks/{deck_id}/flashcards?pageSize=100",
            parameters = {
                    @Parameter(name = "pageNo", description = "頁碼", example = "0"),
                    @Parameter(name = "pageSize", description = "每頁筆記數量", example = "10"),
                    @Parameter(name = "sortBy", description = "排序欄位", example = "dateCreated"),
                    @Parameter(name = "sortDir", description = "排序方向", example = "desc")
            }
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功取得特定牌組的所有字卡",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "content": [
                                    {
                                        "id": 29,
                                        "question": "紅黑樹的主要特點是什麼？",
                                        "type": "SHORT_ANSWER",
                                        "tags": [],
                                        "note_id": 2,
                                        "user_id": 2,
                                        "review_info": {
                                            "review_level": 1,
                                            "review_interval": 1,
                                            "last_reviewed": "2024-08-20T12:04:02.189138",
                                            "next_review": "2024-08-21T12:04:02.189138"
                                        },
                                        "short_answer": "紅黑樹在插入、刪除和搜尋時間方面提供最壞情況保證，並且是持久資料結構，能保持歷史版本。"
                                    },
                                    {
                                        "id": 30,
                                        "question": "In Java, ___ allows an object to take on many forms, enabling a single method to have ___ behaviors depending on the object's ___.",
                                        "type": "FILL_IN_THE_BLANK",
                                        "tags": [],
                                        "note_id": 2,
                                        "user_id": 2,
                                        "review_info": {
                                            "review_level": 1,
                                            "review_interval": 1,
                                            "last_reviewed": "2024-08-20T12:05:00.988358",
                                            "next_review": "2024-08-21T12:05:00.988358"
                                        },
                                        "in_blank_answers": [
                                            {
                                                "id": 16,
                                                "text": "polymorphism"
                                            },
                                            {
                                                "id": 17,
                                                "text": "different"
                                            },
                                            {
                                                "id": 18,
                                                "text": "type"
                                            }
                                        ],
                                        "full_answer": "In Java, polymorphism allows an object to take on many forms, enabling a single method to have different behaviors depending on the object's type."
                                    },
                                    {
                                        "id": 31,
                                        "question": "Which of the following is not a principle of object-oriented programming?",
                                        "type": "MULTIPLE_CHOICE",
                                        "tags": [],
                                        "options": [
                                            {
                                                "id": 29,
                                                "text": "Encapsulation"
                                            },
                                            {
                                                "id": 30,
                                                "text": "Polymorphism"
                                            },
                                            {
                                                "id": 31,
                                                "text": "Abstraction"
                                            },
                                            {
                                                "id": 32,
                                                "text": "Inheritance"
                                            }
                                        ],
                                        "note_id": 2,
                                        "user_id": 2,
                                        "review_info": {
                                            "review_level": 1,
                                            "review_interval": 1,
                                            "last_reviewed": "2024-08-20T12:05:02.065233",
                                            "next_review": "2024-08-21T12:05:02.065233"
                                        },
                                        "answer_option": {
                                            "id": 29,
                                            "text": "Encapsulation"
                                        }
                                    },
                                    {
                                        "id": 32,
                                        "question": "Java is an object-oriented programming language.",
                                        "type": "TRUE_FALSE",
                                        "tags": [],
                                        "note_id": 2,
                                        "user_id": 2,
                                        "review_info": {
                                            "review_level": 1,
                                            "review_interval": 1,
                                            "last_reviewed": "2024-08-20T12:05:03.068624",
                                            "next_review": "2024-08-21T12:05:03.068624"
                                        },
                                        "true_false_answer": true
                                    },
                                    {
                                        "id": 53,
                                        "question": "紅黑樹和 AVL 樹的主要相似之處是什麼？",
                                        "type": "SHORT_ANSWER",
                                        "tags": [],
                                        "note_id": 9,
                                        "user_id": 2,
                                        "review_info": {
                                            "review_level": 0,
                                            "review_interval": 0,
                                            "last_reviewed": null,
                                            "next_review": "2024-08-17T06:07:53.017428"
                                        },
                                        "short_answer": "紅黑樹和 AVL 樹都提供了最好的最壞情況保證，適合時間敏感的應用。"
                                    },
                                    {
                                        "id": 54,
                                        "question": "紅黑樹在函數式程式設計中有什麼用途？",
                                        "type": "SHORT_ANSWER",
                                        "tags": [],
                                        "note_id": 9,
                                        "user_id": 2,
                                        "review_info": {
                                            "review_level": 0,
                                            "review_interval": 0,
                                            "last_reviewed": null,
                                            "next_review": "2024-08-17T06:07:53.110663"
                                        },
                                        "short_answer": "紅黑樹是最常用的持久資料結構之一，用來構造關聯陣列和集合。"
                                    },
                                    {
                                        "id": 55,
                                        "question": "紅黑樹的持久版本在每次插入或刪除後需要多少空間？",
                                        "type": "FILL_IN_THE_BLANK",
                                        "tags": [],
                                        "note_id": 9,
                                        "user_id": 2,
                                        "review_info": {
                                            "review_level": 0,
                                            "review_interval": 0,
                                            "last_reviewed": null,
                                            "next_review": "2024-08-17T06:07:53.120866"
                                        },
                                        "in_blank_answers": [
                                            {
                                                "id": 29,
                                                "text": "O(log n)"
                                            },
                                            {
                                                "id": 30,
                                                "text": "O(log n)"
                                            }
                                        ],
                                        "full_answer": "紅黑樹的持久版本每次插入或刪除需要 O(log n) 的空間。"
                                    },
                                    {
                                        "id": 56,
                                        "question": "紅黑樹的持久版本在每次插入或刪除後需要多少時間？",
                                        "type": "FILL_IN_THE_BLANK",
                                        "tags": [],
                                        "note_id": 9,
                                        "user_id": 2,
                                        "review_info": {
                                            "review_level": 0,
                                            "review_interval": 0,
                                            "last_reviewed": null,
                                            "next_review": "2024-08-17T06:07:53.132388"
                                        },
                                        "in_blank_answers": [
                                            {
                                                "id": 31,
                                                "text": "O(log n)"
                                            },
                                            {
                                                "id": 32,
                                                "text": "O(log n)"
                                            }
                                        ],
                                        "full_answer": "紅黑樹的持久版本每次插入或刪除需要 O(log n) 的時間。"
                                    },
                                    {
                                        "id": 57,
                                        "question": "紅黑樹是2-3-4樹的一種等價結構嗎？",
                                        "type": "TRUE_FALSE",
                                        "tags": [],
                                        "note_id": 9,
                                        "user_id": 2,
                                        "review_info": {
                                            "review_level": 0,
                                            "review_interval": 0,
                                            "last_reviewed": null,
                                            "next_review": "2024-08-17T06:07:53.144195"
                                        },
                                        "true_false_answer": true
                                    },
                                    {
                                        "id": 58,
                                        "question": "紅黑樹在實踐中比2-3-4樹更常使用。",
                                        "type": "TRUE_FALSE",
                                        "tags": [],
                                        "note_id": 9,
                                        "user_id": 2,
                                        "review_info": {
                                            "review_level": 0,
                                            "review_interval": 0,
                                            "last_reviewed": null,
                                            "next_review": "2024-08-17T06:07:53.154192"
                                        },
                                        "true_false_answer": true
                                    }
                                ],
                                "pageNo": 0,
                                "pageSize": 10,
                                "totalElements": 81,
                                "totalPages": 9,
                                "last": false
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<FlashcardPageResponse> getFlashcardsByDeckId(
            @PathVariable(name = "deck_id") @Min(1) Long deckId,
            @RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) @Min(0) Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) @Max(100) @Min(1) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir);

    @Operation(
            summary = "[EDITED] 取得特定筆記的所有字卡",
            description = "根據 note_id 取得特定筆記的所有字卡，並且可以加入分頁、排序等參數，例如想要每頁數量為 100，可以送出 /api/v1/flashcards/notes/{note_id}/flashcards?pageSize=100",
            parameters = {
                    @Parameter(name = "pageNo", description = "頁碼", example = "0"),
                    @Parameter(name = "pageSize", description = "每頁筆記數量", example = "10"),
                    @Parameter(name = "sortBy", description = "排序欄位", example = "dateCreated"),
                    @Parameter(name = "sortDir", description = "排序方向", example = "desc")
            }
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功取得特定筆記的所有字卡",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "content": [
                                    {
                                        "id": 126,
                                        "question": "LinkedList 類別中，如何初始化 head 和 tail 節點？",
                                        "type": "SHORT_ANSWER",
                                        "tags": [],
                                        "note_id": 11,
                                        "user_id": 2,
                                        "review_info": {
                                            "review_level": 0,
                                            "review_interval": 0,
                                            "last_reviewed": null,
                                            "next_review": "2024-08-20T12:06:08.2231"
                                        },
                                        "short_answer": "在 LinkedList 的建構子中，初始化 head 和 tail 為同一個新節點。"
                                    },
                                    {
                                        "id": 127,
                                        "question": "在 LinkedList 類別中，使用何種算法來尋找中間節點？",
                                        "type": "FILL_IN_THE_BLANK",
                                        "tags": [],
                                        "note_id": 11,
                                        "user_id": 2,
                                        "review_info": {
                                            "review_level": 0,
                                            "review_interval": 0,
                                            "last_reviewed": null,
                                            "next_review": "2024-08-20T12:06:08.250906"
                                        },
                                        "in_blank_answers": [
                                            {
                                                "id": 79,
                                                "text": "快慢指針"
                                            },
                                            {
                                                "id": 80,
                                                "text": "遍歷"
                                            },
                                            {
                                                "id": 81,
                                                "text": "鏈結"
                                            }
                                        ],
                                        "full_answer": "在 LinkedList 類別中，使用快慢指針算法來尋找中間節點。"
                                    },
                                    {
                                        "id": 128,
                                        "question": "LinkedList 類別的 append 方法的主要功能是：___、___、___。",
                                        "type": "FILL_IN_THE_BLANK",
                                        "tags": [],
                                        "note_id": 11,
                                        "user_id": 2,
                                        "review_info": {
                                            "review_level": 0,
                                            "review_interval": 0,
                                            "last_reviewed": null,
                                            "next_review": "2024-08-20T12:06:08.263568"
                                        },
                                        "in_blank_answers": [
                                            {
                                                "id": 82,
                                                "text": "在尾部新增節點"
                                            },
                                            {
                                                "id": 83,
                                                "text": "更新尾部指針"
                                            },
                                            {
                                                "id": 84,
                                                "text": "處理空鏈表的情況"
                                            }
                                        ],
                                        "full_answer": "LinkedList 類別的 append 方法的主要功能是：在尾部新增節點、更新尾部指針、處理空鏈表的情況。"
                                    },
                                    {
                                        "id": 129,
                                        "question": "LinkedList 類別中，findMiddleNode 方法使用了快慢指針的方式來找中間節點。",
                                        "type": "TRUE_FALSE",
                                        "tags": [],
                                        "note_id": 11,
                                        "user_id": 2,
                                        "review_info": {
                                            "review_level": 0,
                                            "review_interval": 0,
                                            "last_reviewed": null,
                                            "next_review": "2024-08-20T12:06:08.27518"
                                        },
                                        "true_false_answer": true
                                    },
                                    {
                                        "id": 130,
                                        "question": "LinkedList 的 makeEmpty 方法會將 head 和 tail 設為 null。",
                                        "type": "TRUE_FALSE",
                                        "tags": [],
                                        "note_id": 11,
                                        "user_id": 2,
                                        "review_info": {
                                            "review_level": 0,
                                            "review_interval": 0,
                                            "last_reviewed": null,
                                            "next_review": "2024-08-20T12:06:08.287119"
                                        },
                                        "true_false_answer": true
                                    }
                                ],
                                "pageNo": 0,
                                "pageSize": 10,
                                "totalElements": 5,
                                "totalPages": 1,
                                "last": true
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<FlashcardPageResponse> getFlashcardsByNoteId(
            @PathVariable(name = "note_id") @Min(1) Long noteId,
            @RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) @Min(0) Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) @Max(100) @Min(1) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir);

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
            summary = "[EDITED] 利用標籤查詢字卡",
            description = "根據多個標籤名稱取查詢字卡，並且可以加入分頁、排序等參數，例如想要每頁數量為 100，可以送出 /api/v1/flashcards/tags?tag=台科大&tag=資料結構&pageSize=100",
            parameters = {
                    @Parameter(
                            name = "tag",
                            description = "要查詢的標籤名稱，可以使用一個或多個此參數來查詢標籤，例如 api/v1/flashcards/tags?tag=台科大&tag=資料結構",
                            required = true,
                            example = "台科, 資料結構"
                    ),
                    @Parameter(name = "pageNo", description = "頁碼", example = "0"),
                    @Parameter(name = "pageSize", description = "每頁筆記數量", example = "10"),
                    @Parameter(name = "sortBy", description = "排序欄位", example = "dateCreated"),
                    @Parameter(name = "sortDir", description = "排序方向", example = "desc")

            }
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功取得多個標籤的所有字卡",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "content": [
                                    {
                                        "id": 29,
                                        "question": "紅黑樹的主要特點是什麼？",
                                        "type": "SHORT_ANSWER",
                                        "tags": [
                                            {
                                                "id": 5,
                                                "tag_name": "台科大"
                                            }
                                        ],
                                        "note_id": 2,
                                        "user_id": 2,
                                        "review_info": {
                                            "review_level": 1,
                                            "review_interval": 1,
                                            "last_reviewed": "2024-08-20T12:04:02.189138",
                                            "next_review": "2024-08-21T12:04:02.189138"
                                        },
                                        "short_answer": "紅黑樹在插入、刪除和搜尋時間方面提供最壞情況保證，並且是持久資料結構，能保持歷史版本。"
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
    ResponseEntity<FlashcardPageResponse> getFlashcardsByTagNames(
            @RequestParam(name = "tag") List<String> tagNames,
            @RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) @Min(0) Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) @Max(100) @Min(1) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir);

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
            description = "根據關鍵字搜尋字卡，並且可以加入分頁、排序等參數，例如想要每頁數量為 100，可以送出 /api/v1/flashcards/search?keyword=紅黑樹&pageSize=100",
            parameters = {
                    @Parameter(
                            name = "keyword",
                            description = "要查詢的關鍵字名稱，例如 api/v1/flashcards/search?keyword=紅黑樹",
                            required = true,
                            example = "紅黑樹"
                    ),
                    @Parameter(name = "pageNo", description = "頁碼", example = "0"),
                    @Parameter(name = "pageSize", description = "每頁筆記數量", example = "10"),
                    @Parameter(name = "sortBy", description = "排序欄位", example = "dateCreated"),
                    @Parameter(name = "sortDir", description = "排序方向", example = "desc")
            }
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功搜尋字卡",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "content": [
                                    {
                                        "id": 29,
                                        "question": "紅黑樹的主要特點是什麼？",
                                        "type": "SHORT_ANSWER",
                                        "tags": [
                                            {
                                                "id": 5,
                                                "tag_name": "台科大"
                                            }
                                        ],
                                        "note_id": 2,
                                        "user_id": 2,
                                        "review_info": {
                                            "review_level": 1,
                                            "review_interval": 1,
                                            "last_reviewed": "2024-08-20T12:04:02.189138",
                                            "next_review": "2024-08-21T12:04:02.189138"
                                        },
                                        "short_answer": "紅黑樹在插入、刪除和搜尋時間方面提供最壞情況保證，並且是持久資料結構，能保持歷史版本。"
                                    },
                                    {
                                        "id": 33,
                                        "question": "什麼是 Docker image？",
                                        "type": "SHORT_ANSWER",
                                        "tags": [],
                                        "note_id": 3,
                                        "user_id": 2,
                                        "review_info": {
                                            "review_level": 1,
                                            "review_interval": 1,
                                            "last_reviewed": "2024-08-20T12:05:04.01306",
                                            "next_review": "2024-08-21T12:05:04.01306"
                                        },
                                        "short_answer": "Docker image 是用來創建 Docker 容器的可執行包，包含程式碼、庫和依賴項。"
                                    },
                                    {
                                        "id": 34,
                                        "question": "Docker 的主要構件是：Docker image、___、___。",
                                        "type": "FILL_IN_THE_BLANK",
                                        "tags": [],
                                        "note_id": 3,
                                        "user_id": 2,
                                        "review_info": {
                                            "review_level": 0,
                                            "review_interval": 0,
                                            "last_reviewed": null,
                                            "next_review": "2024-08-07T12:14:29.433422"
                                        },
                                        "in_blank_answers": [
                                            {
                                                "id": 19,
                                                "text": "Docker Container"
                                            },
                                            {
                                                "id": 20,
                                                "text": "Dockerfile"
                                            }
                                        ],
                                        "full_answer": "Docker 的主要構件是：Docker image、Docker Container、Dockerfile。"
                                    },
                                    {
                                        "id": 37,
                                        "question": "什麼是 docker-compose.yml？",
                                        "type": "SHORT_ANSWER",
                                        "tags": [],
                                        "note_id": 4,
                                        "user_id": 2,
                                        "review_info": {
                                            "review_level": 0,
                                            "review_interval": 0,
                                            "last_reviewed": null,
                                            "next_review": "2024-08-07T12:15:27.761742"
                                        },
                                        "short_answer": "docker-compose.yml 是用於定義和運行多個 Docker 容器的配置文件。"
                                    },
                                    {
                                        "id": 38,
                                        "question": "docker-compose.yml 中定義的服務包括：mysqldb，___，___。",
                                        "type": "FILL_IN_THE_BLANK",
                                        "tags": [],
                                        "note_id": 4,
                                        "user_id": 2,
                                        "review_info": {
                                            "review_level": 0,
                                            "review_interval": 0,
                                            "last_reviewed": null,
                                            "next_review": "2024-08-07T12:15:27.779398"
                                        },
                                        "in_blank_answers": [
                                            {
                                                "id": 21,
                                                "text": "springboot"
                                            },
                                            {
                                                "id": 22,
                                                "text": "net"
                                            }
                                        ],
                                        "full_answer": "docker-compose.yml 中定義的服務包括：mysqldb，springboot，net。"
                                    },
                                    {
                                        "id": 39,
                                        "question": "以下哪一項是 docker-compose.yml 的版本號？",
                                        "type": "MULTIPLE_CHOICE",
                                        "tags": [],
                                        "options": [
                                            {
                                                "id": 37,
                                                "text": "2.0"
                                            },
                                            {
                                                "id": 38,
                                                "text": "3.5"
                                            },
                                            {
                                                "id": 39,
                                                "text": "3.8"
                                            },
                                            {
                                                "id": 40,
                                                "text": "4.0"
                                            }
                                        ],
                                        "note_id": 4,
                                        "user_id": 2,
                                        "review_info": {
                                            "review_level": 0,
                                            "review_interval": 0,
                                            "last_reviewed": null,
                                            "next_review": "2024-08-07T12:15:27.800231"
                                        },
                                        "answer_option": {
                                            "id": 39,
                                            "text": "3.8"
                                        }
                                    }
                                ],
                                "pageNo": 0,
                                "pageSize": 10,
                                "totalElements": 6,
                                "totalPages": 1,
                                "last": true
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<FlashcardPageResponse> findFlashcardsByKeyword(
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) @Min(0) Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) @Max(100) @Min(1) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir);

    @Operation(
            summary = "[NEW] 搜尋特定牌組的字卡",
            description = "根據 deck_id 和關鍵字搜尋字卡，並且可以加入分頁、排序等參數，例如想要每頁數量為 100，可以送出 /api/v1/flashcards/decks/1/flashcards/search?keyword=紅黑樹&pageSize=100",
            parameters = {
                    @Parameter(
                            name = "keyword",
                            description = "要查詢的關鍵字名稱，例如 api/v1/flashcards/decks/1/flashcards/search?keyword=紅黑樹",
                            required = true,
                            example = "紅黑樹"
                    ),
                    @Parameter(name = "pageNo", description = "頁碼", example = "0"),
                    @Parameter(name = "pageSize", description = "每頁筆記數量", example = "10"),
                    @Parameter(name = "sortBy", description = "排序欄位", example = "dateCreated"),
                    @Parameter(name = "sortDir", description = "排序方向", example = "desc")
            }
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功搜尋特定牌組的字卡",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "content": [
                                    {
                                        "id": 132,
                                        "question": "風險管理的主要程序包括：風險識別、風險評估、風險監控、風險___、___，以及___。",
                                        "type": "FILL_IN_THE_BLANK",
                                        "tags": [],
                                        "note_id": 17,
                                        "user_id": 2,
                                        "review_info": {
                                            "review_level": 0,
                                            "review_interval": 0,
                                            "last_reviewed": null,
                                            "next_review": "2024-08-20T22:49:50.863049"
                                        },
                                        "in_blank_answers": [
                                            {
                                                "id": 85,
                                                "text": "因應"
                                            },
                                            {
                                                "id": 86,
                                                "text": "風險接受"
                                            },
                                            {
                                                "id": 87,
                                                "text": "風險分析"
                                            }
                                        ],
                                        "full_answer": "風險管理的主要程序包括：風險識別、風險評估、風險監控、風險因應、風險接受，以及風險分析。"
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
    ResponseEntity<FlashcardPageResponse> findFlashcardsByDeckIdAndKeyword(
            @PathVariable(name = "deck_id") @Min(1) Long deckId,
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) @Min(0) Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) @Max(100) @Min(1) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir);

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

    @Operation(
            summary = "[NEW] 評估簡答題正確性",
            description = "根據 question, answer, user_answer 評估簡答題正確性，同時打上分數與給予回饋"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功評估簡答題正確性",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "is_correct": true,
                                "score": 80,
                                "feedback": "答案涵蓋了主要概念，但缺少控制損失的部分。"
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<EvaluateShortAnswerResponse> evaluateShortAnswer(@RequestBody
                                                                    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                            content = @Content(
                                                                                    mediaType = "application/json",
                                                                                    examples = @ExampleObject(value = """
                                                                                            {
                                                                                                "question": "風險管理的基本概念是什麼？",
                                                                                                "answer": "風險管理的基本概念是透過系統化的方法來辨識、評估及應對資訊安全的風險，以控制可能造成的損失。",
                                                                                                "user_answer": "透過系統化的方法來辨識、評估及應對資訊安全的風險"
                                                                                            }
                                                                                            """)
                                                                            )
                                                                    ) EvaluateShortAnswerRequest request);

    @Operation(
            summary = "[NEW] 根據 flashcard_id 評估簡答題正確性",
            description = "根據 flashcard_id 與 user_answer 評估簡答題正確性，同時打上分數與給予回饋"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功評估簡答題正確性",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "is_correct": true,
                                "score": 80,
                                "feedback": "答案涵蓋了主要概念，但缺少控制損失的部分。"
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    public ResponseEntity<EvaluateShortAnswerResponse> evaluateShortAnswerFlashcardByFlashcardId(@PathVariable(name = "flashcard_id") @Min(1) Long flashcardId,
                                                                                                 @RequestBody
                                                                                                 @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                                                         content = @Content(
                                                                                                                 mediaType = "application/json",
                                                                                                                 examples = @ExampleObject(value = """
                                                                                                                         {
                                                                                                                             "user_answer": "透過系統化的方法來辨識、評估及應對資訊安全的風險"
                                                                                                                         }
                                                                                                                         """)
                                                                                                         )
                                                                                                 ) EvaluateShortAnswerRequest request);
}
