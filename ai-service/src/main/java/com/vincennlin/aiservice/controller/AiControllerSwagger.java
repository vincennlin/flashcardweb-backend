package com.vincennlin.aiservice.controller;

import com.vincennlin.aiservice.payload.flashcard.dto.AbstractFlashcardDto;
import com.vincennlin.aiservice.payload.request.GenerateFlashcardRequest;
import com.vincennlin.aiservice.payload.request.GenerateFlashcardsRequest;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(
        name = "ChatGPT 服務",
        description = "ChatGPT 相關的 API，前端不會直接使用到"
)
@OpenAPIDefinition(
        info = @Info(
                title = "Flashcardweb ai-ws API",
                version = "1.0",
                description = "Flashcardweb ChatGPT 服務相關的 API",
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
public interface AiControllerSwagger {

    @Operation(
            summary = "測試",
            description = "根據收到的訊息，生成一個回答"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功生成回答",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            "A joke LOL"
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<String> generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message);

    @Operation(
            summary = "生成一張字卡",
            description = "根據收到的筆記與題型，生成一張字卡"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功生成簡答題字卡",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "question": "紅黑樹的特點是什麼？",
                                "type": "SHORT_ANSWER",
                                "short_answer": "紅黑樹在插入、刪除和搜尋方面提供最壞情況保證，並且能夠保持歷史版本，是最常用的持久資料結構之一。"
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<AbstractFlashcardDto> generateFlashcard(@RequestBody
                                                                   @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                           content = @Content(
                                                                                   mediaType = "application/json",
                                                                                   examples = @ExampleObject(value = """
                                                                                                  {
                                                                                                      "content": "紅黑樹和AVL樹一樣，都在插入時間、刪除時間和搜尋時間方面提供了最好的最壞情況保證。這不僅使它們在時間敏感的應用（如即時應用）中有價值，還使它們成為其他提供最壞情況保證的資料結構的基礎模板。例如，在計算幾何中使用的許多資料結構都可以基於紅黑樹實現。紅黑樹在函數式程式設計中也特別有用。在這裡，它們是最常用的持久資料結構之一，用來構造關聯陣列和集合。每次插入或刪除之後，它們能保持為以前的版本。除了 O(log n) 的時間之外，紅黑樹的持久版本每次插入或刪除還需要 O(log n) 的空間。紅黑樹是2-3-4樹的一種等價結構。換句話說，對於每個2-3-4樹，都存在至少一個數據元素是同樣次序的紅黑樹。在2-3-4樹上的插入和刪除操作也等同於在紅黑樹中的顏色翻轉和旋轉。這使得2-3-4樹成為理解紅黑樹背後邏輯的重要工具，這也是為什麼很多介紹演算法的教科書在介紹紅黑樹之前會先介紹2-3-4樹，儘管2-3-4樹在實踐中不常使用。",
                                                                                                      "type": "SHORT_ANSWER"
                                                                                                  }
                                                                                                  """)
                                                                           )
                                                                   ) GenerateFlashcardRequest request);

    @Operation(
            summary = "生成多張字卡",
            description = "根據收到的筆記以及各題型的數量，生成多張字卡"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功生成多張字卡",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            [
                                {
                                    "question": "紅黑樹的時間複雜度為多少？",
                                    "type": "SHORT_ANSWER",
                                    "short_answer": "O(log n)"
                                },
                                {
                                    "question": "紅黑樹和___樹一樣，都在插入、刪除和搜尋方面提供最好的最壞情況保證。",
                                    "type": "FILL_IN_THE_BLANK",
                                    "in_blank_answers": [
                                        {
                                            "text": "AVL"
                                        }
                                    ],
                                    "full_answer": "紅黑樹和AVL樹一樣，都在插入、刪除和搜尋方面提供最好的最壞情況保證。"
                                },
                                {
                                    "question": "紅黑樹的持久版本每次插入或刪除需要 O(log n) 的空間嗎？",
                                    "type": "MULTIPLE_CHOICE",
                                    "options": [
                                        {
                                            "text": "是"
                                        },
                                        {
                                            "text": "否"
                                        },
                                        {
                                            "text": "不一定"
                                        },
                                        {
                                            "text": "無法確定"
                                        }
                                    ],
                                    "answer_index": 1
                                },
                                {
                                    "question": "紅黑樹是2-3-4樹的一種等價結構。",
                                    "type": "TRUE_FALSE",
                                    "true_false_answer": true
                                }
                            ]
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<List<AbstractFlashcardDto>> generateFlashcards(@RequestBody
                                                                         @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                                 content = @Content(
                                                                                         mediaType = "application/json",
                                                                                         examples = @ExampleObject(value = """
                                                                                                     {
                                                                                                         "note": {
                                                                                                             "content": "紅黑樹和AVL樹一樣，都在插入時間、刪除時間和搜尋時間方面提供了最好的最壞情況保證。這不僅使它們在時間敏感的應用（如即時應用）中有價值，還使它們成為其他提供最壞情況保證的資料結構的基礎模板。例如，在計算幾何中使用的許多資料結構都可以基於紅黑樹實現。紅黑樹在函數式程式設計中也特別有用。在這裡，它們是最常用的持久資料結構之一，用來構造關聯陣列和集合。每次插入或刪除之後，它們能保持為以前的版本。除了 O(log n) 的時間之外，紅黑樹的持久版本每次插入或刪除還需要 O(log n) 的空間。紅黑樹是2-3-4樹的一種等價結構。換句話說，對於每個2-3-4樹，都存在至少一個數據元素是同樣次序的紅黑樹。在2-3-4樹上的插入和刪除操作也等同於在紅黑樹中的顏色翻轉和旋轉。這使得2-3-4樹成為理解紅黑樹背後邏輯的重要工具，這也是為什麼很多介紹演算法的教科書在介紹紅黑樹之前會先介紹2-3-4樹，儘管2-3-4樹在實踐中不常使用。"
                                                                                                         },
                                                                                                         "type_quantities": [
                                                                                                             {
                                                                                                                 "type": "SHORT_ANSWER",
                                                                                                                 "quantity": 1
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
