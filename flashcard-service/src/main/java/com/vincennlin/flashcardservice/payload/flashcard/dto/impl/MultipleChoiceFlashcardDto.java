package com.vincennlin.flashcardservice.payload.flashcard.dto.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vincennlin.flashcardservice.operation.Operation;
import com.vincennlin.flashcardservice.payload.flashcard.dto.AbstractFlashcardDto;
import com.vincennlin.flashcardservice.payload.flashcard.type.FlashcardType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.*;

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
            example = "\"[{\\\"id\\\": 1, \\\"text\\\": \\\"每次操作需要 O(log n) 的時間\\\"}, {\\\"id\\\": 2, \\\"text\\\": \\\"無法保持為以前的版本\\\"}, {\\\"id\\\": 3, \\\"text\\\": \\\"不支持持久資料結構\\\"}, {\\\"id\\\": 4, \\\"text\\\": \\\"只能用於靜態資料結構\\\"}]\"\n"
    )
    @NotEmpty(message = "Options cannot be empty")
    private List<OptionDto> options;


    @Schema(
            name = "answer_option",
            description = "正確選項",
            example = "{\"id\": 1, \"text\": \"每次操作需要 O(log n) 的時間\"}"
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

    @Override
    public void execute(Operation operation) {
        operation.apply(this);
    }
}