package com.vincennlin.flashcardwebbackend.payload.flashcard.concrete;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OptionDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private String text;
}