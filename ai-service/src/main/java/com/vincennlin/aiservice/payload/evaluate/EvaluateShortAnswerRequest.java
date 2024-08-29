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
public class EvaluateShortAnswerRequest {

    @JsonProperty(value = "question")
    private String question;

    @JsonProperty(value = "answer")
    private String answer;

    @JsonProperty(value = "user_answer")
    private String userAnswer;

    public String toString() {
        return "EvaluateShortAnswerRequest{" +
                "question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", userAnswer='" + userAnswer + '\'' +
                '}';
    }

    public Message getInitialSystemMessage() {
        return new SystemMessage(
                "你的任務是根據收到的用戶答案、題目以及標準答案，來批改並評估簡答題的正確性。\n"  +
                        "你會收到 question、answer、user_answer 三個欄位，請根據這三個欄位來判斷用戶答案是否正確，同時打分數(score)與給予回饋(feedback)。\n" +
                        "請注意，user_answer 只要大概與 answer 相同就可以視為正確，批改不需要太過嚴苛。\n" +
                        "分數請介於 0 ~ 100 分，回饋字數請不要超過 50 字，" +
                        "如果你打的分數超過 60 分，請在 is_correct 的部分回傳 true；否則，請回傳 false。\n" +
                        "請參考以下範例 request 與 response：\n\n"
        );
    }

    public Message getExampleResponseString() {
        EvaluateShortAnswerRequest exampleRequest = new EvaluateShortAnswerRequest();
        exampleRequest.setQuestion("風險管理的基本概念是什麼？");
        exampleRequest.setAnswer("風險管理的基本概念是透過系統化的方法來辨識、評估及應對資訊安全的風險，以控制可能造成的損失。");
        exampleRequest.setUserAnswer("系統化方式辨識、評估、應對風險，以控制損失。");

        return new SystemMessage(
                "以下是一個評估簡答題的範例 request：\n" +
                exampleRequest.toString() + "\n");
    }
}
