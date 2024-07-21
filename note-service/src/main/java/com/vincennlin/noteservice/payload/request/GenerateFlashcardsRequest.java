package com.vincennlin.noteservice.payload.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vincennlin.noteservice.payload.note.NoteDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenerateFlashcardsRequest {

    @JsonProperty(value = "note")
    private NoteDto note;

    @NotEmpty(message = "Type quantities cannot be empty")
    @JsonProperty(value = "type_quantities")
    private List<TypeQuantity> typeQuantities;
}
