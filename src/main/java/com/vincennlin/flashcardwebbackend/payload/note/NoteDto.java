package com.vincennlin.flashcardwebbackend.payload.note;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vincennlin.flashcardwebbackend.payload.flashcard.FlashcardDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "NoteDto",
        description = "筆記的 Data Transfer Object"
)
public class NoteDto {

    @Schema(
            name = "id",
            description = "筆記 id",
            example = "1"
    )
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Schema(
            name = "content",
            description = "筆記內容",
            example = "{\"content\": \"This is a note about Java\"}"
    )
    @NotEmpty(message = "content cannot be empty")
    private String content;

    @Schema(
            name = "date_created",
            description = "筆記建立日期",
            example = "2024-06-21T18:50:00"
    )
    @JsonProperty(value = "date_created",
            access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime dateCreated;

    @Schema(
            name = "last_updated",
            description = "筆記最後更新日期",
            example = "2024-06-21T18:50:00"
    )
    @JsonProperty(value = "last_updated",
            access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime lastUpdated;

    @Schema(
            name = "flashcards",
            description = "筆記裡的字卡列表",
            example = "null"
    )
    @JsonProperty(
            value = "flashcards",
            access = JsonProperty.Access.READ_ONLY
    )
    private List<? extends FlashcardDto> flashcards;
}
