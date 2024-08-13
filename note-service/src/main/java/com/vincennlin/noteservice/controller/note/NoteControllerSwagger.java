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
            description = "新增筆記，如果沒有附上 summary ，則由 ChatGPT 生成筆記摘要"
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
                                "summary": "紅黑樹的特性與應用概述",
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
                                                                                "content": "This is a note about Java",
                                                                                "summary": "A note about Java"
                                                                            }
                                                                            """)
                                               )
                                       ) NoteDto noteDto);

    @Operation(
            summary = "[NEW] 根據檔案新增筆記",
            description = "根據檔案新增筆記並存進資料庫。 目前支援 .pdf .txt .docx 三種格式。必須指定 mediaType 為 multipart/form-data。 " +
                    "Request body 必須是檔案，其中 key 為 'file'，value 為該檔案"
    )
    @ApiResponse(
            responseCode = "201",
            description = "成功根據檔案創建筆記",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "id": 7,
                                "content": "package com.vincennlin.misc.findmiddlenode; \\n \\npublic class LinkedList { \\n \\n    private Node head; \\n    private Node tail; \\n \\n    class Node { \\n        int value; \\n        Node next; \\n \\n        Node(int value) { \\n            this.value = value; \\n        } \\n    } \\n \\n    public LinkedList(int value) { \\n        Node newNode = new Node(value); \\n        head = newNode; \\n        tail = newNode; \\n    } \\n \\n    public Node getHead() { \\n        return head; \\n    } \\n \\n    public Node getTail() { \\n        return tail; \\n    } \\n \\n    public void printList() { \\n        Node temp = head; \\n        while (temp != null) { \\n            System.out.println(temp.value); \\n            temp = temp.next; \\n        } \\n    } \\n \\n    public void printAll() { \\n        if (head == null) { \\n            System.out.println(\\"Head: null\\"); \\n            System.out.println(\\"Tail: null\\"); \\n        } else { \\n            System.out.println(\\"Head: \\" + head.value); \\n            System.out.println(\\"Tail: \\" + tail.value); \\n        } \\n        System.out.println(\\"\\\\nLinked List:\\"); \\n        if (head == null) { \\n            System.out.println(\\"empty\\"); \\n        } else { \\n            printList(); \\n        } \\n    } \\n \\n    public void makeEmpty() { \\n        head = null; \\n        tail = null; \\n    } \\n \\n    public void append(int value) { \\n        Node newNode = new Node(value); \\n        if (head == null) { \\n            head = newNode; \\n            tail = newNode; \\n        } else { \\n            tail.next = newNode; \\n            tail = newNode; \\n        } \\n    } \\n     \\n    public Node findMiddleNode() { \\n        Node slow = head; \\n        Node fast = head; \\n        while (fast != null && fast.next != null) { \\n            slow = slow.next; \\n            fast = fast.next.next; \\n        } \\n        return slow; \\n    } \\n} \\n",
                                "summary": "鏈表類別及中間節點查找方法的程式碼",
                                "user_id": 2,
                                "deck_id": 6,
                                "total_flashcard_count": 0,
                                "review_flashcard_count": 0,
                                "flashcards": null,
                                "date_created": "2024-08-08T11:00:44.661082",
                                "last_updated": "2024-08-08T11:00:44.661135"
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<NoteDto> createNoteFromFile(@PathVariable(name = "deck_id") @Min(1) Long deckId,
                                              @RequestPart("file")
                                              @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                      description = ".pdf .txt .docx 檔案",
                                                      required = true,
                                                      content = @Content(
                                                              mediaType = "multipart/form-data",
                                                              examples = @ExampleObject(value = """
                                                                    {
                                                                        "file": "檔案"
                                                                    }
                                                                    """)
                                                      )
                                              ) MultipartFile file);

//    @Operation(
//            summary = "[NEW] 根據圖片新增筆記",
//            description = "根據圖片新增筆記並存進資料庫。 必須指定 mediaType 為 multipart/form-data。 " +
//                    "Request body 必須是圖片檔案，其中 key 為 'file'，value 為該圖片"
//    )
//    @ApiResponse(
//            responseCode = "201",
//            description = "成功根據圖片創建筆記",
//            content = @Content(
//                    mediaType = "application/json",
//                    examples = @ExampleObject(value = """
//                            {
//                                "id": 4,
//                                "content": "& docker-compose.yml X Y\\\\ console_2 [mysql local]\\nversion: \\"3.8\\"\\n> services:\\n> mysqldb:\\ncontainer_name: mysqldb\\nimage: mysqgl\\nenvironment:\\nMYSQL_ROOT_PASSWORD: Passweord\\nMYSQL_DATABASE: employeedb\\nnetworks:\\nspringboot-mysql-net:\\nnetworks:\\nspringboot-mysql-net:\\n5 driver: bridge\\n",
//                                "user_id": 2,
//                                "deck_id": 5,
//                                "total_flashcard_count": 0,
//                                "review_flashcard_count": 0,
//                                "flashcards": null,
//                                "date_created": "2024-08-07T12:13:40.517023",
//                                "last_updated": "2024-08-07T12:13:40.517072"
//                            }
//                            """)
//            )
//    )
//    @SecurityRequirement(name = "Bear Authentication")
//    ResponseEntity<NoteDto> createNoteFromImage(@PathVariable(name = "deck_id") @Min(1) Long deckId,
//                                                @PathVariable(name = "language") ExtractLanguage language,
//                                                @RequestPart("file") MultipartFile file);

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
                                "summary": "紅黑樹的特性與應用概述",
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


