package com.vincennlin.aiservice.payload.flashcard.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vincennlin.aiservice.payload.flashcard.type.FlashcardType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
        name = "AbstractFlashcardDto"
)
public abstract class AbstractFlashcardDto {

    protected AbstractFlashcardDto(FlashcardType type) {
        this.type = type;
    }

    @Schema(
            name = "question",
            description = "字卡問題",
            example = "What is Java?"
    )
    @NotEmpty(message = "Question cannot be empty")
    private String question;

    @Schema(
            name = "extra_info",
            description = "字卡補充資訊，不一定要有",
            example = "Java is a high-level, class-based, object-oriented programming language that is designed to have as few implementation dependencies as possible."
    )
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("extra_info")
    private String extraInfo;

    @Schema(
            name = "type",
            description = "字卡類型，共有四種：SHORT_ANSWER, FILL_IN_THE_BLANK, MULTIPLE_CHOICE, TRUE_FALSE",
            example = "SHORT_ANSWER"
    )
    private FlashcardType type;
}