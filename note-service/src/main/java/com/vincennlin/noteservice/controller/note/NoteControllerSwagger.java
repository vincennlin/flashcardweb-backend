package com.vincennlin.noteservice.controller.note;

import com.vincennlin.noteservice.constant.AppConstants;
import com.vincennlin.noteservice.payload.extract.ExtractLanguage;
import com.vincennlin.noteservice.payload.flashcard.dto.FlashcardDto;
import com.vincennlin.noteservice.payload.note.dto.NoteDto;
import com.vincennlin.noteservice.payload.note.page.NotePageResponse;
import com.vincennlin.noteservice.payload.flashcard.request.GenerateFlashcardRequest;
import com.vincennlin.noteservice.payload.flashcard.request.GenerateFlashcardsRequest;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(
        name = "Note Controller",
        description = "筆記相關的 API"
)
public interface NoteControllerSwagger {

    @Operation(
            summary = "檢查筆記服務狀態",
            description = "檢查筆記服務是否正常運作"
    )
    @ApiResponse(
            responseCode = "200",
            description = "筆記服務正常運作",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "Note Service is up and running on port 50434")
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<String> status();

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
                                        "content": "紅黑樹和AVL樹一樣，都在插入時間、刪除時間和搜尋時間方面提供了最好的最壞情況保證。這不僅使它們在時間敏感的應用（如即時應用）中有價值，還使它們成為其他提供最壞情況保證的資料結構的基礎模板。例如，在計算幾何中使用的許多資料結構都可以基於紅黑樹實現。紅黑樹在函數式程式設計中也特別有用。在這裡，它們是最常用的持久資料結構之一，用來構造關聯陣列和集合。每次插入或刪除之後，它們能保持為以前的版本。除了 O(log n) 的時間之外，紅黑樹的持久版本每次插入或刪除還需要 O(log n) 的空間。紅黑樹是2-3-4樹的一種等價結構。換句話說，對於每個2-3-4樹，都存在至少一個數據元素是同樣次序的紅黑樹。在2-3-4樹上的插入和刪除操作也等同於在紅黑樹中的顏色翻轉和旋轉。這使得2-3-4樹成為理解紅黑樹背後邏輯的重要工具，這也是為什麼很多介紹演算法的教科書在介紹紅黑樹之前會先介紹2-3-4樹，儘管2-3-4樹在實踐中不常使用。",
                                        "user_id": 2,
                                        "deck_id": 1,
                                        "total_flashcard_count": 4,
                                        "review_flashcard_count": 4,
                                        "flashcards": [
                                            {
                                                "id": 1,
                                                "question": "什麼是紅黑樹？",
                                                "type": "SHORT_ANSWER",
                                                "note_id": 1,
                                                "user_id": 2,
                                                "short_answer": "紅黑樹是一種自平衡的二叉搜尋樹，提供最好的最壞情況保證的插入、刪除和搜尋時間。"
                                            },
                                            {
                                                "id": 2,
                                                "question": "紅黑樹的時間複雜度為 O(___ n) 的插入和刪除操作。",
                                                "type": "FILL_IN_THE_BLANK",
                                                "note_id": 1,
                                                "user_id": 2,
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
                                                "answer_option": {
                                                    "id": 2,
                                                    "text": "錯"
                                                }
                                            },
                                            {
                                                "id": 4,
                                                "question": "紅黑樹與 AVL 樹相比，提供更好的最壞時間複雜度保證。",
                                                "type": "TRUE_FALSE",
                                                "note_id": 1,
                                                "user_id": 2,
                                                "true_false_answer": false
                                            }
                                        ],
                                        "date_created": "2024-08-02T23:07:51.339166",
                                        "last_updated": "2024-08-02T23:17:40.060185"
                                    },
                                    {
                                        "id": 2,
                                        "content": "紅黑樹和AVL樹一樣，都在插入時間、刪除時間和搜尋時間方面提供了最好的最壞情況保證。這不僅使它們在時間敏感的應用（如即時應用）中有價值，還使它們成為其他提供最壞情況保證的資料結構的基礎模板。例如，在計算幾何中使用的許多資料結構都可以基於紅黑樹實現。紅黑樹在函數式程式設計中也特別有用。在這裡，它們是最常用的持久資料結構之一，用來構造關聯陣列和集合。每次插入或刪除之後，它們能保持為以前的版本。除了 O(log n) 的時間之外，紅黑樹的持久版本每次插入或刪除還需要 O(log n) 的空間。紅黑樹是2-3-4樹的一種等價結構。換句話說，對於每個2-3-4樹，都存在至少一個數據元素是同樣次序的紅黑樹。在2-3-4樹上的插入和刪除操作也等同於在紅黑樹中的顏色翻轉和旋轉。這使得2-3-4樹成為理解紅黑樹背後邏輯的重要工具，這也是為什麼很多介紹演算法的教科書在介紹紅黑樹之前會先介紹2-3-4樹，儘管2-3-4樹在實踐中不常使用。",
                                        "user_id": 2,
                                        "deck_id": 2,
                                        "total_flashcard_count": 0,
                                        "review_flashcard_count": 0,
                                        "flashcards": [],
                                        "date_created": "2024-08-02T23:22:30.91771",
                                        "last_updated": "2024-08-02T23:22:30.918029"
                                    }
                                ],
                                "pageNo": 0,
                                "pageSize": 10,
                                "totalElements": 2,
                                "totalPages": 1,
                                "last": true
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<NotePageResponse> getAllNotes(
            @RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) @Min(0) Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) @Max(100) @Min(1) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir);


    @Operation(
            summary = "[EDITED][路由名稱] 取得特定使用者的筆記",
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
                                        "content": "紅黑樹和AVL樹一樣，都在插入時間、刪除時間和搜尋時間方面提供了最好的最壞情況保證。這不僅使它們在時間敏感的應用（如即時應用）中有價值，還使它們成為其他提供最壞情況保證的資料結構的基礎模板。例如，在計算幾何中使用的許多資料結構都可以基於紅黑樹實現。紅黑樹在函數式程式設計中也特別有用。在這裡，它們是最常用的持久資料結構之一，用來構造關聯陣列和集合。每次插入或刪除之後，它們能保持為以前的版本。除了 O(log n) 的時間之外，紅黑樹的持久版本每次插入或刪除還需要 O(log n) 的空間。紅黑樹是2-3-4樹的一種等價結構。換句話說，對於每個2-3-4樹，都存在至少一個數據元素是同樣次序的紅黑樹。在2-3-4樹上的插入和刪除操作也等同於在紅黑樹中的顏色翻轉和旋轉。這使得2-3-4樹成為理解紅黑樹背後邏輯的重要工具，這也是為什麼很多介紹演算法的教科書在介紹紅黑樹之前會先介紹2-3-4樹，儘管2-3-4樹在實踐中不常使用。",
                                        "user_id": 2,
                                        "deck_id": 1,
                                        "total_flashcard_count": 4,
                                        "review_flashcard_count": 4,
                                        "flashcards": [
                                            {
                                                "id": 1,
                                                "question": "什麼是紅黑樹？",
                                                "type": "SHORT_ANSWER",
                                                "note_id": 1,
                                                "user_id": 2,
                                                "short_answer": "紅黑樹是一種自平衡的二叉搜尋樹，提供最好的最壞情況保證的插入、刪除和搜尋時間。"
                                            },
                                            {
                                                "id": 2,
                                                "question": "紅黑樹的時間複雜度為 O(___ n) 的插入和刪除操作。",
                                                "type": "FILL_IN_THE_BLANK",
                                                "note_id": 1,
                                                "user_id": 2,
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
                                                "answer_option": {
                                                    "id": 2,
                                                    "text": "錯"
                                                }
                                            },
                                            {
                                                "id": 4,
                                                "question": "紅黑樹與 AVL 樹相比，提供更好的最壞時間複雜度保證。",
                                                "type": "TRUE_FALSE",
                                                "note_id": 1,
                                                "user_id": 2,
                                                "true_false_answer": false
                                            }
                                        ],
                                        "date_created": "2024-08-02T23:07:51.339166",
                                        "last_updated": "2024-08-02T23:17:40.060185"
                                    },
                                    {
                                        "id": 2,
                                        "content": "紅黑樹和AVL樹一樣，都在插入時間、刪除時間和搜尋時間方面提供了最好的最壞情況保證。這不僅使它們在時間敏感的應用（如即時應用）中有價值，還使它們成為其他提供最壞情況保證的資料結構的基礎模板。例如，在計算幾何中使用的許多資料結構都可以基於紅黑樹實現。紅黑樹在函數式程式設計中也特別有用。在這裡，它們是最常用的持久資料結構之一，用來構造關聯陣列和集合。每次插入或刪除之後，它們能保持為以前的版本。除了 O(log n) 的時間之外，紅黑樹的持久版本每次插入或刪除還需要 O(log n) 的空間。紅黑樹是2-3-4樹的一種等價結構。換句話說，對於每個2-3-4樹，都存在至少一個數據元素是同樣次序的紅黑樹。在2-3-4樹上的插入和刪除操作也等同於在紅黑樹中的顏色翻轉和旋轉。這使得2-3-4樹成為理解紅黑樹背後邏輯的重要工具，這也是為什麼很多介紹演算法的教科書在介紹紅黑樹之前會先介紹2-3-4樹，儘管2-3-4樹在實踐中不常使用。",
                                        "user_id": 2,
                                        "deck_id": 2,
                                        "total_flashcard_count": 0,
                                        "review_flashcard_count": 0,
                                        "flashcards": [],
                                        "date_created": "2024-08-02T23:22:30.91771",
                                        "last_updated": "2024-08-02T23:22:30.918029"
                                    }
                                ],
                                "pageNo": 0,
                                "pageSize": 10,
                                "totalElements": 2,
                                "totalPages": 1,
                                "last": true
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<NotePageResponse> getNotesByUserId(
            @PathVariable(name = "user_id") @Min(1) Long userId,
            @RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) @Min(0) Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) @Max(100) @Min(1) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir);

    @Operation(
            summary = "取得特定牌組的筆記",
            description = "根據 deck_id 取得特定牌組的筆記，取得的筆記會包含所有子牌組的筆記。"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功取得特定牌組的筆記",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "content": [
                                    {
                                        "id": 1,
                                        "content": "紅黑樹和AVL樹一樣，都在插入時間、刪除時間和搜尋時間方面提供了最好的最壞情況保證。這不僅使它們在時間敏感的應用（如即時應用）中有價值，還使它們成為其他提供最壞情況保證的資料結構的基礎模板。例如，在計算幾何中使用的許多資料結構都可以基於紅黑樹實現。紅黑樹在函數式程式設計中也特別有用。在這裡，它們是最常用的持久資料結構之一，用來構造關聯陣列和集合。每次插入或刪除之後，它們能保持為以前的版本。除了 O(log n) 的時間之外，紅黑樹的持久版本每次插入或刪除還需要 O(log n) 的空間。紅黑樹是2-3-4樹的一種等價結構。換句話說，對於每個2-3-4樹，都存在至少一個數據元素是同樣次序的紅黑樹。在2-3-4樹上的插入和刪除操作也等同於在紅黑樹中的顏色翻轉和旋轉。這使得2-3-4樹成為理解紅黑樹背後邏輯的重要工具，這也是為什麼很多介紹演算法的教科書在介紹紅黑樹之前會先介紹2-3-4樹，儘管2-3-4樹在實踐中不常使用。",
                                        "user_id": 2,
                                        "deck_id": 1,
                                        "total_flashcard_count": 4,
                                        "review_flashcard_count": 4,
                                        "flashcards": [
                                            {
                                                "id": 1,
                                                "question": "什麼是紅黑樹？",
                                                "type": "SHORT_ANSWER",
                                                "note_id": 1,
                                                "user_id": 2,
                                                "short_answer": "紅黑樹是一種自平衡的二叉搜尋樹，提供最好的最壞情況保證的插入、刪除和搜尋時間。"
                                            },
                                            {
                                                "id": 2,
                                                "question": "紅黑樹的時間複雜度為 O(___ n) 的插入和刪除操作。",
                                                "type": "FILL_IN_THE_BLANK",
                                                "note_id": 1,
                                                "user_id": 2,
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
                                                "answer_option": {
                                                    "id": 2,
                                                    "text": "錯"
                                                }
                                            },
                                            {
                                                "id": 4,
                                                "question": "紅黑樹與 AVL 樹相比，提供更好的最壞時間複雜度保證。",
                                                "type": "TRUE_FALSE",
                                                "note_id": 1,
                                                "user_id": 2,
                                                "true_false_answer": false
                                            }
                                        ],
                                        "date_created": "2024-08-02T23:07:51.339166",
                                        "last_updated": "2024-08-02T23:17:40.060185"
                                    },
                                    {
                                        "id": 2,
                                        "content": "紅黑樹和AVL樹一樣，都在插入時間、刪除時間和搜尋時間方面提供了最好的最壞情況保證。這不僅使它們在時間敏感的應用（如即時應用）中有價值，還使它們成為其他提供最壞情況保證的資料結構的基礎模板。例如，在計算幾何中使用的許多資料結構都可以基於紅黑樹實現。紅黑樹在函數式程式設計中也特別有用。在這裡，它們是最常用的持久資料結構之一，用來構造關聯陣列和集合。每次插入或刪除之後，它們能保持為以前的版本。除了 O(log n) 的時間之外，紅黑樹的持久版本每次插入或刪除還需要 O(log n) 的空間。紅黑樹是2-3-4樹的一種等價結構。換句話說，對於每個2-3-4樹，都存在至少一個數據元素是同樣次序的紅黑樹。在2-3-4樹上的插入和刪除操作也等同於在紅黑樹中的顏色翻轉和旋轉。這使得2-3-4樹成為理解紅黑樹背後邏輯的重要工具，這也是為什麼很多介紹演算法的教科書在介紹紅黑樹之前會先介紹2-3-4樹，儘管2-3-4樹在實踐中不常使用。",
                                        "user_id": 2,
                                        "deck_id": 2,
                                        "total_flashcard_count": 0,
                                        "review_flashcard_count": 0,
                                        "flashcards": [],
                                        "date_created": "2024-08-02T23:22:30.91771",
                                        "last_updated": "2024-08-02T23:22:30.918029"
                                    }
                                ],
                                "pageNo": 0,
                                "pageSize": 10,
                                "totalElements": 2,
                                "totalPages": 1,
                                "last": true
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<NotePageResponse> getNotesByDeckId(
            @PathVariable(name = "deck_id") @Min(1) Long deckId,
            @RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) @Min(0) Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) @Max(100) @Min(1) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir);

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
                                "content": "紅黑樹和AVL樹一樣，都在插入時間、刪除時間和搜尋時間方面提供了最好的最壞情況保證。這不僅使它們在時間敏感的應用（如即時應用）中有價值，還使它們成為其他提供最壞情況保證的資料結構的基礎模板。例如，在計算幾何中使用的許多資料結構都可以基於紅黑樹實現。紅黑樹在函數式程式設計中也特別有用。在這裡，它們是最常用的持久資料結構之一，用來構造關聯陣列和集合。每次插入或刪除之後，它們能保持為以前的版本。除了 O(log n) 的時間之外，紅黑樹的持久版本每次插入或刪除還需要 O(log n) 的空間。紅黑樹是2-3-4樹的一種等價結構。換句話說，對於每個2-3-4樹，都存在至少一個數據元素是同樣次序的紅黑樹。在2-3-4樹上的插入和刪除操作也等同於在紅黑樹中的顏色翻轉和旋轉。這使得2-3-4樹成為理解紅黑樹背後邏輯的重要工具，這也是為什麼很多介紹演算法的教科書在介紹紅黑樹之前會先介紹2-3-4樹，儘管2-3-4樹在實踐中不常使用。",
                                "user_id": 2,
                                "deck_id": 1,
                                "total_flashcard_count": 4,
                                "review_flashcard_count": 4,
                                "flashcards": [
                                    {
                                        "id": 1,
                                        "question": "什麼是紅黑樹？",
                                        "type": "SHORT_ANSWER",
                                        "note_id": 1,
                                        "user_id": 2,
                                        "short_answer": "紅黑樹是一種自平衡的二叉搜尋樹，提供最好的最壞情況保證的插入、刪除和搜尋時間。"
                                    },
                                    {
                                        "id": 2,
                                        "question": "紅黑樹的時間複雜度為 O(___ n) 的插入和刪除操作。",
                                        "type": "FILL_IN_THE_BLANK",
                                        "note_id": 1,
                                        "user_id": 2,
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
                                        "answer_option": {
                                            "id": 2,
                                            "text": "錯"
                                        }
                                    },
                                    {
                                        "id": 4,
                                        "question": "紅黑樹與 AVL 樹相比，提供更好的最壞時間複雜度保證。",
                                        "type": "TRUE_FALSE",
                                        "note_id": 1,
                                        "user_id": 2,
                                        "true_false_answer": false
                                    }
                                ],
                                "date_created": "2024-08-02T23:07:51.339166",
                                "last_updated": "2024-08-02T23:17:40.060185"
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<NoteDto> getNoteById(@PathVariable(name = "note_id") @Min(1) Long id);


    @Operation(
            summary = "[EDITED][路由][Response Body] 新增筆記",
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
                                "content": "紅黑樹和AVL樹一樣，都在插入時間、刪除時間和搜尋時間方面提供了最好的最壞情況保證。這不僅使它們在時間敏感的應用（如即時應用）中有價值，還使它們成為其他提供最壞情況保證的資料結構的基礎模板。例如，在計算幾何中使用的許多資料結構都可以基於紅黑樹實現。紅黑樹在函數式程式設計中也特別有用。在這裡，它們是最常用的持久資料結構之一，用來構造關聯陣列和集合。每次插入或刪除之後，它們能保持為以前的版本。除了 O(log n) 的時間之外，紅黑樹的持久版本每次插入或刪除還需要 O(log n) 的空間。紅黑樹是2-3-4樹的一種等價結構。換句話說，對於每個2-3-4樹，都存在至少一個數據元素是同樣次序的紅黑樹。在2-3-4樹上的插入和刪除操作也等同於在紅黑樹中的顏色翻轉和旋轉。這使得2-3-4樹成為理解紅黑樹背後邏輯的重要工具，這也是為什麼很多介紹演算法的教科書在介紹紅黑樹之前會先介紹2-3-4樹，儘管2-3-4樹在實踐中不常使用。",
                                "user_id": 2,
                                "deck_id": 1,
                                "flashcards": null,
                                "total_flashcard_count": 0,
                                "review_flashcard_count": 0,
                                "date_created": "2024-08-02T23:07:51.339166",
                                "last_updated": "2024-08-02T23:07:51.339424"
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<NoteDto> createNote(@PathVariable(name = "deck_id") @Min(1) Long deckId,
                                       @Valid @RequestBody
                                       @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                               content = @Content(
                                                       mediaType = "application/json",
                                                       examples = @ExampleObject(value = """
                                                                            {
                                                                                "content": "This is a note about Java"
                                                                            }
                                                                            """)
                                               )
                                       ) NoteDto noteDto);

    @Operation(
            summary = "[NEW] 根據 PDF 檔案新增筆記",
            description = "根據 PDF 檔案新增筆記並存進資料庫。 必須指定 mediaType 為 multipart/form-data。 " +
                    "Request body 必須是 PDF 檔案，其中 key 為 'file'，value 為該 PDF 檔案"
    )
    @ApiResponse(
            responseCode = "201",
            description = "成功根據 PDF 檔案創建筆記",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "id": 5,
                                "content": "資安科技與管理\\nModule3. 風險管理\\n大綱\\n• 風險管理的基本觀念\\n• 常見的資安事件\\n• 風險識別\\n• 風險評估\\n• 風險對應\\n1 風險管理的基本概念\\n由風險為出發點的思維\\n• 為何要以風險為出發點：\\n• 資安的吸引與排除效果，如果太過可能反而\\n會造成反效果\\n• 經濟效益考量\\n• 資安不太會對整個生產過程產生加值，\\n但通常需要投入許多額外的資源。\\n• 如果不管控，等到發生事情又可能會造\\n成巨大損失\\n• 透過從風險的角度來思考，可決定到底該要採用\\n什麼樣的資安措施，並把錢花在刀口上。\\n企業\\n安控機制\\n怪客 間諜 惡意攻擊者\\n排除\\n客戶 供應商 授權使用者\\n因為信賴\\n而吸引\\n但過頭了\\n會使使用\\n者卻步\\n風險管理在做些什麼？\\n• 風險管理程序是要透過一套有系統的方法，來達到以下的目的：\\n• 管控或降低資訊安全意外事件所可能造成的損失：風險管理要能夠辨識出可能發生的意外事件或\\n風險，並採取適當回應，以使得可能的損失被控管在一個可接受的範圍內。\\n• 提升資訊安全措施的成本效益：風險管理的方法要能夠協助企業或組織，在需要控管某項風險時，\\n能夠找到最有成本效益的措施來進行控管。\\n• 滿足法規或是利害關係人 (如客戶與消費者團體) 的相關要求。\\n風險管理的起源：孫子兵法謀攻篇\\n故曰：知己知彼，百戰不貽﹔\\n不知彼而知己，一勝一負﹔\\n不知彼不知己，每戰必貽。\\n了解本身的弱點 了解可能遭遇的威脅\\n風險管理在資安上的概念\\n• 風險管理了解到威脅 (T) 利用到弱點 (V) 所可能造成意外事件的損失 (R)。而採取適當的控制措施 (S)，\\n使得殘存風險 (RR)，是可被企業接受的。\\nAssets\\nV\\nV\\nV\\nV\\nV\\nV\\nS\\nS\\nS\\nT\\nT\\nT\\nT\\nT\\nR\\nRR\\nRR\\nRR\\nSource: ISO 13335-1\\nISO/IEC 27005 的架構\\n了解背景\\n風險識別\\n風險判斷\\n風險估計\\n風險回應\\n風險接受\\n風險分析\\n風險評估\\n風\\n險\\n監\\n控\\n與\\n審\\n查\\n是\\n接受評估\\n結果？\\n接受回應\\n方式？\\n是\\n否\\n否\\nANSI/ISA-62443-3-2:2020\\n• 系統設計的安全風險評估 (Security risk assessment for system design)\\n9\\n識別考量中系統\\n初始系統架構圖\\n與資產清單\\n系統架構圖與資產\\n清單更新\\n執行高階風險評估\\n既有其他風險評估\\n結果與風險矩陣\\n初步的考量中系統\\n安全水準目標\\n切分區域與管道\\n標準、最佳實務、\\n供應商指引、關鍵\\n性評估、功能規格\\n初步的區域與\\n管道圖\\n執行詳細的風險評估\\n殘存風險與安全\\n水準目標\\n紀錄安全需求假設與限制 資訊安全需求規格\\n公司政策、法規、\\n可接受風險\\n風險管理的主要程序\\n風險\\n識別\\n風險\\n評估\\n風險\\n監控\\n風險\\n因應\\n成\\n熟\\n度\\n2 常見的資安事件\\n常見的資安意外事件來源\\n類別 威脅\\n災難事件\\nCatastrophic \\nincident\\n火災 Fire\\n水災Flood\\n地震Earthquake\\n暴風 Severe storm\\n恐怖攻擊 Terrorist attack\\n暴動 Civil unrest/riots\\n山崩 Landslide\\n雪崩 Avalanche\\n產業面影響 Industrial accident\\n機械故障\\nMechanical failure\\n缺乏電源 Power outage\\n機械固障 Mechanical failure\\n應體失效 Hardware failure\\n網路中斷 Network outage\\n環境控制失效 Environmental controls failure\\n建築物意外Construction accident\\n類別 威脅\\n非故意\\nNon-malicious \\nperson\\n未被通知的員工 Uninformed \\nemployee\\n未被通知的使用者 Uninformed \\nuser\\n疏乎的員工 Negligent employee\\n人為惡意攻\\n擊 Malicious \\nperson\\n駭客 Hacker, cracker\\n電腦犯罪 Computer criminal\\n產業間諜 Industrial espionage\\n社交工程 Social engineering\\n不滿的現任或離職員工\\nDisgruntled current or former \\nemployee\\n恐怖份子 Terrorist\\nCSI 調查中的資訊安全威脅\\n事件 金額 比率\\n財務舞弊 (Financial Fraud) $21,124,750 12%\\n病毒 (Virus) $8,391,800 52%\\n資訊系統被外部滲透 (System\\npenetration by outsider)\\n$6,875,000 13%\\n資料被竊取 (除了行動設備失竊外)\\n(Theft of confidential data)\\n$5,685,000 17%\\n筆電或行動裝置遭竊 (Laptop or \\nmobile hardware theft)\\n$3,881,150 50%\\n內部員工對網路的濫用 (Insider abuse \\nof net access)\\n$2,889,700 59%\\n服務阻斷攻擊 (Denial of Service) $2,888,600 25%\\n網路釣魚 (Phishing) $2,752,000 26%\\n殭屍病毒 (Bot) $2,869,600 21%\\n3 風險識別\\n風險管理與緊急應變相關程序\\n風險管理程序\\n風險\\n意外事件應變程序風險項目\\n新發現的問題\\n系統回復程序 備援服務程序\\n事件升級\\n可能造成中斷的情況\\n風險識別方式\\n• 資料庫法 (通常以資產為導向)\\n• Misuse Case 與 CORAS\\n• FTA 法\\nOCTAVE Threat Profile – Human Actor using Network Access\\nCritical \\nAsset\\nNetwork \\nAccess\\nInside\\nOutside\\nDisclosure\\nDeliberate\\nModification\\nLoss, Destruction\\nInterruption\\nDisclosure\\nModification\\nLoss, Destruction\\nInterruption\\nDisclosure\\nDeliberate\\nModification\\nLoss, Destruction\\nInterruption\\nDisclosure\\nModification\\nLoss, Destruction\\nInterruption\\nAsset Access Actor Motive Outcome\\nOCTAVE Threat Profile – Human Actor using Physical Access\\nCritical \\nAsset\\nPhysical \\nAccess\\nInside\\nOutside\\nDisclosure\\nDeliberate\\nModification\\nLoss, Destruction\\nInterruption\\nDisclosure\\nModification\\nLoss, Destruction\\nInterruption\\nDisclosure\\nDeliberate\\nModification\\nLoss, Destruction\\nInterruption\\nDisclosure\\nModification\\nLoss, Destruction\\nInterruption\\nAsset Access Actor Motive Outcome\\nOCTAVE Threat Profile – System Problems\\nCritical \\nAsset\\nDisclosure\\nModification\\nLoss, Destruction\\nInterruption\\nDisclosure\\nModification\\nLoss, Destruction\\nInterruption\\nDisclosure\\nModification\\nLoss, Destruction\\nInterruption\\nDisclosure\\nModification\\nLoss, Destruction\\nInterruption\\nsoftware defects\\nmalicious code\\nsystem crashes\\nhardware defects\\nAsset Actor Outcome\\nOCTAVE Threat Profile – Other Problems\\nCritical \\nAsset\\nPower supply problems\\nTelecommunications \\nproblems or unavailability\\nThird-party problems or \\nunavailability of Third-party\\nsystems\\nPhysical configuration or \\narrangement of buildings, \\noffice, or equipment\\nnatural disasters (e.g., flood, \\nfire, tornado)\\nAsset Actor\\nDFD 的主要元件\\n組織或人員\\n處理/程序\\n資料流\\n資料儲存 系統邊界\\n介面\\n實體邊界\\n實體儲存\\n21\\n透過 DFD 建立系統資料流程圖\\n資料處\\n理元件 \\nA\\n使用者\\n個資 X\\n個資 X\\n資料處\\n理元件 \\nB\\n資料處\\n理元件 \\nC\\n個資 X 管理者\\n個資 X\\n個資 X\\n外部資料\\n處理單位\\n個資 X\\n系統 X\\n系統 Y\\n三個重點：資料流、邊界、存取 (攻擊)介面\\n22\\n以 RFID 點名系統為例去運用 DFD 進行應用程式分解\\n23\\n24\\n25\\n評估是否有可被下列威脅利用的弱點 (STRIDE)\\n• Spoofing Identity: 主要針對身份鑑別機制，看是否有可能會做到冒名的情況\\n• Tampering with data: 看資料是否有可能被竄改\\n• Repudiation: 否認交易或通訊\\n• Information disclosure: 在未經授權的情況下，可以存取到資料\\n• Denial of service: 會因為關鍵資源被攻擊，而造成服務無法被使用的情況\\n• Elevation of privilege: 未經授權的使用者透過入侵系統因而取得較高的系統權限\\n26\\nMisuse Cases\\n• Guttorm Sindre and Andreas Opdahl, 2000\\n• Actor is a Hostile Agent\\n• Bubble is drawn in inverted colours\\n• Goal is a Threat to Our System\\n• Obvious Security Applications\\nDrive the Car Steal the Car\\nCar Thief\\nthreatens\\nExample use and misuse cases for an e-store\\nSource: Guttorm Sindre A Andreas L. Opdahl, Eliciting security \\nrequirements with misuse cases, Requirements Eng (2005) 10: 34–44\\nText fields for describing misuse cases\\n• Name, Summary, Author, and Date: These fields retain the same meaning as in regular use \\ncases\\n• Basic path: This field describes the actions that the misuser(s) and the system go through to \\nharm the proposed system\\n• Alternative paths: This field describes ways to harm the proposed system that are not \\naccounted for by the basic path, but are still sufficiently similar to be described as variants of \\nthe basic path\\n• Mitigation points: This field identifies those actions in a basic or alternative path where misuse \\ncan be mitigated.\\n• Trigger: This field describes the states or events in the system or its environment that may \\ninitiate the misuse case. For some misuse cases, the trigger is just the predicate True, indicating \\na permanently present danger\\n• Assumptions: This field describes the states in the system’s environment that make the \\nmisuse case possible\\nSource: Guttorm Sindre A Andreas L. Opdahl, Eliciting security \\nrequirements with misuse cases, Requirements Eng (2005) 10: 34–44\\nText fields for describing misuse cases (續)\\n• Preconditions: This field describes the system states that make the misuse case possible\\n• Mitigation guarantee: This field describes the guaranteed outcome of mitigating a misuse case. \\n• Related business rules: Typically, business rules will be violated by the misuse. This field \\ncontains links to such rules, maybe along with links to rules that enable the threat or that limit \\nhow it could be mitigated or eliminated\\n• Misuser profile: This field describes whatever can be assumed about the misuser, for example, \\nwhether the misuser acts intentionally or inadvertently; whether the misuser is an insider or \\noutsider; and how technically skilled the misuser must be\\n• Scope: This field indicates whether the proposed system in a misuse case is, e.g., an entire \\nbusiness, a system of both users and computers, or just a software system\\nSource: Guttorm Sindre A Andreas L. Opdahl, Eliciting security \\nrequirements with misuse cases, Requirements Eng (2005) 10: 34–44\\n• Iteration: As for regular use cases, it is useful to allow both initial and detailed descriptions of \\nmisuse cases. This field indicates the misuse case’s iteration level, usually taken from the set \\nof iteration levels used for the use cases in the project\\n• Level: As for regular use cases, misuse cases can be specified at a general or specific \\nabstraction level. This field indicates whether the misuse case is, e.g., a summary, a user goal, \\nor a sub-function\\n• Stakeholders and risks: This field specifies the major risks for each stakeholder involved in the \\nmisuse case. \\n• Technology and data variations: A misuser may carry out a misuse case from a variety of \\ntechnical platforms, such as a PC or a WAP phone and, since only a few equipment-related \\nactions will differ in each case, it is unnecessary to specify two separate paths. Instead, this \\nfield lists the candidate types of equipment and explains how they differ in particular actions\\n• Terminology and explanations: This field contains explanations of technical terms and other \\nissues\\nSource: Guttorm Sindre A Andreas L. Opdahl, Eliciting security \\nrequirements with misuse cases, Requirements Eng (2005) 10: 34–44\\nText fields for describing misuse cases (續)\\nThe security requirements process\\n• Identify critical assets\\n• Define security goals for each asset\\n• Identify threats to each security goal by identifying stakeholders that may intentionally harm \\nthe system or its environment and/or identifying sequences of actions that may result in \\nintentional harm\\n• Identify and analyze risks for the threats using standard techniques for risk analysis and \\ncosting from the security and safety engineering fields\\n• Define security requirements for the threats to match risks and protection costs\\nSource: Guttorm Sindre A Andreas L. Opdahl, Eliciting security \\nrequirements with misuse cases, Requirements Eng (2005) 10: 34–44\\nMethodology – Security FTA\\n• The steps of building a fault tree:\\n• Identify undesired (root) events that can happen.\\n• Starting at each root event, construct a fault tree.\\n• Analyze the fault tree(s).\\nMethodology – Security FTA\\n• Step 1: Identify undesired events\\n• Ex. A system requirements document may mandate particular properties.\\n• Requirement: “only legitimate users can read patient documents”\\n• → “an unauthorized user reads a patent document”\\n• Step 2: Construct a fault tree\\n• Ground Rule I\\n• Write statements that are entered in the event boxes as faults; state precisely what the \\nfault is and when it occurs.\\n• Ground Rule II\\n• Ask “Can this fault consist of a component failure?”\\n• YES →“an individual component fails”  (state-of-component fault)\\n• NO → “something outside causes its failure” (state-of-system fault).\\nMethodology – Security FTA\\n• Step 2: Construct a fault tree (Cont'd)\\n• No Miracles Rule\\n• If the normal functioning of a component propagates a fault sequence, then it is \\nassumed that the component functions normally.\\n• Complete-the-Gate Rule\\n• All inputs to a particular gate should be completely defined before further analysis of \\nany one of them is undertaken.\\n• No Gate-to-Gate Rule\\n• Gate inputs should be properly defined fault events, and gates should not be directly \\nconnected to another gates.\\n35\\nMethodology – Security FTA\\n• Step 3: Analyze the fault tree(s)\\n• Once a fault tree has been constructed, then we can consider calculating probabilities of \\nfailure.\\n• However, in computer security, in common with many failure problems in computer \\nsoftware, it is difficult to assign useful probabilities to the events: the discrete, non-linear \\nnature of these systems makes a complete nonsense of the concept.\\n• Instead, the analyst must carry out a risk analysis. The fault tree provides most of the raw \\ndata: it tells the analyst how the system fails, given the primary events.\\n36\\nFault tree event symbols\\n• Primary events: are not developed any further for some reason\\n• The BASIC event indicates a basic initiating event at the limit of resolution. \\n• The UNDEVELOPED event is undeveloped because there we either lack information, or the \\nevent is of no consequence.\\n• The EXTERNAL event is an event that is expected to happen in the course of normal \\noperation of the system.\\nFault tree event symbols\\n• Other events\\n• The INTERMEDIATE event is A fault event that occurs because of one or more antecedent \\ncauses acting through logic gates.\\n• The CONDITIONING event is used to record any conditions or restrictions. It is used to \\nprimarily with INHIBIT and PRIORITY AND gates.\\nFault tree event symbols\\n• The AND gate indicates that the output fault only occurs if the two input faults occur.\\n• The OR gate indicates that the output fault occurs if at least one of the input faults occur.\\n• The EXECLUSIVE OR gate indicates that the output fault occurs if exactly one of the input faults \\noccur.\\nFault tree event symbols\\n• The PRIORITY AND gate indicates that the output fault only occurs if all the input faults occur \\nin a specified order.\\n• The INHIBIT gate is another special case of the AND gate: The output event is caused by a \\nsingle input event, but a particular condition (CONDITIONING event) must be satisfied before \\nthe input can produce the output.\\nFault tree event symbols\\n• The two transfer symbols, TRANSFER IN and TRANSFER OUT allow a large tree to be broken up \\ninto multiple pages; it also allows for the analysis to be broken into more manageable parts, \\nand for duplication to be reduced.\\nExample\\n42\\nExample\\nExample\\nIdentifying Threats with Attack Trees\\n• Attack tree\\n• Type of methodology commonly used to identify all  threats associated with a system\\n• Provides formal way of describing the security of systems based on various attacks\\n• Attack trees model the decision-making process of attackers\\n• Attack path\\n• Each path tracing from the root node to a leaf node represents a unique way to achieve \\nthe goal of the attacker\\n45\\nIdentifying Threats with Attack Trees (continued)\\n• Attack trees answer questions such as the following:\\n• What is the shortest path of attack?\\n• What is the easiest attack?\\n• What is the cheapest attack?\\n• Which attack causes the most damage?\\n• Which attack path is the hardest to detect?\\n• Commonly used for threat discovery, risk analysis, and system security evaluation\\n• Node in an attack tree can be either an \\"AND node\\" or an \\"OR node\\"\\n46\\nIdentifying Threats with Attack Trees (continued)\\n47\\nIdentifying Threats with Attack Trees (continued)\\n• Indicators\\n• Values assigned to each leaf node\\n• Used to make calculations about the nodes and attack paths\\n• Common types of indicators\\n• Cost of attack\\n• Probability of apprehension\\n• Technical ability\\n48\\nIdentifying Threats with Attack Trees (continued)\\n49\\nIdentifying Threats with Attack Trees (continued)\\n50\\n4 風險評估\\n風險評估的方法：正式法的資安風險評量 (定量法)\\nConcept Derivation Formula\\n曝險因子 Exposure Factor (EF) 威脅發生造成資產受損的比率\\n單一損失期望值 Single Loss Expectancy (SLE) 資產價值 x 曝險因子 (EF).\\n平均年發生率 Annualized Rate of Occurrence (ARO) 每年發生的頻率\\n年期望損失 Annualized Loss Expectancy (ALE) 單一損失期望值 (SLE) x平均年發生率 (ARO)\\n• 定量風險評估方法：採用數量化的分析方式，去計算可能的損失。\\n資安事件 預期風險\\n天然災害\\n人為疏失\\n風險分配\\n風險評估的方法：正式法的資安風險評量 (定性法)\\n• 主要是針對可能發生的資安事件，進行細部\\n的分析。又可以分為定性法和定量法：\\n• 定性風險評估方法：利用質化的類別，\\n來對風險進行評估。\\n威脅 大 中 小\\n弱點 大 中 小 大 中 小 大 中 小\\n重\\n要\\n性\\n高 高 高 高 高 高 中 高 中 低\\n中 高 高 中 高 中 中 中 中 低\\n低 高 中 低 中 中 中 中 中 低\\n資安事件 威脅發\\n生率\\n弱點易被\\n利用率\\n資產重要性 風險\\n火災 大 中 高 高\\n資訊資產 價值 風險\\nA 高 高\\nB 中\\nC 低\\n54\\n資產導向方法：流程訪談與資訊資產的辨識\\n系統/服務 參與者輸入 作業 輸出\\n種類 說明編號 價值 負責人 使用者\\n辨識系統/服務，\\n以及其所需元件\\n辨識資\\n料/文\\n件資產\\n就資產本身\\n評估重要性\\n或價值\\n由流程進\\n行效正\\n識別相關\\n負責人與\\n使用者\\n人員權責\\n55\\n資產導向方法：風險的評估\\n資訊系統\\n/ 流程/資\\n訊資產\\n弱點 A\\n弱點 B\\n弱點 C\\n弱點 D\\n弱點 E\\n威脅 1\\n威脅2\\n威脅3\\n資產 價值\\n可能風險 威脅 弱點 機率 影響 風險值\\n由資產去找出\\n可能的資安事\\n件\\n參考資產價值，\\n評估發生資安\\n事件後的影響\\n評估發生\\n的機率\\n計算風險值如果一個組織有 x 個資產，每個資產的風險有 n 項，則要\\n評 x*n 次\\n節省評估成本的方法\\n• 將資產分類，依類別產生風險清單\\n• 可節省每個資產都要去判斷到底有哪些風險的問題\\n• 將會有相似風險的資產放在一起評\\n• 只看資產價值減損 (AV*EF)，而避免一個一個風險去判斷\\n• 思考：資產價值可以如何判斷？\\n其實風險評估是一個很難的問題\\n57\\n發\\n生\\n機\\n率\\n損失\\n某事件一般會是常態分配\\n損失該如何估計？\\n甚麼樣的風險可以接受？\\n可接受風險水準\\n• 要準備可以滿足可接受風險水準\\n的資源。Ex. 存款準備。\\n• 在極端值發生的情況下，有規劃\\n因應方式。Ex. 保險。\\n要採取甚麼樣的控制手段？\\n風險控制\\n要評估控制前還是控制後風險？\\n比較\\n定性法 定量法\\n優點 計算較簡單 算出來之結果比較容易做跨領域比較\\n缺點 計算出來的結果可能沒有數學\\n上的意義\\n計算較複雜\\n半定量的作法\\nClass Name Asset Value \\nRange\\nExposure Factor \\nRange\\nFrequency\\nD [$0, $10K) [0%, 25%) [0,1)\\nC [$10K, $1M) [25%, 50%) [1,2)\\nB [$1M, $100M) [50%, 75%) [2,5)\\nA [$100M, ∞) [75%, 100%) [5, ∞)\\n半定量的作法\\nAsset Value \\nRange  \\nLower bound Upper bound Representative \\nvalue\\nD 0 10000 5000 \\nC 10000 1000000 505000 \\nB 1000000 100000000 50500000 \\nA 100000000 ∞ 5050000000 \\nFrequency Lower bound Upper bound Representative \\nvalue\\nD 0 1 0.25 \\nC 1 2 1.5 \\nB 2 5 3.5 \\nA 5 ∞ 6.5 \\nExposure \\nFactor Range\\nLower bound Upper bound Representative \\nvalue\\nD 0 0.25 0.125 \\nC 0.25 0.5 0.375 \\nB 0.5 0.75 0.625 \\nA 0.75 1 0.875 \\nClass Name Value in Range of \\nAsset Value \\nD [$0, $10K)\\nC [$10K, $1M)\\nB [$1M, $100M)\\nA [$100M, ∞)\\nALE of incident i:\\n5,050,000    0.625    3.5 \\n=789,062.5 \\n混合法\\n• 混合法：同時採用上述方法，例如：\\n• 首先用非正式法決定哪些部份特別重要\\n• 重要的部份採用正式的方法進行風險評估\\n• 而其它的部份採用基準法\\n進階思考：如何考慮極端值的狀況\\n一種可能的作法 ─ 加入最大影響\\n與業務永續運作計畫的連結\\n5 風險對應\\n風險的管理\\n• 當評估出來風險以後，一般可以採用以下四種方式加以管理：\\n• 接受\\n• 轉嫁\\n• 繞道\\n• 控制 嚴\\n重\\n性\\n發生頻率\\n接受\\n轉嫁\\n控制\\n規避\\n• 如何解決資安疲勞的問題？\\n67\\n弱點掃描/設定安全性掃描：針對資訊系統或設備\\n滲透測試：從各種可能的情況去發現系統風險\\n社交工程演練：針對人員去進行測試\\n稽核：發現沒有遵從流程的問題\\n流程完整性評估：檢查是否有符合最佳實務\\n法規符合性評估：評估相關作業是否有符合法規要求\\n威脅模型：針對重大系統識別威脅\\n風險分析的主要資料來源\\n資訊系統\\n與元件\\n弱點掃描\\n風險\\n• 弱點掃描結果\\n• 設 定 安 全 檢 查\\n結果\\n• 社 交 工 程 演 練\\n找到的缺失\\n• 對 於 流 程 上 的\\n缺失\\n• 可 能 不 合 法 的\\n問題\\n• 稽 核 所 發 現 的\\n問題\\n• 所識別出的重大\\n威脅以及相關風\\n險的評估\\n設定安全檢查\\n人員\\n社交工程演練\\n制度\\n流程完整性評估\\n法規符合性評估\\n核心\\n系統 建立威脅模型\\n與風險評估\\n滲透測試\\n稽核\\n自動化分析 半自動化分析 人工分析\\n70\\nShi-Cho Cha, Pei-Wen Juo, Li-Ting Liu and Wei-\\nNing Chen, \\"RiskPatrol: A risk management \\nsystem considering the integration risk \\nmanagement with business continuity \\nprocesses,\\" 2008 IEEE International Conference \\non Intelligence and Security Informatics, Taipei, \\nTaiwan, 2008, pp. 110-115, doi: \\n10.1109/ISI.2008.4565039.\\n71\\nS. -C. Cha, L. -T. Liu and B. -C. Yu, \\n\\"Process-Oriented Approach for Validating \\nAsset Value for Evaluating Information \\nSecurity Risk,\\" 2009 International \\nConference on Computational Science and \\nEngineering, Vancouver, BC, Canada, 2009, \\npp. 379-385, doi: 10.1109/CSE.2009.217.\\n72\\nS. -C. Cha and K. -H. Yeh, \\"A Data-Driven Security \\nRisk Assessment Scheme for Personal Data \\nProtection,\\" in IEEE Access, vol. 6, pp. 50510-50517, \\n2018, doi: 10.1109/ACCESS.2018.2868726.\\n謝謝各位的聆聽\\n",
                                "user_id": 2,
                                "deck_id": 7,
                                "total_flashcard_count": 0,
                                "review_flashcard_count": 0,
                                "flashcards": null,
                                "date_created": "2024-08-06T22:16:17.410525",
                                "last_updated": "2024-08-06T22:16:17.410566"
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<NoteDto> createNoteFromPdf(@PathVariable(name = "deck_id") @Min(1) Long deckId,
                                              @RequestPart("file")
                                              @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                      description = "PDF 檔案",
                                                      required = true,
                                                      content = @Content(
                                                              mediaType = "multipart/form-data",
                                                              examples = @ExampleObject(value = """
                                                                    {
                                                                        "file": "PDF 檔案"
                                                                    }
                                                                    """)
                                                      )
                                              ) MultipartFile file);

    @Operation(
            summary = "[NEW] 根據圖片新增筆記",
            description = "根據圖片新增筆記並存進資料庫。 必須指定 mediaType 為 multipart/form-data。 " +
                    "Request body 必須是圖片檔案，其中 key 為 'file'，value 為該圖片"
    )
    @ApiResponse(
            responseCode = "201",
            description = "成功根據圖片創建筆記",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "id": 4,
                                "content": "& docker-compose.yml X Y\\\\ console_2 [mysql local]\\nversion: \\"3.8\\"\\n> services:\\n> mysqldb:\\ncontainer_name: mysqldb\\nimage: mysqgl\\nenvironment:\\nMYSQL_ROOT_PASSWORD: Passweord\\nMYSQL_DATABASE: employeedb\\nnetworks:\\nspringboot-mysql-net:\\nnetworks:\\nspringboot-mysql-net:\\n5 driver: bridge\\n",
                                "user_id": 2,
                                "deck_id": 5,
                                "total_flashcard_count": 0,
                                "review_flashcard_count": 0,
                                "flashcards": null,
                                "date_created": "2024-08-07T12:13:40.517023",
                                "last_updated": "2024-08-07T12:13:40.517072"
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<NoteDto> createNoteFromImage(@PathVariable(name = "deck_id") @Min(1) Long deckId,
                                                @PathVariable(name = "language") ExtractLanguage language,
                                                @RequestPart("file") MultipartFile file);

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
                                "content": "(已更新) 紅黑樹和AVL樹一樣，都在插入時間、刪除時間和搜尋時間方面提供了最好的最壞情況保證。這不僅使它們在時間敏感的應用（如即時應用）中有價值，還使它們成為其他提供最壞情況保證的資料結構的基礎模板。例如，在計算幾何中使用的許多資料結構都可以基於紅黑樹實現。紅黑樹在函數式程式設計中也特別有用。在這裡，它們是最常用的持久資料結構之一，用來構造關聯陣列和集合。每次插入或刪除之後，它們能保持為以前的版本。除了 O(log n) 的時間之外，紅黑樹的持久版本每次插入或刪除還需要 O(log n) 的空間。紅黑樹是2-3-4樹的一種等價結構。換句話說，對於每個2-3-4樹，都存在至少一個數據元素是同樣次序的紅黑樹。在2-3-4樹上的插入和刪除操作也等同於在紅黑樹中的顏色翻轉和旋轉。這使得2-3-4樹成為理解紅黑樹背後邏輯的重要工具，這也是為什麼很多介紹演算法的教科書在介紹紅黑樹之前會先介紹2-3-4樹，儘管2-3-4樹在實踐中不常使用。",
                                "user_id": 2,
                                "deck_id": 1,
                                "total_flashcard_count": 4,
                                "review_flashcard_count": 4,
                                "flashcards": [
                                    {
                                        "id": 1,
                                        "question": "什麼是紅黑樹？",
                                        "type": "SHORT_ANSWER",
                                        "note_id": 1,
                                        "user_id": 2,
                                        "short_answer": "紅黑樹是一種自平衡的二叉搜尋樹，提供最好的最壞情況保證的插入、刪除和搜尋時間。"
                                    },
                                    {
                                        "id": 2,
                                        "question": "紅黑樹的時間複雜度為 O(___ n) 的插入和刪除操作。",
                                        "type": "FILL_IN_THE_BLANK",
                                        "note_id": 1,
                                        "user_id": 2,
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
                                        "answer_option": {
                                            "id": 2,
                                            "text": "錯"
                                        }
                                    },
                                    {
                                        "id": 4,
                                        "question": "紅黑樹與 AVL 樹相比，提供更好的最壞時間複雜度保證。",
                                        "type": "TRUE_FALSE",
                                        "note_id": 1,
                                        "user_id": 2,
                                        "true_false_answer": false
                                    }
                                ],
                                "date_created": "2024-08-02T23:07:51.339166",
                                "last_updated": "2024-08-02T23:09:18.745139"
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<NoteDto> updateNote(@PathVariable(name = "note_id") @Min(1) Long id,
                                       @RequestHeader("Authorization") String authorization,
                                       @Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                               content = @Content(
                                                       mediaType = "application/json",
                                                       examples = @ExampleObject(value = """
                                                                      {
                                                                          "content":"（已更新）紅黑樹和AVL樹一樣，都在插入時間、刪除時間和搜尋時間方面提供了最好的最壞情況保證。這不僅使它們在時間敏感的應用（如即時應用）中有價值，還使它們成為其他提供最壞情況保證的資料結構的基礎模板。例如，在計算幾何中使用的許多資料結構都可以基於紅黑樹實現。紅黑樹在函數式程式設計中也特別有用。在這裡，它們是最常用的持久資料結構之一，用來構造關聯陣列和集合。每次插入或刪除之後，它們能保持為以前的版本。除了 O(log n) 的時間之外，紅黑樹的持久版本每次插入或刪除還需要 O(log n) 的空間。紅黑樹是2-3-4樹的一種等價結構。換句話說，對於每個2-3-4樹，都存在至少一個數據元素是同樣次序的紅黑樹。在2-3-4樹上的插入和刪除操作也等同於在紅黑樹中的顏色翻轉和旋轉。這使得2-3-4樹成為理解紅黑樹背後邏輯的重要工具，這也是為什麼很多介紹演算法的教科書在介紹紅黑樹之前會先介紹2-3-4樹，儘管2-3-4樹在實踐中不常使用。"
                                                                      }
                                                                      """)
                                               )
                                       ) NoteDto noteDto);

    @Operation(
            summary = "刪除筆記",
            description = "根據 note_id 刪除筆記"
    )
    @ApiResponse(
            responseCode = "204",
            description = "成功刪除筆記"
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<Void> deleteNoteById(@PathVariable(name = "note_id") @Min(1) Long id);

    @Operation(
            summary = "檢查是否為筆記擁有者",
            description = "根據 JWT Token 檢查是否為該 note_id 的筆記擁有者"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功檢查是否為筆記擁有者",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "true")
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<Boolean> isNoteOwner(@PathVariable(name = "note_id") @Min(1) Long id);

    @Operation(
            summary = "[EDITED][路由名稱] 生成一個字卡",
            description = "根據 note_id，將筆記內容傳給 ChatGPT，生成一個簡答題字卡並存進資料庫"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功生成字卡",
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
    ResponseEntity<FlashcardDto> generateFlashcard(@PathVariable(name = "note_id") @Min(1) Long id,
                                                   @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                   content = @Content(
                                                                           mediaType = "application/json",
                                                                           examples = {
                                                                                   @ExampleObject(value = """
                                                                                          {
                                                                                              "type": "SHORT_ANSWER"
                                                                                          }
                                                                                          """)
                                                                           }
                                                                   )
                                                           ) GenerateFlashcardRequest request);

    @Operation(
            summary = "[EDITED][路由名稱] 生成多張字卡",
            description = "根據 note_id，自訂要生成的字卡題目數量，將筆記內容傳給 ChatGPT，生成相對應數量的字卡並存進資料庫"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功生成多張字卡",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            [
                                {
                                    "id": 2,
                                    "question": "紅黑樹和AVL樹都提供了最好的最壞情況保證，這使得它們有價值於___、___和___等時間敏感的應用。",
                                     "type": "FILL_IN_THE_BLANK",
                                     "note_id": 1,
                                     "user_id": 1,
                                     "in_blank_answers": [
                                         {
                                             "id": 2,
                                             "text": "即時應用"
                                         },
                                         {
                                             "id": 3,
                                             "text": "計算幾何"
                                         },
                                         {
                                             "id": 4,
                                             "text": "持久資料結構"
                                         }
                                     ],
                                     "full_answer": "紅黑樹和AVL樹都提供了最好的最壞情況保證，這使得它們有價值於即時應用、計算幾何和持久資料結構等時間敏感的應用。"
                                },
                                {
                                    "id": 3,
                                    "question": "紅黑樹在函數式程式設計中不常用。這句話是？",
                                    "type": "TRUE_FALSE",
                                    "note_id": 1,
                                    "user_id": 1,
                                    "true_false_answer": false
                                },
                                {
                                    "id": 4,
                                    "question": "以下哪一個是紅黑樹的特性？",
                                    "type": "MULTIPLE_CHOICE",
                                    "options": [
                                        {
                                            "id": 1,
                                            "text": "每次操作需要 O(log n) 的時間"
                                        },
                                        {
                                            "id": 2,
                                            "text": "無法保持為以前的版本"
                                        },
                                        {
                                            "id": 3,
                                            "text": "不支持持久資料結構"
                                        },
                                        {
                                            "id": 4,
                                            "text": "只能用於靜態資料結構"
                                        }
                                    ],
                                    "note_id": 1,
                                    "user_id": 1,
                                    "answer_option": {
                                        "id": 1,
                                        "text": "每次操作需要 O(log n) 的時間"
                                    }
                                }
                            ]
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<List<FlashcardDto>> generateFlashcards(@PathVariable(name = "note_id") @Min(1) Long id,
                                                          @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                          content = @Content(
                                                                                  mediaType = "application/json",
                                                                                  examples = @ExampleObject(value = """
                                                                                          {
                                                                                              "type_quantities": [
                                                                                                  {
                                                                                                      "type": "SHORT_ANSWER",
                                                                                                      "quantity":0
                                                                                                  },
                                                                                                  {
                                                                                                      "type": "FILL_IN_THE_BLANK",
                                                                                                      "quantity":1
                                                                                                  },
                                                                                                  {
                                                                                                      "type": "MULTIPLE_CHOICE",
                                                                                                      "quantity":1
                                                                                                  },
                                                                                                  {
                                                                                                      "type": "TRUE_FALSE",
                                                                                                      "quantity":1
                                                                                                  }
                                                                                              ]
                                                                                          }
                                                                                          """)
                                                                          )
                                                                  ) GenerateFlashcardsRequest request);
}


