package com.vincennlin.noteservice.payload.flashcard.dto;

import com.fasterxml.jackson.annotation.*;
import com.vincennlin.noteservice.payload.flashcard.dto.impl.FillInTheBlankFlashcardDto;
import com.vincennlin.noteservice.payload.flashcard.dto.impl.MultipleChoiceFlashcardDto;
import com.vincennlin.noteservice.payload.flashcard.dto.impl.ShortAnswerFlashcardDto;
import com.vincennlin.noteservice.payload.flashcard.dto.impl.TrueFalseFlashcardDto;
import com.vincennlin.noteservice.payload.flashcard.type.FlashcardType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type",
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ShortAnswerFlashcardDto.class, name = "SHORT_ANSWER"),
        @JsonSubTypes.Type(value = FillInTheBlankFlashcardDto.class, name = "FILL_IN_THE_BLANK"),
        @JsonSubTypes.Type(value = MultipleChoiceFlashcardDto.class, name = "MULTIPLE_CHOICE"),
        @JsonSubTypes.Type(value = TrueFalseFlashcardDto.class, name = "TRUE_FALSE")
})
@Schema(
        name = "AbstractFlashcardDto",
        description = "不會用到，只是拿來給其他字卡繼承用的 Data Transfer Object"
)
public abstract class AbstractFlashcardDto {

    protected AbstractFlashcardDto(FlashcardType type) {
        this.type = type;
    }

    @Schema(
            name = "id",
            description = "字卡 id",
            example = "1"
    )
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
    private FlashcardType type;

    @Schema(
            name = "note_id",
            description = "筆記 id",
            example = "1"
    )
    @JsonProperty(value = "note_id")
    private Long noteId;

    @Schema(
            name = "user_id",
            description = "字卡所屬的使用者 id",
            example = "1"
    )
    @JsonProperty(value = "user_id")
    private Long userId;

    @JsonIgnore
    private ModelMapper modelMapper = new ModelMapper();
}