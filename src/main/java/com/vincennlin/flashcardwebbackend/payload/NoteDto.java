package com.vincennlin.flashcardwebbackend.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoteDto {

    private Long id;

    @NotEmpty(message = "content cannot be empty")
    private String content;

    @JsonProperty("date_created")
    private LocalDateTime dateCreated;

    @JsonProperty("last_updated")
    private LocalDateTime lastUpdated;
}
