package com.vincennlin.courseservice.payload.note;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateNoteRequest {

    @JsonProperty("content")
    private String content;

    @JsonProperty("summary")
    private String summary;
}
