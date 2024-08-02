package com.vincennlin.flashcardservice.payload.flashcard.dto;

import com.fasterxml.jackson.annotation.*;
import com.vincennlin.flashcardservice.entity.flashcard.Flashcard;
import com.vincennlin.flashcardservice.mapper.FlashcardMapper;
import com.vincennlin.flashcardservice.payload.flashcard.dto.impl.FillInTheBlankFlashcardDto;
import com.vincennlin.flashcardservice.payload.flashcard.dto.impl.MultipleChoiceFlashcardDto;
import com.vincennlin.flashcardservice.payload.flashcard.dto.impl.ShortAnswerFlashcardDto;
import com.vincennlin.flashcardservice.payload.flashcard.dto.impl.TrueFalseFlashcardDto;
import com.vincennlin.flashcardservice.payload.flashcard.type.FlashcardType;
import com.vincennlin.flashcardservice.operation.Operation;
import com.vincennlin.flashcardservice.payload.review.dto.ReviewInfoDto;
import com.vincennlin.flashcardservice.payload.tag.dto.TagDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

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
        name = "FlashcardDto",
        description = "不會用到，只是拿來給其他字卡繼承用的 Data Transfer Object"
)
public abstract class FlashcardDto {

    protected FlashcardDto(FlashcardType type) {
        this.type = type;
        this.reviewInfo = new ReviewInfoDto();
    }

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
            example = "紅黑樹的主要特點是什麼？"
    )
    @NotEmpty(message = "Question cannot be empty")
    private String question;

    @Schema(
            name = "extra_info",
            description = "字卡補充資訊，不一定要有",
            example = "紅黑樹和AVL樹都提供了最好的最壞情況保證，這使得它們有價值於即時應用、計算幾何和持久資料結構等時間敏感的應用。"
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

    @Schema(
            name = "tags",
            description = "字卡標籤",
            example = "[{\"id\":1,\"tag_name\":\"Data Structure\",\"flashcard_count\":1}]"
    )
    private List<TagDto> tags;

    @JsonProperty(
            value = "review_info"
    )
    private ReviewInfoDto reviewInfo;

    @JsonIgnore
    private FlashcardMapper flashcardMapper = new FlashcardMapper();

    public Flashcard mapToEntity() {
        return flashcardMapper.mapToEntity(this);
    }

    public abstract void execute(Operation operation);
}