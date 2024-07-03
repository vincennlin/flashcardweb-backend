package com.vincennlin.flashcardwebbackend.payload.flashcard.concrete;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlankAnswerDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private String answer;
}
