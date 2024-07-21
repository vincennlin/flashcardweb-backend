package com.vincennlin.noteservice.payload.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vincennlin.noteservice.payload.note.NoteDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Schema(
        name = "GenerateFlashcardsRequest",
        description = "生成多張字卡的請求"
)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenerateFlashcardsRequest {

    @Schema(hidden = true)
    @JsonProperty(value = "note")
    private NoteDto note;

    @Schema(
            name = "type_quantities",
            description = "要生成的字卡題型與數量列表",
            example = "[{\"type\": \"SHORT_ANSWER\", \"quantity\": 0}, {\"type\": \"FILL_IN_THE_BLANK\", \"quantity\": 1}, {\"type\": \"MULTIPLE_CHOICE\", \"quantity\": 1}, {\"type\": \"TRUE_FALSE\", \"quantity\": 1}]"
    )
    @NotEmpty(message = "Type quantities cannot be empty")
    @JsonProperty(value = "type_quantities")
    private List<TypeQuantity> typeQuantities;
}
