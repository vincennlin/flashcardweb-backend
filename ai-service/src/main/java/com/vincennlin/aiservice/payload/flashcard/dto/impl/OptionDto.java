package com.vincennlin.aiservice.payload.flashcard.dto.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "OptionDto",
        description = "選項的 Data Transfer Object"
)
public class OptionDto {

    private Long id;

    @Schema(
            name = "text",
            description = "選項文字",
            example = "This is an option."
    )
    private String text;
}