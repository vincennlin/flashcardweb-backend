package com.vincennlin.flashcardwebbackend.payload.flashcard.concrete;

import com.vincennlin.flashcardwebbackend.payload.flashcard.FlashcardDto;
import com.vincennlin.flashcardwebbackend.operation.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(
        name = "TrueFalseFlashcardDto",
        description = "是非題的 Data Transfer Object"
)
public class TrueFalseFlashcardDto extends FlashcardDto {

    @Schema(
            name = "correct_answer",
            description = "答案是否為真",
            example = "true"
    )
    @NotNull(message = "The value for 'answer' cannot be null")
    private Boolean answer;

    @Override
    public void execute(Operation operation) {
        operation.apply(this);
    }
}