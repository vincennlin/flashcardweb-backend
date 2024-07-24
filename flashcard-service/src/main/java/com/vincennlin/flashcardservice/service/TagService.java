package com.vincennlin.flashcardservice.service;

import com.vincennlin.flashcardservice.payload.tag.TagDto;

import java.util.List;

public interface TagService {

    List<TagDto> getAllTags();

    List<TagDto> getTagsByFlashcardId(Long flashcardId);

    TagDto getTagById(Long tagId);

    Integer getFlashcardCountByTagId(Long tagId);

    TagDto createTag(TagDto tagDto);

    TagDto updateTag(TagDto tagDto);

    void deleteTagById(Long tagId);

    void addTagToFlashcard(Long flashcardId, String tagName);

    void removeTagFromFlashcard(Long flashcardId, String tagName);
}
