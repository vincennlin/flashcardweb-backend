package com.vincennlin.flashcardwebbackend.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
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
    private Long id;

    @Schema(
            name = "content",
            description = "筆記內容",
            example = "This is a note."
    )
    @NotEmpty(message = "content cannot be empty")
    private String content;

    @Schema(
            name = "date_created",
            description = "筆記建立日期",
            example = "2024-06-21T18:50:00"
    )
    @JsonProperty("date_created")
    private LocalDateTime dateCreated;

    @Schema(
            name = "last_updated",
            description = "筆記最後更新日期",
            example = "2024-06-21T18:50:00"
    )
    @JsonProperty("last_updated")
    private LocalDateTime lastUpdated;

    @Schema(
            name = "flashcards",
            description = "筆記的所有字卡",
            example = "[{\"id\":1,\"question\":\"What is Java?\",\"answer\":\"Java is a programming language.\",\"extra_info\":\"Java is a high-level, class-based, object-oriented programming language that is designed to have as few implementation dependencies as possible.\"},{\"id\":2,\"question\":\"What is Spring Boot?\",\"answer\":\"Spring Boot is a Spring module that provides the RAD (Rapid Application Development) feature to the Spring framework.\",\"extra_info\":\"Spring Boot is a Spring module that provides the RAD (Rapid Application Development) feature to the Spring framework.\"]"
    )
    @JsonProperty("flashcards")
    private List<FlashcardDto> flashcards;
}
