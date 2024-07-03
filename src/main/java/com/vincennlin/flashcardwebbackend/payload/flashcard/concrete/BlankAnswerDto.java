package com.vincennlin.flashcardwebbackend.payload.flashcard.concrete;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BlankAnswerDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private String answer;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long flashcardId;
}
