package com.vincennlin.flashcardservice.service;

import com.vincennlin.flashcardservice.payload.tag.dto.TagDto;
import com.vincennlin.flashcardservice.payload.tag.request.EditFlashcardTagsRequest;
import com.vincennlin.flashcardservice.payload.tag.response.EditFlashcardTagsResponse;

import java.util.List;

public interface TagService {

    List<TagDto> getAllTags();

    List<TagDto> getTagsByFlashcardId(Long flashcardId);

    TagDto getTagById(Long tagId);

    Integer getFlashcardCountByTagId(Long tagId);

    TagDto createTag(TagDto tagDto);

    TagDto addTagToFlashcard(Long flashcardId, String tagName);

    TagDto updateTag(Long tagId, TagDto tagDto);

    EditFlashcardTagsResponse editFlashcardTags(Long flashcardId, EditFlashcardTagsRequest request);

    void deleteTagById(Long tagId);

    void removeTagFromFlashcard(Long flashcardId, String tagName);
}
