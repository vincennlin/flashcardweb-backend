package com.vincennlin.aiservice.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenerateSummaryRequest {

    @Schema(
            description = "要生成摘要的筆記內容",
            example = "紅黑樹和AVL樹一樣，都在插入時間、刪除時間和搜尋時間方面提供了最好的最壞情況保證。這不僅使它們在時間敏感的應用（如即時應用）中有價值，還使它們成為其他提供最壞情況保證的資料結構的基礎模板。例如，在計算幾何中使用的許多資料結構都可以基於紅黑樹實現。紅黑樹在函數式程式設計中也特別有用。在這裡，它們是最常用的持久資料結構之一，用來構造關聯陣列和集合。每次插入或刪除之後，它們能保持為以前的版本。除了 O(log n) 的時間之外，紅黑樹的持久版本每次插入或刪除還需要 O(log n) 的空間。紅黑樹是2-3-4樹的一種等價結構。換句話說，對於每個2-3-4樹，都存在至少一個數據元素是同樣次序的紅黑樹。在2-3-4樹上的插入和刪除操作也等同於在紅黑樹中的顏色翻轉和旋轉。這使得2-3-4樹成為理解紅黑樹背後邏輯的重要工具，這也是為什麼很多介紹演算法的教科書在介紹紅黑樹之前會先介紹2-3-4樹，儘管2-3-4樹在實踐中不常使用。"
    )
    @JsonProperty(
            value = "note_content"
    )
    private String noteContent;

    public Message getInitialSystemMessage() {
        return new SystemMessage(
                "你的任務是根據收到的筆記內容，生成一個摘要。\n" +
                        "接下來你會收到一個筆記的content，請在收到content後，生成一個客觀且讓人馬上能知道該筆記所述的內容的摘要。\n" +
                        "請注意，生成的摘要字數請介於5至15個字之間。\n"
        );
    }
}
