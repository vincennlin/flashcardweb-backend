package com.vincennlin.noteservice.payload.flashcard.dto.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vincennlin.noteservice.payload.flashcard.dto.AbstractFlashcardDto;
import com.vincennlin.noteservice.payload.flashcard.type.FlashcardType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
        name = "MultipleChoiceFlashcardDto",
        description = "選擇題的 Data Transfer Object"
)
public class MultipleChoiceFlashcardDto extends AbstractFlashcardDto {

    public MultipleChoiceFlashcardDto() {
        super(FlashcardType.MULTIPLE_CHOICE);
    }

    @Schema(
            name = "options",
            description = "選項列表",
            example = "[{\"text\":\"Java is a programming language.\"}, {\"text\":\"Java is a type of coffee.\"}, {\"text\":\"Java is a type of tea.\"}, {\"text\":\"Java is a type of fruit.\"}]"
    )
    @NotEmpty(message = "Options cannot be empty")
    private List<OptionDto> options;


    @Schema(
            name = "answer_option",
            description = "正確選項",
            example = "{\"text\":\"Java is a programming language.\"}"
    )
    @JsonProperty(
            value = "answer_option",
            access = JsonProperty.Access.READ_ONLY
    )
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private OptionDto answerOption;

    @Schema(
            name = "answer_index",
            description = "正確選項在所有選項裡的順序",
            example = "1"
    )
    @JsonProperty(value = "answer_index")
    @Positive
    private Integer answerIndex;
}