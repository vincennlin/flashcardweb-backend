package com.vincennlin.courseservice.controller;

import com.vincennlin.courseservice.constant.AppConstants;
import com.vincennlin.courseservice.payload.course.dto.CourseDto;
import com.vincennlin.courseservice.payload.course.page.CoursePageResponse;
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
}
