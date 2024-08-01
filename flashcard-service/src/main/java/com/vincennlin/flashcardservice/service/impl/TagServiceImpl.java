package com.vincennlin.flashcardservice.service.impl;

import com.vincennlin.flashcardservice.entity.flashcard.Flashcard;
import com.vincennlin.flashcardservice.entity.tag.Tag;
import com.vincennlin.flashcardservice.exception.ResourceNotFoundException;
import com.vincennlin.flashcardservice.exception.ResourceOwnershipException;
import com.vincennlin.flashcardservice.exception.WebAPIException;
import com.vincennlin.flashcardservice.payload.tag.dto.TagDto;
import com.vincennlin.flashcardservice.payload.tag.request.EditFlashcardTagsRequest;
import com.vincennlin.flashcardservice.payload.tag.response.EditFlashcardTagsResponse;
import com.vincennlin.flashcardservice.repository.FlashcardRepository;
import com.vincennlin.flashcardservice.repository.TagRepository;
import com.vincennlin.flashcardservice.service.TagService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class TagServiceImpl implements TagService {

    private final FlashcardRepository flashcardRepository;
    private final TagRepository tagRepository;

    private ModelMapper modelMapper;

    @Override
    public List<TagDto> getAllTags() {

        List<Tag> tags = tagRepository.findByUserId(getCurrentUserId());

        return tags.stream().map(this::mapToDto).toList();
    }

    @Override
    public List<TagDto> getTagsByFlashcardId(Long flashcardId) {

        List<Tag> tags = tagRepository.findByFlashcardsIdAndUserId(flashcardId, getCurrentUserId());

        return tags.stream().map(this::mapToDto).toList();
    }

    @Override
    public TagDto getTagById(Long tagId) {

        Tag tag = tagRepository.findById(tagId).orElseThrow(() ->
                new ResourceNotFoundException("Tag", "id", tagId.toString()));

        authorizeOwnershipByTagOwnerId(tag.getUserId());

        return mapToDto(tag);
    }

    @Override
    public Integer getFlashcardCountByTagId(Long tagId) {

        return tagRepository.countFlashcardsByTagId(tagId, getCurrentUserId());
    }

    @Override
    public TagDto createTag(TagDto tagDto) {

        Tag tag = tagRepository.findByTagNameAndUserId(tagDto.getTagName(), getCurrentUserId()).orElse(null);

        if (tag != null) {
            throw new WebAPIException(HttpStatus.BAD_REQUEST, "Tag already exists");
        }

        Tag createdTag = createTagAndGetEntity(tagDto.getTagName());

        return mapToDto(createdTag);
    }

    @Transactional
    @Override
    public TagDto addTagToFlashcard(Long flashcardId, String tagName) {

        Tag tag = tagRepository.findByTagNameAndUserId(tagName, getCurrentUserId()).orElse(null);

        if (tag == null) {
            tag = createTagAndGetEntity(tagName);
        }

        Flashcard flashcard = getFlashcardEntityById(flashcardId);

        if (flashcard.getTags().contains(tag)) {
            throw new WebAPIException(HttpStatus.BAD_REQUEST, "Tag already exists on the flashcard.");
        }

        flashcard.getTags().add(tag);
        tag.getFlashcards().add(flashcard);

        flashcardRepository.save(flashcard);
        Tag savedTag = tagRepository.save(tag);

        return mapToDto(savedTag);
    }

    @Override
    public TagDto updateTag(Long tagId, TagDto tagDto) {

        Tag tag = tagRepository.findById(tagId).orElseThrow(() ->
                new ResourceNotFoundException("Tag", "id", tagId.toString()));

        authorizeOwnershipByTagOwnerId(tag.getUserId());

        Tag queriedTag = tagRepository.findByTagNameAndUserId(tagDto.getTagName(), getCurrentUserId()).orElse(null);

        if (queriedTag != null) {
            throw new WebAPIException(HttpStatus.BAD_REQUEST, "Tag already exists");
        }

        tag.setTagName(tagDto.getTagName());

        Tag updatedTag = tagRepository.save(tag);

        return mapToDto(updatedTag);
    }

    @Override
    public EditFlashcardTagsResponse editFlashcardTags(Long flashcardId, EditFlashcardTagsRequest request) {

        Flashcard flashcard = getFlashcardEntityById(flashcardId);
        flashcard.getTags().clear();

        List<TagDto> tagDtoList = request.getTags();

        List<Tag> tags = tagDtoList.stream().map(tagDto -> {
            Tag tag = tagRepository.findByTagNameAndUserId(tagDto.getTagName(), getCurrentUserId()).orElse(null);

            if (tag == null) {
                tag = createTagAndGetEntity(tagDto.getTagName());
            }

            flashcard.getTags().add(tag);
            tag.getFlashcards().add(flashcard);

            return tag;
        }).toList();

        flashcardRepository.save(flashcard);
        List<Tag> savedTags = tagRepository.saveAll(tags);

        List<TagDto> responseTagDtoList = savedTags.stream().map(this::mapToDto).toList();

        EditFlashcardTagsResponse response = new EditFlashcardTagsResponse();
        response.setFlashcardId(flashcardId);
        response.setTags(responseTagDtoList);

        return response;
    }

    @Override
    public void deleteTagById(Long tagId) {

        Tag tag = tagRepository.findById(tagId).orElseThrow(() ->
                new ResourceNotFoundException("Tag", "id", tagId.toString()));

        authorizeOwnershipByTagOwnerId(tag.getUserId());

        List<Flashcard> flashcards = flashcardRepository.findByTags(List.of(tag));

        flashcards.forEach(flashcard -> flashcard.getTags().remove(tag));

        flashcardRepository.saveAll(flashcards);

        tagRepository.deleteById(tagId);
    }

    @Transactional
    @Override
    public void removeTagFromFlashcard(Long flashcardId, String tagName) {

        Tag tag = tagRepository.findByTagNameAndUserId(tagName, getCurrentUserId()).orElseThrow(
                () -> new ResourceNotFoundException("Tag", "tagName", tagName));

        Flashcard flashcard = getFlashcardEntityById(flashcardId);

        if (!flashcard.getTags().contains(tag)) {
            throw new WebAPIException(HttpStatus.BAD_REQUEST, "Tag does not exist on the flashcard");
        }

        flashcard.getTags().remove(tag);
        tag.getFlashcards().remove(flashcard);

        flashcardRepository.save(flashcard);
        tagRepository.save(tag);
    }

    private Tag createTagAndGetEntity(String tagName) {

        Tag tag = new Tag();

        tag.setTagName(tagName);
        tag.setUserId(getCurrentUserId());

        return tagRepository.save(tag);
    }

    private Flashcard getFlashcardEntityById(Long flashcardId) {
        Flashcard flashcard =  flashcardRepository.findById(flashcardId).orElseThrow(() ->
                new ResourceNotFoundException("Flashcard", "id", flashcardId.toString()));
        authorizeOwnershipByFlashcardOwnerId(flashcard.getUserId());
        return flashcard;
    }

    private void authorizeOwnershipByTagOwnerId(Long tagOwnerId) {
        if (tagOwnerId != getCurrentUserId() && !containsAuthority("ADVANCED")) {
            throw new ResourceOwnershipException(getCurrentUserId(), "Tag");
        }
    }

    private void authorizeOwnershipByFlashcardOwnerId(Long flashcardOwnerId) {
        if (flashcardOwnerId != getCurrentUserId() && !containsAuthority("ADVANCED")) {
            throw new ResourceOwnershipException(getCurrentUserId(), "Flashcard");
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
        TagDto tagDto = modelMapper.map(tag, TagDto.class);
        tagDto.setFlashcardCount(getFlashcardCountByTagId(tagDto.getId()));
        return tagDto;
    }
}
