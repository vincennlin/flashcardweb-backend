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

    TagDto addTagToFlashcard(Long flashcardId, String tagName);

    EditFlashcardTagsResponse editFlashcardTags(Long flashcardId, EditFlashcardTagsRequest request);

    TagDto createTag(TagDto tagDto);

    TagDto updateTag(Long tagId, TagDto tagDto);

    void deleteTagById(Long tagId);

    void removeTagFromFlashcard(Long flashcardId, String tagName);
}
