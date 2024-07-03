package com.vincennlin.flashcardwebbackend.payload.flashcard.concrete;

import com.vincennlin.flashcardwebbackend.payload.flashcard.FlashcardDto;
import com.vincennlin.flashcardwebbackend.operation.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(
        name = "ShortAnswerFlashcardDto",
        description = "簡答題的 Data Transfer Object"
)
public class ShortAnswerFlashcardDto extends FlashcardDto {

    @Schema(
            name = "answer",
            description = "答案",
            example = "This is the answer."
    )
    @NotEmpty(message = "Answer cannot be empty")
    private String answer;

    @Override
    public void execute(Operation operation) {
        operation.apply(this);
    }
}
