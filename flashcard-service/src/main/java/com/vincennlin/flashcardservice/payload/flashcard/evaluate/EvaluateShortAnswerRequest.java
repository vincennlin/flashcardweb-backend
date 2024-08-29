package com.vincennlin.flashcardservice.payload.flashcard.evaluate;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
