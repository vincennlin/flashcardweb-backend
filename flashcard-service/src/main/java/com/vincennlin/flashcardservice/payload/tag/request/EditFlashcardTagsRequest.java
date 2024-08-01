package com.vincennlin.flashcardservice.payload.tag.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vincennlin.flashcardservice.payload.tag.dto.TagDto;
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
public class EditFlashcardTagsRequest {

    @JsonProperty("tags")
    @NotEmpty
    private List<TagDto> tags;
}
