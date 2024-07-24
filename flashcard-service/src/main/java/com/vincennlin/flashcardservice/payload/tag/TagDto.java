package com.vincennlin.flashcardservice.payload.tag;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vincennlin.flashcardservice.entity.tag.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagDto {

    @JsonProperty(
            value = "id",
            access = JsonProperty.Access.READ_ONLY
    )
    private Long id;

    @NotBlank
    @JsonProperty("tag_name")
    private String tagName;

    @JsonProperty(
            value = "flashcard_count",
            access = JsonProperty.Access.READ_ONLY
    )
    private Integer flashcardCount;

    @JsonIgnore
    private ModelMapper modelMapper = new ModelMapper();

    public Tag mapToEntity() {
        return modelMapper.map(this, Tag.class);
    }
}
