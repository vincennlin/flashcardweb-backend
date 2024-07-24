package com.vincennlin.flashcardservice.service.impl;

import com.vincennlin.flashcardservice.entity.flashcard.AbstractFlashcard;
import com.vincennlin.flashcardservice.entity.tag.Tag;
import com.vincennlin.flashcardservice.exception.ResourceNotFoundException;
import com.vincennlin.flashcardservice.exception.ResourceOwnershipException;
import com.vincennlin.flashcardservice.payload.tag.TagDto;
import com.vincennlin.flashcardservice.repository.TagRepository;
import com.vincennlin.flashcardservice.service.FlashcardService;
import com.vincennlin.flashcardservice.service.TagService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    private final FlashcardService flashcardService;

    private ModelMapper modelMapper;

    @Override
    public List<TagDto> getAllTags() {

        List<Tag> tags = tagRepository.findByUserId(getCurrentUserId());

        return tags.stream().map(
                tag -> modelMapper.map(tag, TagDto.class)).toList();
    }

    @Override
    public List<TagDto> getTagsByFlashcardId(Long flashcardId) {

        List<Tag> tags = tagRepository.findByFlashcardsIdAndUserId(flashcardId, getCurrentUserId());

        List<TagDto> tagDtoList = tags.stream().map(tag ->
                modelMapper.map(tag, TagDto.class)).toList();

        return tagDtoList.stream().peek(tagDto ->
                tagDto.setFlashcardCount(getFlashcardCountByTagId(tagDto.getId()))).toList();
    }

    @Override
    public TagDto getTagById(Long tagId) {

        Tag tag = tagRepository.findById(tagId).orElseThrow(() ->
                new ResourceNotFoundException("Tag", "id", tagId));

        authorizeOwnershipByTagOwnerId(tag.getUserId());

        TagDto tagDto = modelMapper.map(tag, TagDto.class);

        tagDto.setFlashcardCount(getFlashcardCountByTagId(tagId));

        return tagDto;
    }

    @Override
    public Integer getFlashcardCountByTagId(Long tagId) {

        return tagRepository.countFlashcardsByTagId(tagId, getCurrentUserId());
    }

    @Override
    public TagDto createTag(TagDto tagDto) {

        Tag tag = tagRepository.findByTagNameAndUserId(tagDto.getTagName(), getCurrentUserId()).orElse(null);

        if (tag == null) {
            TagDto newTag = mapToDto(createTagAndGetEntity(tagDto.getTagName()));
            newTag.setFlashcardCount(0);
            return newTag;
        } else {
            return mapToDto(tag);
        }
    }

    private Tag createTagAndGetEntity(String tagName) {

        Tag tag = new Tag();

        tag.setTagName(tagName);
        tag.setUserId(getCurrentUserId());

        return tagRepository.save(tag);
    }

    @Override
    public TagDto updateTag(TagDto tagDto) {

        Long tagId = tagDto.getId();

        Tag tag = tagRepository.findById(tagId).orElseThrow(() ->
                new ResourceNotFoundException("Tag", "id", tagId));

        authorizeOwnershipByTagOwnerId(tag.getUserId());

        tag.setTagName(tagDto.getTagName());

        Tag updatedTag = tagRepository.save(tag);

        TagDto updatedTagDto = mapToDto(updatedTag);

        updatedTagDto.setFlashcardCount(getFlashcardCountByTagId(tagId));

        return updatedTagDto;
    }

    @Override
    public void deleteTagById(Long tagId) {

        Tag tag = tagRepository.findById(tagId).orElseThrow(() ->
                new ResourceNotFoundException("Tag", "id", tagId));

        authorizeOwnershipByTagOwnerId(tag.getUserId());

        tagRepository.deleteById(tagId);
    }

    @Override
    public void addTagToFlashcard(Long flashcardId, String tagName) {

        Tag tag = tagRepository.findByTagNameAndUserId(tagName, getCurrentUserId()).orElse(
                createTagAndGetEntity(tagName));

        authorizeOwnershipByTagOwnerId(tag.getUserId());

        List<AbstractFlashcard> flashcards = flashcardService.getFlashcardsEntitiesByTags(List.of(tag));

        AbstractFlashcard flashcard = flashcardService.getFlashcardEntityById(flashcardId);

        flashcards.add(flashcard);

        tag.setFlashcards(flashcards);

        tagRepository.save(tag);
    }

    @Override
    public void removeTagFromFlashcard(Long flashcardId, String tagName) {

        Tag tag = tagRepository.findByTagNameAndUserId(tagName, getCurrentUserId()).orElse(
                createTagAndGetEntity(tagName));

        authorizeOwnershipByTagOwnerId(tag.getUserId());

        List<AbstractFlashcard> flashcards = flashcardService.getFlashcardsEntitiesByTags(List.of(tag));

        AbstractFlashcard flashcard = flashcardService.getFlashcardEntityById(flashcardId);

        flashcards.remove(flashcard);

        tag.setFlashcards(flashcards);

        tagRepository.save(tag);
    }

    private void authorizeOwnershipByTagOwnerId(Long tagOwnerId) {
        if (tagOwnerId != getCurrentUserId() && !containsAuthority("ADVANCED")) {
            throw new ResourceOwnershipException(getCurrentUserId(), "Tag");
        }
    }

    private Long getCurrentUserId() {
        return Long.parseLong(getAuthentication().getPrincipal().toString());
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private Boolean containsAuthority(String authorityName) {
        return getAuthentication().getAuthorities().stream().anyMatch(
                authority -> authority.getAuthority().equals(authorityName));
    }

    private TagDto mapToDto(Tag tag) {
        return modelMapper.map(tag, TagDto.class);
    }
}
