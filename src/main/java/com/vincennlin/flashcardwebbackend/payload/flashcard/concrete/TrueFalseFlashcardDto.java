package com.vincennlin.flashcardwebbackend.payload.flashcard.concrete;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vincennlin.flashcardwebbackend.payload.flashcard.FlashcardDto;
import com.vincennlin.flashcardwebbackend.operation.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(
        name = "TrueFalseFlashcardDto",
        description = "是非題的 Data Transfer Object"
)
public class TrueFalseFlashcardDto extends FlashcardDto {

    @Schema(
            description = "答案是否為真，ture 或 false 的布林值",
            example = "true"
    )
    @JsonProperty("true_false_answer")
    @NotNull(message = "The value for 'true_false_answer' cannot be null")
    private Boolean trueFalseAnswer;

    @Override
    public void execute(Operation operation) {
        operation.apply(this);
    }
}