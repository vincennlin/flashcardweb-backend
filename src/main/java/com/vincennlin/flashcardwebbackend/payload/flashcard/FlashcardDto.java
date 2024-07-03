package com.vincennlin.flashcardwebbackend.payload.flashcard;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vincennlin.flashcardwebbackend.constant.FlashcardType;
import com.vincennlin.flashcardwebbackend.operation.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "FlashcardDto",
        description = "不會用到，只是拿來給其他字卡繼承用的 Data Transfer Object"
)
public abstract class FlashcardDto {

    @Schema(
            name = "id",
            description = "字卡 id",
            example = "1"
    )
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

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
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private FlashcardType type;

    @Schema(
            name = "note_id",
            description = "筆記 id",
            example = "1"
    )
    @JsonProperty(
            value = "note_id",
            access = JsonProperty.Access.READ_ONLY
    )
    private Long noteId;

    public abstract void execute(Operation operation);
}