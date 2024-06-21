package com.vincennlin.flashcardwebbackend.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FlashcardDto {

    private Long id;

    @NotEmpty(message = "Question cannot be empty")
    private String question;

    @NotEmpty(message = "Answer cannot be empty")
    private String answer;

    @JsonProperty("extra_info")
    private String extraInfo;
}
