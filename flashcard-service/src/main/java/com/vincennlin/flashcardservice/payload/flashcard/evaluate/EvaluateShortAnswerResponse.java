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
public class EvaluateShortAnswerResponse {

    @JsonProperty(value = "is_correct")
    private boolean isCorrect;

    @JsonProperty(value = "score")
    private Integer score;

    @JsonProperty(value = "feedback")
    private String feedback;
}
