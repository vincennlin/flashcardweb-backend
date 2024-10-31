package com.vincennlin.courseservice.payload.flashcard.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vincennlin.courseservice.payload.flashcard.FlashcardDto;
import com.vincennlin.courseservice.payload.flashcard.type.FlashcardType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(
        name = "TrueFalseFlashcardDto",
        description = "是非題的 Data Transfer Object"
)
public class TrueFalseFlashcardDto extends FlashcardDto {

    public TrueFalseFlashcardDto() {
        super(FlashcardType.TRUE_FALSE);
    }

    @Schema(
            description = "答案是否為真，ture 或 false 的布林值",
            example = "true"
    )
    @JsonProperty("true_false_answer")
    @NotNull(message = "The value for 'true_false_answer' cannot be null")
    private Boolean trueFalseAnswer;
}