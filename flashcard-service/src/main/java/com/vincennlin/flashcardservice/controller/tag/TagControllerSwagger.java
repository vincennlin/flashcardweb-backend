package com.vincennlin.flashcardservice.controller.tag;

import com.vincennlin.flashcardservice.payload.tag.dto.TagDto;
import com.vincennlin.flashcardservice.payload.tag.request.EditFlashcardTagsRequest;
import com.vincennlin.flashcardservice.payload.tag.response.EditFlashcardTagsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(
        name = "Tag Controller",
        description = "字卡標籤相關的 API"
)
public interface TagControllerSwagger {

    @Operation(
            summary = "取得所有標籤",
            description = "取得該用戶的所有標籤"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功取得所有標籤",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            [
                                {
                                    "id": 8,
                                    "tag_name": "台科",
                                    "flashcard_count": 3
                                },
                                {
                                    "id": 7,
                                    "tag_name": "樹",
                                    "flashcard_count": 2
                                },
                                {
                                    "id": 5,
                                    "tag_name": "資料結構",
                                    "flashcard_count": 2
                                }
                            ]
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<List<TagDto>> getAllTags();

    @Operation(
            summary = "取得特定字卡的所有標籤",
            description = "根據 flashcard_id 取得特定字卡的所有標籤"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功取得特定字卡的所有標籤",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            [
                                {
                                    "id": 1,
                                    "tag_name": "資料結構"
                                },
                                {
                                    "id": 2,
                                    "tag_name": "演算法"
                                }
                            ]
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<List<TagDto>> getTagsByFlashcardId(@PathVariable(name = "flashcard_id") Long flashcardId);

    @Operation(
            summary = "取得特定標籤",
            description = "根據 tag_id 取得特定標籤"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功取得特定標籤",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "id": 8,
                                "tag_name": "資料結構",
                                "flashcard_count": 3
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<TagDto> getTagById(@PathVariable(name = "tag_id") Long tagId);

    @Operation(
            summary = "新增標籤",
            description = "建立一個新標籤"
    )
    @ApiResponse(
            responseCode = "201",
            description = "成功建立新標籤",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "id": 9,
                                "tag_name": "演算法",
                                "flashcard_count": 0
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<TagDto> createTag(@RequestBody
                                     @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                             content = @Content(
                                                     mediaType = "application/json",
                                                     examples = @ExampleObject(value = """
                                                             {
                                                                 "tag_name": "演算法"
                                                             }
                                                             """)
                                             )
                                     ) TagDto tagDto);

    @Operation(
            summary = "更新標籤",
            description = "根據 tag_id 更新特定標籤"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功更新特定標籤",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "id": 8,
                                "tag_name": "資料結構",
                                "flashcard_count": 3
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<TagDto> updateTag(@PathVariable(name = "tag_id") Long tagId,
                                     @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                             content = @Content(
                                                     mediaType = "application/json",
                                                     examples = @ExampleObject(value = """
                                                             {
                                                                 "tag_name": "資料結構"
                                                             }
                                                             """)
                                             )
                                     ) TagDto tagDto);

    @Operation(
            summary = "為特定字卡加上標籤",
            description = "根據 flashcard_id 為特定字卡加上標籤，如果這個標籤不存在則會建立一個新標籤"
    )
    @ApiResponse(
            responseCode = "201",
            description = "成功為特定字卡新增標籤",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "id": 8,
                                "tag_name": "資料結構",
                                "flashcard_count": 3
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<TagDto> addTagToFlashcard(@PathVariable(name = "flashcard_id") Long flashcardId,
                                             @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                     content = @Content(
                                                             mediaType = "application/json",
                                                             examples = @ExampleObject(value = """
                                                                     {
                                                                         "tag_name": "資料結構"
                                                                     }
                                                                     """)
                                                     )
                                             ) TagDto tagDto);

    @Operation(
            summary = "編輯特定字卡的標籤",
            description = "根據 flashcard_id 編輯特定字卡的標籤"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功編輯特定字卡的標籤",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "flashcard_id": 1,
                                "tags": [
                                    {
                                        "tag_name": "資料結構"
                                    },
                                    {
                                        "tag_name": "樹"
                                    },
                                    {
                                        "tag_name": "台科"
                                    }
                                ]
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<EditFlashcardTagsResponse> editFlashcardTags(@PathVariable(name = "flashcard_id") Long flashcardId,
                                                                @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                        content = @Content(
                                                                                mediaType = "application/json",
                                                                                examples = @ExampleObject(value = """
                                                                                               {
                                                                                                   "tags": [
                                                                                                       {
                                                                                                           "tag_name": "資料結構"
                                                                                                       },
                                                                                                       {
                                                                                                           "tag_name": "樹"
                                                                                                       },
                                                                                                       {
                                                                                                           "tag_name": "台科"
                                                                                                       }
                                                                                                   ]
                                                                                               }
                                                                                               """)
                                                                        )
                                                                ) EditFlashcardTagsRequest request);

    @Operation(
            summary = "刪除標籤",
            description = "根據 tag_id 刪除特定標籤"
    )
    @ApiResponse(
            responseCode = "204",
            description = "成功刪除特定標籤"
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<Void> deleteTagById(@PathVariable(name = "tag_id") Long tagId);

    @Operation(
            summary = "移除特定字卡的特定標籤",
            description = "根據 flashcard_id 移除特定字卡的特定標籤"
    )
    @ApiResponse(
            responseCode = "204",
            description = "成功移除特定字卡的特定標籤"
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<Void> removeTagFromFlashcard(@PathVariable(name = "flashcard_id") Long flashcardId,
                                                @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                        content = @Content(
                                                                mediaType = "application/json",
                                                                examples = @ExampleObject(value = """
                                                                        {
                                                                            "tag_name": "資料結構"
                                                                        }
                                                                        """)
                                                        )
                                                ) TagDto tagDto);
}
