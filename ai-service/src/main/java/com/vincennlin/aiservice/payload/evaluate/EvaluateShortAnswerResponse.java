package com.vincennlin.aiservice.payload.evaluate;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class EvaluateShortAnswerResponse {

    @JsonProperty(value = "is_correct")
    private boolean isCorrect;

    @JsonProperty(value = "score")
    private Integer score;

    @JsonProperty(value = "feedback")
    private String feedback;

    public Message getExampleResponseString() {
        EvaluateShortAnswerResponse exampleResponse = new EvaluateShortAnswerResponse();
        exampleResponse.setCorrect(true);
        exampleResponse.setScore(70);
        exampleResponse.setFeedback("涵蓋了主要概念，但缺少“系統化的方法”和“資訊安全的風險”的細節。請加上這些要點以提升完整性。");

        return new SystemMessage(
                "以下是一個根據以上回答所評估簡答題的範例 response，你的回應請使用 json 格式做回應：\n" +
                exampleResponse.toString() + "\n"
        );
    }
}
