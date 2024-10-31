package com.vincennlin.courseservice.controller;

import com.vincennlin.courseservice.constant.AppConstants;
import com.vincennlin.courseservice.payload.course.dto.CourseDto;
import com.vincennlin.courseservice.payload.course.page.CoursePageResponse;
import com.vincennlin.courseservice.payload.flashcard.FlashcardDto;
import com.vincennlin.courseservice.payload.request.CopyFlashcardsToDeckRequest;
import com.vincennlin.courseservice.payload.request.CreateCourseRequest;
import com.vincennlin.courseservice.payload.request.FlashcardIdsRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Course Controller",
        description = "課程相關的 API"
)
public interface CourseControllerSwagger {

    @Operation(
            summary = "取得所有課程",
            description = "取得所有課程"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功取得所有課程",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "content": [
                                    {
                                        "id": 4,
                                        "name": "資料結構",
                                        "creator_id": 2,
                                        "user_count": 1,
                                        "user_ids": [
                                            2
                                        ],
                                        "flashcard_count": 1,
                                        "flashcard_ids": [
                                            44
                                        ]
                                    },
                                    {
                                        "id": 6,
                                        "name": "演算法",
                                        "creator_id": 2,
                                        "user_count": 1,
                                        "user_ids": [
                                            2
                                        ],
                                        "flashcard_count": 2,
                                        "flashcard_ids": [
                                            41,
                                            44
                                        ]
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
    ResponseEntity<CoursePageResponse> getAllCourses(
            @RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) @Min(0) Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) @Max(100) @Min(1) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir);

