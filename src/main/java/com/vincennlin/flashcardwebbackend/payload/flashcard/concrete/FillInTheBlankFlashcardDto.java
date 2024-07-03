package com.vincennlin.flashcardwebbackend.payload.flashcard.concrete;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vincennlin.flashcardwebbackend.payload.flashcard.FlashcardDto;
import com.vincennlin.flashcardwebbackend.operation.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(
        name = "FillInTheBlankFlashcardDto",
        description = "填空題的 Data Transfer Object"
)
public class FillInTheBlankFlashcardDto extends FlashcardDto {

    @Schema(
            name = "blank_answers",
            description = "填空答案列表",
            example = "[\"Answer 1\", \"Answer 2\"]"
    )
    @NotEmpty(message = "Blank answers cannot be empty")
    @JsonProperty("blank_answers")
    private List<BlankAnswerDto> blankAnswers;

    @Schema(
            name = "full_answer",
            description = "完整答案",
            example = "This is the full answer with all blanks filled in."
    )
    @NotEmpty(message = "Full answer cannot be empty")
    @JsonProperty("full_answer")
    private String fullAnswer;

    @Override
    public void execute(Operation operation) {
        operation.apply(this);
    }
}
