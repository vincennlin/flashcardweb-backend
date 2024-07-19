package com.vincennlin.aiservice.payload.flashcard.dto.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vincennlin.aiservice.constant.FlashcardType;
import com.vincennlin.aiservice.payload.flashcard.dto.AbstractFlashcardDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(
        name = "FillInTheBlankFlashcardDto",
        description = "填空題的 Data Transfer Object"
)
public class FillInTheBlankFlashcardDto extends AbstractFlashcardDto {

    public FillInTheBlankFlashcardDto() {
        super(FlashcardType.FILL_IN_THE_BLANK);
    }

    @Schema(
            name = "in_blank_answers",
            description = "填空答案列表，儲存 BlankAnswerDto 的陣列",
            example = "[{\"text\":\"answer 1\"}, {\"text\":\"answer 2\"}]"
    )
    @NotEmpty(message = "In blank answers cannot be empty")
    @JsonProperty("in_blank_answers")
    private List<InBlankAnswerDto> inBlankAnswers;

    @Schema(
            name = "full_answer",
            description = "完整答案",
            example = "This is the full answer with all blanks filled in."
    )
    @NotBlank(message = "Full answer cannot be blank")
    @JsonProperty("full_answer")
    private String fullAnswer;

    @Override
    public String toString() {
        return "FillInTheBlankFlashcardDto{" +
                "inBlankAnswers=" + inBlankAnswers +
                ", fullAnswer='" + fullAnswer + '\'' +
                ", question='" + getQuestion() + '\'' +
                ", type=" + getType() +
                ", extraInfo='" + getExtraInfo() + '\'' +
                '}';
    }
}
