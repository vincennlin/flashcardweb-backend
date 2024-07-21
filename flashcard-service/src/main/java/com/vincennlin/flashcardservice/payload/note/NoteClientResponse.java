package com.vincennlin.flashcardservice.payload.note;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoteClientResponse {

    @Schema(
            name = "id",
            description = "筆記 id",
            example = "1"
    )
    Long id;

    @Schema(
            name = "content",
            description = "筆記內容",
            example = "{\"content\": \"This is a note about Java\"}"
    )
    String content;

    @Schema(
            name = "user_id",
            description = "筆記所屬的使用者 id",
            example = "1"
    )
    @JsonProperty(
            value = "user_id"
    )
    Long userId;

    @Schema(
            name = "date_created",
            description = "筆記建立日期",
            example = "2024-06-21T18:50:00"
    )
    @JsonProperty(
            value = "date_created"
    )
    LocalDateTime dateCreated;

    @Schema(
            name = "last_updated",
            description = "筆記最後更新日期",
            example = "2024-06-21T18:50:00"
    )
    @JsonProperty(
            value = "last_updated"
    )
    LocalDateTime lastUpdated;
}
