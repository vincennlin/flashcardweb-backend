package com.vincennlin.flashcardwebbackend.payload.flashcard.concrete;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vincennlin.flashcardwebbackend.payload.flashcard.FlashcardDto;
import com.vincennlin.flashcardwebbackend.operation.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(
        name = "MultipleChoiceFlashcardDto",
        description = "選擇題的 Data Transfer Object"
)
public class MultipleChoiceFlashcardDto extends FlashcardDto {

    @Schema(
            name = "options",
            description = "選項列表",
            example = "[\"Option 1\", \"Option 2\", \"Option 3\"]"
    )
    @NotEmpty(message = "Options cannot be empty")
    private List<OptionDto> options;


    @Schema(
            name = "answer",
            description = "正確答案",
            example = "Option 1"
    )
    @JsonProperty(
            access = JsonProperty.Access.READ_ONLY
    )
    private OptionDto answer;

    @JsonProperty(
            value = "answer_index",
            access = JsonProperty.Access.WRITE_ONLY
    )
    @Min(value = 1, message = "Answer index must be greater than or equal to 1")
    private Integer answerIndex;

    @Override
    public void execute(Operation operation) {
        operation.apply(this);
    }
}