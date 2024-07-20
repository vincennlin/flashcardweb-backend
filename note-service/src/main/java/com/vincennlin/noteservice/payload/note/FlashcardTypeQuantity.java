package com.vincennlin.noteservice.payload.note;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vincennlin.noteservice.payload.flashcard.type.FlashcardType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlashcardTypeQuantity {

    @NotNull
    @JsonProperty(value = "type")
    private FlashcardType type;

    @Min(value = 1, message = "The value for 'quantity' must be greater than 0")
    @Max(value = 3, message = "The value for 'quantity' must be less than or equal to 3")
    @JsonProperty(value = "quantity")
    private Integer quantity;
}