    @Operation(
            summary = "取得課程",
            description = "根據課程 id 取得課程"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功取得課程",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "id": 6,
                                "name": "演算法",
                                "creator_id": 2,
                                "user_count": 1,
                                "user_ids": [
                                    2
                                ],
                                "flashcard_count": 0,
                                "flashcard_ids": []
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<CourseDto> getCourseById(@PathVariable(name = "course_id") @Min(1) Long courseId);

    @Operation(
            summary = "創建課程",
            description = "創建課程"
    )
    @ApiResponse(
            responseCode = "201",
            description = "成功創建課程",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "id": 6,
                                "name": "演算法",
                                "creator_id": 2,
                                "user_count": 1,
                                "user_ids": [
                                    2
                                ],
                                "flashcard_count": 0,
                                "flashcard_ids": []
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<CourseDto> createCourse(@RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                        "name": "演算法"
                                    }
                                    """)
                    )
            ) CreateCourseRequest request);

    @Operation(
            summary = "更新課程",
            description = "更新課程"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功更新課程",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "id": 6,
                                "name": "[new] 演算法",
                                "creator_id": 2,
                                "user_count": 1,
                                "user_ids": [
                                    2
                                ],
                                "flashcard_count": 0,
                                "flashcard_ids": []
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<CourseDto> updateCourse(@PathVariable(name = "course_id") @Min(1) Long courseId,
                                                  @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                          content = @Content(
                                                                  mediaType = "application/json",
                                                                  examples = @ExampleObject(value = """
                                                                          {
                                                                              "name": "[new] 演算法"
                                                                          }
                                                                          """)
                                                          )
                                                  ) CourseDto courseDto);

    @Operation(
            summary = "刪除課程",
            description = "刪除課程"
    )
    @ApiResponse(
            responseCode = "204",
            description = "成功刪除課程"
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<Void> deleteCourse(@PathVariable(name = "course_id") @Min(1) Long courseId);

    @Operation(
            summary = "加入課程",
            description = "加入課程 id 加入課程"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功加入課程",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "id": 6,
                                "name": "演算法",
                                "creator_id": 2,
                                "user_count": 1,
                                "user_ids": [
                                    2
                                ],
                                "flashcard_count": 0,
                                "flashcard_ids": []
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<CourseDto> enrollCourse(@PathVariable(name = "course_id") @Min(1) Long courseId);

    @Operation(
            summary = "退出課程",
            description = "根據課程 id 退出課程"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功退出課程",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "id": 6,
                                "name": "演算法",
                                "creator_id": 2,
                                "user_count": 0,
                                "user_ids": [],
                                "flashcard_count": 0,
                                "flashcard_ids": []
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<CourseDto> leaveCourse(@PathVariable(name = "course_id") @Min(1) Long courseId);

    @Operation(
            summary = "將字卡加入課程",
            description = "根據課程 id 與字卡 id 將字卡加入課程，只有當前用戶的字卡可以被加入課程"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功將字卡加入課程",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "id": 6,
                                "name": "演算法",
                                "creator_id": 2,
                                "user_count": 1,
                                "user_ids": [
                                    2
                                ],
                                "flashcard_count": 4,
                                "flashcard_ids": [
                                    41,
                                    42,
                                    43,
                                    44
                                ]
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<CourseDto> addFlashcardsToCourse(@PathVariable(name = "course_id") @Min(1) Long courseId,
                                                           @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                   content = @Content(
                                                                           mediaType = "application/json",
                                                                           examples = @ExampleObject(value = """
                                                                                   {
                                                                                       "flashcard_ids": [41, 42, 43, 44]
                                                                                   }
                                                                                   """)
                                                                   )
                                                           ) FlashcardIdsRequest request);

    @Operation(
            summary = "將字卡從課程移除",
            description = "根據課程 id 與字卡 id 將字卡從課程移除"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功將字卡從課程移除",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "id": 6,
                                "name": "演算法",
                                "creator_id": 2,
                                "user_count": 1,
                                "user_ids": [
                                    2
                                ],
                                "flashcard_count": 2,
                                "flashcard_ids": [
                                    41,
                                    44
                                ]
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<CourseDto> removeFlashcardsFromCourse(@PathVariable(name = "course_id") @Min(1) Long courseId,
                                                                @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                        content = @Content(
                                                                                mediaType = "application/json",
                                                                                examples = @ExampleObject(value = """
                                                                                        {
                                                                                            "flashcard_ids": [42,43]
                                                                                        }
                                                                                        """)
                                                                        )
                                                                ) FlashcardIdsRequest request);

    @Operation(
            summary = "[NEW] 儲存字卡到牌組",
            description = "將分享至課程的字卡，根據課程 id 與字卡 id 複製字卡到牌組"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功儲存字卡到牌組",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            [
                                {
                                    "id": 183,
                                    "question": "什麼是紅黑樹的主要特性？",
                                    "type": "SHORT_ANSWER",
                                    "note_id": 40,
                                    "user_id": 2,
                                    "short_answer": "紅黑樹在插入、刪除和搜尋操作上提供最好的最壞情況保證，並且支持持久資料結構。"
                                },
                                {
                                    "id": 184,
                                    "question": "紅黑樹的時間複雜度在插入和刪除操作中是 O(log n)，而持久版本在插入或刪除還需要 O(log n) 的___。",
                                    "type": "FILL_IN_THE_BLANK",
                                    "note_id": 40,
                                    "user_id": 2,
                                    "in_blank_answers": [
                                        {
                                            "id": 130,
                                            "text": "空間"
                                        },
                                        {
                                            "id": 131,
                                            "text": "時間"
                                        },
                                        {
                                            "id": 132,
                                            "text": "性能"
                                        }
                                    ],
                                    "full_answer": "紅黑樹的時間複雜度在插入和刪除操作中是 O(log n)，而持久版本在插入或刪除還需要 O(log n) 的空間。"
                                },
                                {
                                    "id": 185,
                                    "question": "紅黑樹和哪種樹結構是等價的？",
                                    "type": "MULTIPLE_CHOICE",
                                    "options": [
                                        {
                                            "id": 149,
                                            "text": "AVL 樹"
                                        },
                                        {
                                            "id": 150,
                                            "text": "2-3-4 樹"
                                        },
                                        {
                                            "id": 151,
                                            "text": "B 樹"
                                        },
                                        {
                                            "id": 152,
                                            "text": "線性樹"
                                        }
                                    ],
                                    "note_id": 40,
                                    "user_id": 2,
                                    "answer_option": {
                                        "id": 150,
                                        "text": "2-3-4 樹"
                                    }
                                },
                                {
                                    "id": 186,
                                    "question": "紅黑樹不適用於即時應用。",
                                    "type": "TRUE_FALSE",
                                    "note_id": 40,
                                    "user_id": 2,
                                    "true_false_answer": false
                                }
                            ]
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<List<FlashcardDto>> copyFlashcardsToDeck(@PathVariable(name = "course_id") @Min(1) Long courseId,
                                                            @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                    content = @Content(
                                                                            mediaType = "application/json",
                                                                            examples = @ExampleObject(value = """
                                                                                    {
                                                                                        "flashcard_ids": [135, 136, 137, 138],
                                                                                        "deck_id": 9
                                                                                    }
                                                                                    """)
                                                                    )
                                                            ) CopyFlashcardsToDeckRequest request);
}
