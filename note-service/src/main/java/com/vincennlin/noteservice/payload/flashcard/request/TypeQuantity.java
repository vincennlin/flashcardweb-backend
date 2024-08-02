package com.vincennlin.noteservice.payload.flashcard.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vincennlin.noteservice.payload.flashcard.type.FlashcardType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(
        name = "TypeQuantity",
        description = "要生成的字卡題型與數量"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TypeQuantity {

    @Schema(
            name = "type",
            description = "字卡題型",
            example = "SHORT_ANSWER"
    )
    @NotNull
    @JsonProperty(value = "type")
    private FlashcardType type;

    @Schema(
            name = "quantity",
            description = "要生成的字卡數量",
            example = "1"
    )
    @Min(value = 0, message = "The value for 'quantity' must be greater than or equal to 0")
    @Max(value = 5, message = "The value for 'quantity' must be less than or equal to 5")
    @JsonProperty(value = "quantity")
    private Integer quantity;
}
