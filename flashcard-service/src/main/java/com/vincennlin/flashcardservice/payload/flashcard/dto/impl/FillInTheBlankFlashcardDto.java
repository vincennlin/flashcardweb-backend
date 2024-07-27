package com.vincennlin.flashcardservice.payload.flashcard.dto.impl;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.vincennlin.flashcardservice.operation.Operation;
import com.vincennlin.flashcardservice.payload.flashcard.dto.FlashcardDto;
import com.vincennlin.flashcardservice.payload.flashcard.type.FlashcardType;
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
public class FillInTheBlankFlashcardDto extends FlashcardDto {

    public FillInTheBlankFlashcardDto() {
        super(FlashcardType.FILL_IN_THE_BLANK);
    }

    @Schema(
            name = "in_blank_answers",
            description = "填空答案列表，儲存 BlankAnswerDto 的陣列",
            example = "[{\"id\": 2, \"text\": \"即時應用\"}, {\"id\": 3, \"text\": \"計算幾何\"}, {\"id\": 4, \"text\": \"持久資料結構\"}]"
    )
    @NotEmpty(message = "In blank answers cannot be empty")
    @JsonProperty("in_blank_answers")
    private List<InBlankAnswerDto> inBlankAnswers;

    @Schema(
            name = "full_answer",
            description = "完整答案",
            example = "紅黑樹和AVL樹都提供了最好的最壞情況保證，這使得它們有價值於即時應用、計算幾何和持久資料結構等時間敏感的應用。"
    )
    @NotBlank(message = "Full answer cannot be blank")
    @JsonProperty("full_answer")
    private String fullAnswer;

    @Override
    public void execute(Operation operation) {
        operation.apply(this);
    }
}
