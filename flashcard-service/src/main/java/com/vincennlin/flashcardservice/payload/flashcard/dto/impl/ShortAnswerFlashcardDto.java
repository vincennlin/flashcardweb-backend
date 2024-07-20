package com.vincennlin.flashcardservice.payload.flashcard.dto.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vincennlin.flashcardservice.operation.Operation;
import com.vincennlin.flashcardservice.payload.flashcard.dto.AbstractFlashcardDto;
import com.vincennlin.flashcardservice.payload.flashcard.type.FlashcardType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(
        name = "ShortAnswerFlashcardDto",
        description = "問答題的 Data Transfer Object"
)
public class ShortAnswerFlashcardDto extends AbstractFlashcardDto {

    public ShortAnswerFlashcardDto() {
        super(FlashcardType.SHORT_ANSWER);
    }

    @Schema(
            name = "short_answer",
            description = "問答題的答案",
            example = "Java is a programming language."
    )
    @JsonProperty("short_answer")
    @NotEmpty(message = "The value for 'short_answer' cannot be empty")
    private String shortAnswer;

    @Override
    public void execute(Operation operation) {
        operation.apply(this);
    }
}
