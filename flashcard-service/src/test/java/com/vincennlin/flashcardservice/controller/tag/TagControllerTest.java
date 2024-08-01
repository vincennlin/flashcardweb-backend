package com.vincennlin.flashcardservice.controller.tag;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vincennlin.flashcardservice.payload.tag.dto.TagDto;
import com.vincennlin.flashcardservice.payload.tag.request.EditFlashcardTagsRequest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
class TagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Value("${admin.jwt.token}")
    private String adminJwtToken;

    @Value("${test1.jwt.token}")
    private String test1UserJwtToken;

    @Value("${test2.jwt.token}")
    private String test2UserJwtToken;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String getAllTagsUrl = "/api/v1/tags";
    private final String getTagsByFlashcardIdUrl = "/api/v1/flashcards/{flashcard_id}/tags";
    private final String getTagByIdUrl = "/api/v1/tags/{tag_id}";

    private final String createTagUrl = "/api/v1/tags";

    private final String updateTagUrl = "/api/v1/tags/{tag_id}";
    private final String addTagToFlashcardUrl = "/api/v1/flashcards/{flashcard_id}/tags";
    private final String editFlashcardTagsUrl = "/api/v1/flashcards/{flashcard_id}/tags/edit";

    private final String deleteTagUrl = "/api/v1/tags/{tag_id}";
    private final String removeTagFromFlashcardUrl = "/api/v1/flashcards/{flashcard_id}/tags";

    @Test
    public void getAllTags_success() throws Exception{

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(getAllTagsUrl)
                .header("Authorization", "Bearer " + getTest1UserJwtToken());

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].tag_name").value("Tag1"))
                .andExpect(jsonPath("$[0].flashcard_count").value(2))
                .andExpect(jsonPath("$[1].tag_name").value("Tag2"))
                .andExpect(jsonPath("$[1].flashcard_count").value(1))
                .andExpect(jsonPath("$[2].tag_name").value("Tag3"))
                .andExpect(jsonPath("$[2].flashcard_count").value(1));
    }

    @Test
    public void getTagsByFlashcardId_success() throws Exception{

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(getTagsByFlashcardIdUrl, 1)
                .header("Authorization", "Bearer " + getTest1UserJwtToken());

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].tag_name").value("Tag1"))
                .andExpect(jsonPath("$[0].flashcard_count").value(2))
                .andExpect(jsonPath("$[1].tag_name").value("Tag2"))
                .andExpect(jsonPath("$[1].flashcard_count").value(1))
                .andExpect(jsonPath("$[2].tag_name").value("Tag3"))
                .andExpect(jsonPath("$[2].flashcard_count").value(1));
    }

    @Test
    public void getTagById_success() throws Exception{

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(getTagByIdUrl, 1)
                .header("Authorization", "Bearer " + getTest1UserJwtToken());

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tag_name").value("Tag1"))
                .andExpect(jsonPath("$.flashcard_count").value(2));
    }

    @Transactional
    @Test
    public void createTag_success() throws Exception{

        TagDto tag = getTagDtoExample();

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(createTagUrl)
                .header("Authorization", "Bearer " + getTest1UserJwtToken())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(tag));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tag_name").value("New Tag"))
                .andExpect(jsonPath("$.flashcard_count").value(0));
    }

    @Transactional
    @Test
    public void updateTag_success() throws Exception{

        TagDto tag = getTagDtoExample();
        tag.setTagName("Updated Tag1");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(updateTagUrl, 1)
                .header("Authorization", "Bearer " + getTest1UserJwtToken())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(tag));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tag_name").value("Updated Tag1"))
                .andExpect(jsonPath("$.flashcard_count").value(2));
    }

    @Transactional
    @Test
    public void addTagToFlashcard_newTag_success() throws Exception{

        TagDto tag = getTagDtoExample();
        tag.setTagName("Tag5");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(addTagToFlashcardUrl, 1)
                .header("Authorization", "Bearer " + getTest1UserJwtToken())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(tag));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tag_name").value("Tag5"))
                .andExpect(jsonPath("$.flashcard_count").value(1));
    }

    @Transactional
    @Test
    public void addTagToFlashcard_existingTag_success() throws Exception{

        TagDto tag = getTagDtoExample();
        tag.setTagName("Tag1");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(addTagToFlashcardUrl, 3)
                .header("Authorization", "Bearer " + getTest1UserJwtToken())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(tag));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tag_name").value("Tag1"))
                .andExpect(jsonPath("$.flashcard_count").value(3));
    }

    @Transactional
    @Test
    public void addTagToFlashcard_existingTag_alreadyAddedToFlashcard() throws Exception{

        TagDto tag = getTagDtoExample();
        tag.setTagName("Tag1");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(addTagToFlashcardUrl, 1)
                .header("Authorization", "Bearer " + getTest1UserJwtToken())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(tag));

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    @Transactional
    @Test
    public void editFlashcardTags_success() throws Exception{

        TagDto tag1 = getTagDtoExample();
        tag1.setTagName("Tag1");

        TagDto tag5 = getTagDtoExample();
        tag5.setTagName("Tag5");

        List<TagDto> tagDtoList = List.of(tag1, tag5);

        EditFlashcardTagsRequest request = new EditFlashcardTagsRequest();
        request.setTags(tagDtoList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(editFlashcardTagsUrl, 1)
                .header("Authorization", "Bearer " + getTest1UserJwtToken())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flashcard_id").value(1))
                .andExpect(jsonPath("$.tags", hasSize(2)))
                .andExpect(jsonPath("$.tags[0].tag_name").value("Tag1"))
                .andExpect(jsonPath("$.tags[1].tag_name").value("Tag5"))
                .andExpect(jsonPath("$.tags[1].flashcard_count").value(1));
    }

    @Transactional
    @Test
    public void deleteTagById_success() throws Exception{

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(deleteTagUrl, 1)
                .header("Authorization", "Bearer " + getTest1UserJwtToken());

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(204));

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(404));
    }

    @Transactional
    @Test
    public void removeTagFromFlashcard() throws Exception{

        TagDto tag = getTagDtoExample();
        tag.setTagName("Tag1");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(removeTagFromFlashcardUrl, 1)
                .header("Authorization", "Bearer " + getTest1UserJwtToken())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(tag));

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(204));

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    private TagDto getTagDtoExample() {
        TagDto tag = new TagDto();
        tag.setTagName("New Tag");
        return tag;
    }

    private String getAdminJwtToken() {
        return adminJwtToken;
    }

    private String getTest1UserJwtToken() {
        return test1UserJwtToken;
    }

    private String getTest2UserJwtToken() {
        return test2UserJwtToken;
    }
}