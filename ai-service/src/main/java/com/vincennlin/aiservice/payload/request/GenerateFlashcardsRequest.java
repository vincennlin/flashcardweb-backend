package com.vincennlin.aiservice.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vincennlin.aiservice.payload.flashcard.dto.FlashcardDto;
import com.vincennlin.aiservice.payload.flashcard.type.FlashcardType;
import com.vincennlin.aiservice.payload.note.NoteDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenerateFlashcardsRequest {

    @Schema(
            description = "要生成的字卡的筆記",
            example = "{\"note\": {\"content\": \"紅黑樹和AVL樹一樣，都在插入時間、刪除時間和搜尋時間方面提供了最好的最壞情況保證。這不僅使它們在時間敏感的應用（如即時應用）中有價值，還使它們成為其他提供最壞情況保證的資料結構的基礎模板。例如，在計算幾何中使用的許多資料結構都可以基於紅黑樹實現。紅黑樹在函數式程式設計中也特別有用。在這裡，它們是最常用的持久資料結構之一，用來構造關聯陣列和集合。每次插入或刪除之後，它們能保持為以前的版本。除了 O(log n) 的時間之外，紅黑樹的持久版本每次插入或刪除還需要 O(log n) 的空間。紅黑樹是2-3-4樹的一種等價結構。換句話說，對於每個2-3-4樹，都存在至少一個數據元素是同樣次序的紅黑樹。在2-3-4樹上的插入和刪除操作也等同於在紅黑樹中的顏色翻轉和旋轉。這使得2-3-4樹成為理解紅黑樹背後邏輯的重要工具，這也是為什麼很多介紹演算法的教科書在介紹紅黑樹之前會先介紹2-3-4樹，儘管2-3-4樹在實踐中不常使用。\"}}"
    )
    @JsonProperty(value = "note")
    private NoteDto note;

    @Schema(
            description = "要生成的字卡類型與數量",
            example = "{\"type_quantities\": [{\"type\": \"SHORT_ANSWER\", \"quantity\": 1}, {\"type\": \"FILL_IN_THE_BLANK\", \"quantity\": 1}, {\"type\": \"MULTIPLE_CHOICE\", \"quantity\": 1}, {\"type\": \"TRUE_FALSE\", \"quantity\": 1}]}"
    )
    @NotEmpty(message = "Type quantities cannot be empty")
    @JsonProperty(value = "type_quantities")
    private List<TypeQuantity> typeQuantities;

    public Message getInitialSystemMessage() {
        return new SystemMessage(
                "你的任務是根據收到的筆記，生成「簡答題」、「填充題」、「選擇題」、「是非題」四種題型的字卡。\n" +
                        "你會收到一個筆記的content，請先參考以下各種題型的json範例格式，並在收到content後，生成" +
                        "簡答題字卡" + getQuantityFor(FlashcardType.SHORT_ANSWER) + "張、" +
                        "填充題字卡" + getQuantityFor(FlashcardType.FILL_IN_THE_BLANK) + "張、" +
                        "選擇題字卡" + getQuantityFor(FlashcardType.MULTIPLE_CHOICE) + "張、" +
                        "是非題字卡" + getQuantityFor(FlashcardType.TRUE_FALSE) + "張。\n" +
                        "並以json陣列的格式回傳字卡。\n\n" +
                        FlashcardType.SHORT_ANSWER.getFormatExampleString() + "\n\n" +
                        FlashcardType.FILL_IN_THE_BLANK.getFormatExampleString() + "\n\n" +
                        FlashcardType.MULTIPLE_CHOICE.getFormatExampleString() + "\n\n" +
                        FlashcardType.TRUE_FALSE.getFormatExampleString() + "\n\n"
        );
    }

    public Message getResponseFormatExampleSystemMessage() {
        return new SystemMessage(
                "以下是一個範例的json格式回應：\n\n" +
                        getResponseExampleString() + "\n\n" +
                        "請注意，以上json回應僅為格式範例，實際生成的字卡內容請依照收到的content生成。\n\n"
        );
    }

    private String getResponseExampleString() {
        List<FlashcardDto> exampleFlashcards = new ArrayList<>();
        for (int i = 0; i < getQuantityFor(FlashcardType.SHORT_ANSWER); i++) {
            exampleFlashcards.add(FlashcardType.SHORT_ANSWER.getFlashcardExampleDto());
        }
        for (int i = 0; i < getQuantityFor(FlashcardType.FILL_IN_THE_BLANK); i++) {
            exampleFlashcards.add(FlashcardType.FILL_IN_THE_BLANK.getFlashcardExampleDto());
        }
        for (int i = 0; i < getQuantityFor(FlashcardType.MULTIPLE_CHOICE); i++) {
            exampleFlashcards.add(FlashcardType.MULTIPLE_CHOICE.getFlashcardExampleDto());
        }
        for (int i = 0; i < getQuantityFor(FlashcardType.TRUE_FALSE); i++) {
            exampleFlashcards.add(FlashcardType.TRUE_FALSE.getFlashcardExampleDto());
        }
        try{
            return getObjectMapper().writeValueAsString(exampleFlashcards);
        } catch (Exception e) {
            return "Exception Occurred while Generating Flashcards Example Json String";
        }
    }

    private Integer getQuantityFor(FlashcardType flashcardType) {
        return typeQuantities.stream()
                .filter(typeQuantity -> typeQuantity.getType().equals(flashcardType))
                .findFirst()
                .map(TypeQuantity::getQuantity)
                .orElse(0);
    }

    private ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }
}
