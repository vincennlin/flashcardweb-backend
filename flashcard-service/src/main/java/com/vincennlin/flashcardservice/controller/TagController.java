package com.vincennlin.flashcardservice.controller;

import com.vincennlin.flashcardservice.payload.tag.TagDto;
import com.vincennlin.flashcardservice.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@Validated
@RequestMapping("/api/v1")
public class TagController {

    private final TagService tagService;

    @PostMapping("/tag")
    public ResponseEntity<TagDto> createTag(@RequestBody TagDto tagDto) {

        TagDto newTag = tagService.createTag(tagDto);

        return new ResponseEntity<>(newTag, HttpStatus.CREATED);
    }
}
