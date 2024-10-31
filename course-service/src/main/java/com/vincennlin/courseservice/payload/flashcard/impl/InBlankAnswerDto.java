package com.vincennlin.courseservice.payload.flashcard.impl;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "InBlankAnswerDto",
        description = "填空題答案的 Data Transfer Object"
)
public class InBlankAnswerDto {

    @Schema(
            name = "id",
            description = "填空答案的 id",
            example = "1"

    )
    private Long id;

    @Schema(
            name = "text",
            description = "空格答案",
            example = "2-3-4樹"
    )
    private String text;
}
