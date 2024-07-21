package com.vincennlin.aiservice.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vincennlin.aiservice.payload.flashcard.type.FlashcardType;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class TypeQuantity {

    @Schema(
            description = "字卡類型",
            example = "SHORT_ANSWER"
    )
    @NotNull
    @JsonProperty(value = "type")
    private FlashcardType type;

    @Schema(
            description = "該題型要生成的字卡數量",
            example = "1"
    )
    @Min(value = 1, message = "The value for 'quantity' must be greater than 0")
    @Max(value = 3, message = "The value for 'quantity' must be less than or equal to 3")
    @JsonProperty(value = "quantity")
    private Integer quantity;
}
