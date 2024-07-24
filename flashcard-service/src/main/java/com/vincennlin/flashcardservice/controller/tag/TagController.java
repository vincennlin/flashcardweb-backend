package com.vincennlin.flashcardservice.controller.tag;

import com.vincennlin.flashcardservice.payload.tag.TagDto;
import com.vincennlin.flashcardservice.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@Validated
@RequestMapping("/api/v1")
public class TagController implements TagControllerSwagger{

    private final TagService tagService;

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/tags")
    public ResponseEntity<List<TagDto>> getAllTags() {

        List<TagDto> tags = tagService.getAllTags();

        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/flashcard/{flashcard_id}/tags")
    public ResponseEntity<List<TagDto>> getTagsByFlashcardId(@PathVariable(name = "flashcard_id") Long flashcardId) {

        List<TagDto> tags = tagService.getTagsByFlashcardId(flashcardId);

        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/tag/{tag_id}")
    public ResponseEntity<TagDto> getTagById(@PathVariable(name = "tag_id") Long tagId) {

        TagDto tag = tagService.getTagById(tagId);

        return new ResponseEntity<>(tag, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('CREATE')")
    @PostMapping("/flashcard/{flashcard_id}/tag")
    public ResponseEntity<TagDto> addTagToFlashcard(@PathVariable(name = "flashcard_id") Long flashcardId,
                                                    @RequestBody TagDto tagDto) {

        TagDto addedTag = tagService.addTagToFlashcard(flashcardId, tagDto.getTagName());

        return new ResponseEntity<>(addedTag, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('CREATE')")
    @PostMapping("/tag")
    public ResponseEntity<TagDto> createTag(@RequestBody TagDto tagDto) {

        TagDto newTag = tagService.createTag(tagDto);

        return new ResponseEntity<>(newTag, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('UPDATE')")
    @PutMapping("/tag/{tag_id}")
    public ResponseEntity<TagDto> updateTag(@PathVariable(name = "tag_id") Long tagId,
                                           @RequestBody TagDto tagDto) {

        TagDto updatedTag = tagService.updateTag(tagId, tagDto);

        return new ResponseEntity<>(updatedTag, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DELETE')")
    @DeleteMapping("/tag/{tag_id}")
    public ResponseEntity<Void> deleteTagById(@PathVariable(name = "tag_id") Long tagId) {

        tagService.deleteTagById(tagId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAuthority('DELETE')")
    @DeleteMapping("/flashcard/{flashcard_id}/tag")
    public ResponseEntity<Void> removeTagFromFlashcard(@PathVariable(name = "flashcard_id") Long flashcardId,
                                                      @RequestBody TagDto tagDto) {

        tagService.removeTagFromFlashcard(flashcardId, tagDto.getTagName());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
