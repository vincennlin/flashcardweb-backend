package com.vincennlin.noteservice.payload.note;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vincennlin.noteservice.payload.flashcard.type.FlashcardType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenerateFlashcardRequest {

    @NotNull(message = "Type cannot be null")
    @JsonProperty(value = "type")
    private FlashcardType type;

    @Min(value = 1, message = "The value for 'quantity' must be greater than 0")
    @JsonProperty(value = "quantity")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer quantity;
}
