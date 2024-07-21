package com.vincennlin.noteservice.payload.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vincennlin.noteservice.payload.flashcard.type.FlashcardType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenerateFlashcardRequest {

    @Schema(
            name = "type",
            description = "字卡題型",
            example = "SHORT_ANSWER"
    )
    @NotNull(message = "Type cannot be null")
    @JsonProperty(value = "type")
    private FlashcardType type;
}
