package com.vincennlin.noteservice.controller.deck;

import com.vincennlin.noteservice.payload.deck.dto.DeckDto;
import com.vincennlin.noteservice.payload.deck.request.CreateDeckRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "[NEW] Deck Controller",
        description = "牌組相關的 API"
)
public interface DeckControllerSwagger {

    @Operation(
            summary = "取得所有牌組",
            description = "取得所有牌組，會以巢狀的方式呈現所有牌組，包含子牌組"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功取得所有牌組",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            [
                                 {
                                     "id": 1,
                                     "name": "台科大",
                                     "user_id": 2,
                                     "parent_id": null,
                                     "note_count": 2,
                                     "total_flashcard_count": 5,
                                     "review_flashcard_count": 5,
                                     "sub_decks": [
                                         {
                                             "id": 2,
                                             "name": "資料結構",
                                             "user_id": 2,
                                             "parent_id": 1,
                                             "note_count": 1,
                                             "total_flashcard_count": 1,
                                             "review_flashcard_count": 1,
                                             "sub_decks": [
                                                 {
                                                     "id": 4,
                                                     "name": "紅黑樹",
                                                     "user_id": 2,
                                                     "parent_id": 2,
                                                     "note_count": 0,
                                                     "total_flashcard_count": 0,
                                                     "review_flashcard_count": 0,
                                                     "sub_decks": []
                                                 }
                                             ]
                                         },
                                         {
                                             "id": 3,
                                             "name": "演算法",
                                             "user_id": 2,
                                             "parent_id": 1,
                                             "note_count": 0,
                                             "total_flashcard_count": 0,
                                             "review_flashcard_count": 0,
                                             "sub_decks": []
                                         }
                                     ]
                                 },
                                 {
                                     "id": 5,
                                     "name": "線上課程",
                                     "user_id": 2,
                                     "parent_id": null,
                                     "note_count": 0,
                                     "total_flashcard_count": 0,
                                     "review_flashcard_count": 0,
                                     "sub_decks": []
                                 }
                             ]
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<List<DeckDto>> getAllDecks();

    @Operation(
            summary = "取得牌組",
            description = "取得指定 id 的牌組，包含子牌組"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功取得牌組",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "id": 1,
                                "name": "台科大",
                                "user_id": 2,
                                "parent_id": null,
                                "note_count": 2,
                                "total_flashcard_count": 5,
                                "review_flashcard_count": 5,
                                "sub_decks": [
                                    {
                                        "id": 3,
                                        "name": "演算法",
                                        "user_id": 2,
                                        "parent_id": 1,
                                        "note_count": 0,
                                        "total_flashcard_count": 0,
                                        "review_flashcard_count": 0,
                                        "sub_decks": []
                                    },
                                    {
                                        "id": 2,
                                        "name": "資料結構",
                                        "user_id": 2,
                                        "parent_id": 1,
                                        "note_count": 1,
                                        "total_flashcard_count": 1,
                                        "review_flashcard_count": 1,
                                        "sub_decks": [
                                            {
                                                "id": 4,
                                                "name": "紅黑樹",
                                                "user_id": 2,
                                                "parent_id": 2,
                                                "note_count": 0,
                                                "total_flashcard_count": 0,
                                                "review_flashcard_count": 0,
                                                "sub_decks": []
                                            }
                                        ]
                                    }
                                ]
                            }
                            """)
            )

    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<DeckDto> getDeckById(@PathVariable(name = "deck_id") Long deckId);

    @Operation(
            summary = "取得牌組的所有筆記 id",
            description = "取得指定 id 的牌組的所有筆記 id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功取得牌組的所有筆記 id",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            [
                                1,
                                2
                            ]
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<List<Long>> getNoteIdsByDeckId(@PathVariable(name = "deck_id") Long deckId);

    @Operation(
            summary = "新增牌組",
            description = "新增一個牌組。每個牌組可以有父牌組跟子牌組，創建時可以指定 parent_id 。\n" +
                    "有指定會將自己加進該父牌組的子牌組內，沒有指定則變為最高層級的牌組"
    )
    @ApiResponse(
            responseCode = "201",
            description = "成功新增牌組",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "id": 2,
                                "name": "演算法",
                                "user_id": 2,
                                "parent_id": null,
                                "note_count": 0,
                                "total_flashcard_count": 0,
                                "review_flashcard_count": 0,
                                "sub_decks": []
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<DeckDto> createDeck(@Valid @RequestBody
                                       @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                               content = @Content(
                                                       mediaType = "application/json",
                                                       examples = @ExampleObject(value = """
                                                               {
                                                                   "name": "演算法",
                                                                   "parent_id": null
                                                               }
                                                               """)
                                               )
                                       ) CreateDeckRequest request);

    @Operation(
            summary = "更新牌組",
            description = "更新牌組，同時可以更新 parent_id。"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功更新牌組",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "id": 2,
                                "name": "（已更新）演算法",
                                "user_id": 2,
                                "parent_id": 1,
                                "note_count": 0,
                                "total_flashcard_count": 0,
                                "review_flashcard_count": 0,
                                "sub_decks": []
                            }
                            """)
            )

    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<DeckDto> updateDeck(@PathVariable(name = "deck_id") Long deckId,
                                              @Valid @RequestBody
                                              @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                      content = @Content(
                                                              mediaType = "application/json",
                                                              examples = @ExampleObject(value = """
                                                                      {
                                                                          "name": "（已更新）演算法",
                                                                          "parent_id": 1
                                                                      }
                                                                      """)
                                                      )
                                              ) DeckDto deckDto);

    @Operation(
            summary = "刪除牌組",
            description = "刪除牌組，同時會刪除所有子牌組"
    )
    @ApiResponse(
            responseCode = "204",
            description = "成功刪除牌組"
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<Void> deleteDeckById(@PathVariable(name = "deck_id") Long deckId);

    @Operation(
            summary = "判斷是否為牌組擁有者",
            description = "判斷是否為指定 id 的牌組的擁有者"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功判斷是否為牌組擁有者",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "true")
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<Boolean> isDeckOwner(@PathVariable(name = "deck_id") Long deckId);
}
