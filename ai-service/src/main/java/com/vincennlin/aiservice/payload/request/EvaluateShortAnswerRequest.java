package com.vincennlin.aiservice.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    public Message getInitialSystemMessage() {
        return new SystemMessage(
                "你的任務是根據收到的用戶答案、題目以及標準答案，來批改並評估簡答題的正確性。\n"  +
                        "你會收到 question、answer、user_answer 三個欄位，請根據這三個欄位來判斷用戶答案是否正確。\n" +
                        "請注意，user_answer 只要大概與 answer 相同就可以視為正確，批改不需要太過嚴苛。\n" +
                        "如果你評估的 user_answer 正確，請回傳 true；否則，請回傳 false。"
        );
    }

    public String getRequestString() {
        return "EvaluateShortAnswerRequest{" +
                "question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", userAnswer='" + userAnswer + '\'' +
                '}';
    }
}
