package com.vincennlin.flashcardwebbackend.payload.flashcard;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.vincennlin.flashcardwebbackend.payload.flashcard.concrete.FillInTheBlankFlashcardDto;
import com.vincennlin.flashcardwebbackend.payload.flashcard.concrete.MultipleChoiceFlashcardDto;
import com.vincennlin.flashcardwebbackend.payload.flashcard.concrete.ShortAnswerFlashcardDto;
import com.vincennlin.flashcardwebbackend.payload.flashcard.concrete.TrueFalseFlashcardDto;
import com.vincennlin.flashcardwebbackend.operation.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Schema(
        name = "FlashcardDto",
        description = "字卡的 Data Transfer Object"
)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = FillInTheBlankFlashcardDto.class, name = "FILL_IN_THE_BLANK"),
        @JsonSubTypes.Type(value = MultipleChoiceFlashcardDto.class, name = "MULTIPLE_CHOICE"),
        @JsonSubTypes.Type(value = ShortAnswerFlashcardDto.class, name = "SHORT_ANSWER"),
        @JsonSubTypes.Type(value = TrueFalseFlashcardDto.class, name = "TRUE_FALSE")
})
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
            description = "字卡補充資訊",
            example = "Java is a high-level, class-based, object-oriented programming language that is designed to have as few implementation dependencies as possible."
    )
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("extra_info")
    private String extraInfo;

    public abstract void execute(Operation operation);

//    @Schema(
//            name = "type",
//            description = "字卡類型"
//    )
//    private FlashcardType type;
}