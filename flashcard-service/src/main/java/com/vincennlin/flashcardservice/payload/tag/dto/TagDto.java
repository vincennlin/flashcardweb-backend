package com.vincennlin.flashcardservice.payload.tag.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vincennlin.flashcardservice.entity.tag.Tag;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(
        name = "TagDto",
        description = "標籤 Data Transfer Object"
)
public class TagDto {

    @JsonProperty(
            value = "id",
            access = JsonProperty.Access.READ_ONLY
    )
    private Long id;

    @Schema(
            name = "tag_name",
            description = "標籤名稱",
            example = "資料結構"
    )
    @NotBlank
    @JsonProperty("tag_name")
    private String tagName;

    @Schema(
            name = "flashcard_count",
            description = "該標籤的字卡數量",
            example = "2"
    )
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
