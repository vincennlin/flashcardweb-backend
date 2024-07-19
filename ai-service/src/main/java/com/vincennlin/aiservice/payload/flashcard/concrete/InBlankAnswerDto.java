package com.vincennlin.aiservice.payload.flashcard.concrete;

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
            name = "text",
            description = "空格答案",
            example = "an answer"
    )
    private String text;

    @Override
    public String toString() {
        return "InBlankAnswerDto{" +
                "text='" + text + '\'' +
                '}';
    }
}
