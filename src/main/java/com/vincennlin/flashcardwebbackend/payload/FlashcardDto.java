package com.vincennlin.flashcardwebbackend.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Schema(
        name = "FlashcardDto",
        description = "字卡的 Data Transfer Object"

)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FlashcardDto {

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
            name = "answer",
            description = "字卡答案",
            example = "Java is a programming language."
    )
    @NotEmpty(message = "Answer cannot be empty")
    private String answer;

    @Schema(
            name = "extra_info",
            description = "字卡補充資訊",
            example = "Java is a high-level, class-based, object-oriented programming language that is designed to have as few implementation dependencies as possible."
    )
    @JsonProperty("extra_info")
    private String extraInfo;
}
