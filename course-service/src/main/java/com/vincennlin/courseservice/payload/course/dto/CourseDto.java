package com.vincennlin.courseservice.payload.course.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "creator_id")
    private Long creatorId;

    @JsonProperty(value = "user_count")
    private Integer userCount;

    @JsonProperty(value = "user_ids")
    private Set<Long> userIds;

    @JsonProperty(value = "flashcard_count")
    private Integer flashcardCount;

    @JsonProperty(value = "flashcard_ids")
    private Set<Long> flashcardIds;
}
