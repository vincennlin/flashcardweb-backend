package com.vincennlin.noteservice.payload.deck.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateDeckRequest {

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "parent_id")
    private Long parentId;
}
