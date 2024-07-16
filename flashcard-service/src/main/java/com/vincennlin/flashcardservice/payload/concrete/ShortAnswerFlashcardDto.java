package com.vincennlin.flashcardservice.payload.concrete;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vincennlin.flashcardservice.operation.Operation;
import com.vincennlin.flashcardservice.payload.FlashcardDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(
        name = "ShortAnswerFlashcardDto",
        description = "問答題的 Data Transfer Object"
)
public class ShortAnswerFlashcardDto extends FlashcardDto {

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
