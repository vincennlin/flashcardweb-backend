package com.vincennlin.flashcardservice.controller.review;

import com.vincennlin.flashcardservice.payload.flashcard.dto.FlashcardDto;
import com.vincennlin.flashcardservice.payload.review.dto.ReviewStateDto;
import com.vincennlin.flashcardservice.payload.review.request.ReviewRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Review Controller",
        description = "字卡複習相關的 API"
)
public interface ReviewControllerSwagger {

    @Operation(
            summary = "取得要複習的字卡",
            description = "取得所有到了複習時間的字卡"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功取得要複習的字卡",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            [
                                {
                                    "id": 2,
                                    "question": "紅黑樹的時間複雜度是多少？",
                                    "type": "SHORT_ANSWER",
                                    "tags": [],
                                    "note_id": 1,
                                    "user_id": 2,
                                    "review_info": {
                                        "review_level": 0,
                                        "review_interval": 1,
                                        "next_review": "2024-07-27T19:07:06.552139"
                                    },
                                    "short_answer": "紅黑樹的插入、刪除和搜尋時間複雜度都是 O(log n)。"
                                },
                                {
                                    "id": 3,
                                    "question": "紅黑樹在函數式程式設計中有什麼用途？",
                                    "type": "SHORT_ANSWER",
                                    "tags": [],
                                    "note_id": 1,
                                    "user_id": 2,
                                    "review_info": {
                                        "review_level": 0,
                                        "review_interval": 1,
                                        "next_review": "2024-07-27T19:07:06.552311"
                                    },
                                    "short_answer": "紅黑樹是最常用的持久資料結構之一，用來構造關聯陣列和集合。"
                                },
                                {
                                    "id": 4,
                                    "question": "紅黑樹可以被視為哪一種樹的等價結構？___樹。",
                                    "type": "FILL_IN_THE_BLANK",
                                    "tags": [],
                                    "note_id": 1,
                                    "user_id": 2,
                                    "review_info": {
                                        "review_level": 0,
                                        "review_interval": 1,
                                        "next_review": "2024-07-27T19:07:06.553835"
                                    },
                                    "in_blank_answers": [
                                        {
                                            "id": 1,
                                            "text": "2-3-4"
                                        }
                                    ],
                                    "full_answer": "紅黑樹可以被視為2-3-4樹的等價結構。"
                                }
                            ]
                            """)
            )
    )
    ResponseEntity<List<FlashcardDto>> getFlashcardsToReview();

    @Operation(
            summary = "[EDITED][路由名稱] 取得該字卡的複習歷史紀錄",
            description = "根據 flashcard_id 取得該字卡的複習歷史紀錄"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功取得字卡的複習歷史紀錄",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            [
                                {
                                    "id": 1,
                                    "review_option": "GOOD",
                                    "review_level": 0,
                                    "review_interval": 1,
                                    "next_review": "2024-07-27T18:57:04.743438"
                                },
                                {
                                    "id": 2,
                                    "review_option": "EASY",
                                    "review_level": 1,
                                    "review_interval": 3,
                                    "last_reviewed": "2024-07-27T18:58:32.657087",
                                    "next_review": "2024-07-30T18:58:32.657087"
                                },
                                {
                                    "id": 3,
                                    "review_option": "HARD",
                                    "review_level": 2,
                                    "review_interval": 6,
                                    "last_reviewed": "2024-07-27T18:59:54.172888",
                                    "next_review": "2024-08-02T18:59:54.172888"
                                }
                            ]
                            """)
            )

    )
    ResponseEntity<List<ReviewStateDto>> getReviewHistoryByFlashcardId(@PathVariable(name = "flashcard_id") Long flashcardId);

    @Operation(
            summary = "[EDITED][路由名稱] 複習字卡",
            description = "根據 flashcard_id 複習字卡，並更新字卡的複習狀態。總共有四種 Review option: AGAIN, HARD, GOOD, EASY"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功複習字卡",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "id": 1,
                                "question": "紅黑樹的主要特點是什麼？",
                                "type": "SHORT_ANSWER",
                                "tags": [],
                                "note_id": 1,
                                "user_id": 2,
                                "review_info": {
                                    "review_level": 1,
                                    "review_interval": 3,
                                    "last_reviewed": "2024-07-27T18:58:32.657087",
                                    "next_review": "2024-07-30T18:58:32.657087"
                                },
                                "short_answer": "紅黑樹在插入、刪除和搜尋時間方面提供最壞情況保證，並且是持久資料結構，能保持歷史版本。"
                            }
                            """)
            )
    )
    ResponseEntity<FlashcardDto> reviewFlashcard(@PathVariable(name = "flashcard_id") Long flashcardId,
                                                        @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                content = @Content(
                                                                        mediaType = "application/json",
                                                                        examples = @ExampleObject(value = """
                                                                                {
                                                                                    "review_option": "GOOD"
                                                                                }
                                                                                """)
                                                                )
                                                        ) ReviewRequest request);

    @Operation(
            summary = "[EDITED][路由名稱] 取消上一次複習紀錄",
            description = "根據 flashcard_id 取消上一次複習紀錄，類似 Undo"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功取消上一次複習紀錄",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "id": 1,
                                "question": "紅黑樹的主要特點是什麼？",
                                "type": "SHORT_ANSWER",
                                "tags": [],
                                "note_id": 1,
                                "user_id": 2,
                                "review_info": {
                                    "review_level": 2,
                                    "review_interval": 6,
                                    "last_reviewed": "2024-07-27T18:59:54.172888",
                                    "next_review": "2024-08-02T18:59:54.172888"
                                },
                                "short_answer": "紅黑樹在插入、刪除和搜尋時間方面提供最壞情況保證，並且是持久資料結構，能保持歷史版本。"
                            }
                            """)
            )
    )
    ResponseEntity<FlashcardDto> undoReviewFlashcard(@PathVariable(name = "flashcard_id") Long flashcardId);
}
