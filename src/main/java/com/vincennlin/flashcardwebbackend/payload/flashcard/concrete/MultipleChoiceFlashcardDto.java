package com.vincennlin.flashcardwebbackend.payload.flashcard.concrete;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vincennlin.flashcardwebbackend.payload.flashcard.FlashcardDto;
import com.vincennlin.flashcardwebbackend.operation.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private OptionDto answer;

    @JsonProperty(value = "answer_index")
    @Positive
    private Integer answerIndex;

    @Override
    public void execute(Operation operation) {
        operation.apply(this);
    }
}