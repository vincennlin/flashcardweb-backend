package com.vincennlin.flashcardservice.controller.review;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vincennlin.flashcardservice.client.NoteServiceClient;
import com.vincennlin.flashcardservice.exception.ResourceNotFoundException;
import com.vincennlin.flashcardservice.mapper.FlashcardMapper;
import com.vincennlin.flashcardservice.payload.flashcard.dto.impl.ShortAnswerFlashcardDto;
import com.vincennlin.flashcardservice.payload.review.option.ReviewOption;
import com.vincennlin.flashcardservice.payload.review.request.ReviewRequest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Value("${admin.jwt.token}")
    private String adminJwtToken;

    @Value("${test1.jwt.token}")
    private String test1UserJwtToken;

    @Value("${test2.jwt.token}")
    private String test2UserJwtToken;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private NoteServiceClient noteServiceClient;

    @Autowired
    private FlashcardMapper flashcardMapper;

    private final String getFlashcardsToReviewUrl = "/api/v1/flashcards/review";
    private final String getReviewHistoryByFlashcardIdUrl = "/api/v1/flashcard/{flashcard_id}/review-history";

    private final String reviewUrl = "/api/v1/flashcard/{flashcard_id}/review";

    private final String undoReviewUrl = "/api/v1/flashcard/{flashcard_id}/undo-review";

    @Test
    public void getReviewInfo_success() throws Exception {

        String getFlashcardsByIdUrl = "/api/v1/flashcards/{flashcardId}";

        mockNoteServiceIsOwnerResponse(true);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(getFlashcardsByIdUrl, 1)
                .header("Authorization", "Bearer " + getTest1UserJwtToken());

        var result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.review_info.review_level").value(0))
                .andExpect(jsonPath("$.review_info.review_interval").value(1))
                .andExpect(jsonPath("$.review_info.last_reviewed").hasJsonPath())
                .andExpect(jsonPath("$.review_info.next_review").exists())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Transactional
    @Test
    public void getFlashcardsToReview_success() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(getFlashcardsToReviewUrl)
                .header("Authorization", "Bearer " + getTest1UserJwtToken());

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }

    @Disabled
    @Transactional
    @Test
    public void getReviewHistoryByFlashcardId_success() throws Exception {

        ReviewRequest reviewRequest = new ReviewRequest();
        reviewRequest.setReviewOption(ReviewOption.HARD);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(reviewUrl, 1)
                .header("Authorization", "Bearer " + getTest1UserJwtToken())
                .content(objectMapper.writeValueAsString(reviewRequest))
                .contentType("application/json");

        mockMvc.perform(requestBuilder);
        mockMvc.perform(requestBuilder);
        mockMvc.perform(requestBuilder);

        requestBuilder = MockMvcRequestBuilders
                .get(getReviewHistoryByFlashcardIdUrl, 1)
                .header("Authorization", "Bearer " + getTest1UserJwtToken());

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Disabled
    @Transactional
    @Test
    public void review_success() throws Exception {

        ReviewRequest reviewRequest = new ReviewRequest();
        reviewRequest.setReviewOption(ReviewOption.HARD);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(reviewUrl, 1)
                .header("Authorization", "Bearer " + getTest1UserJwtToken())
                .content(objectMapper.writeValueAsString(reviewRequest))
                .contentType("application/json");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.review_info.review_level").value(3))
                .andExpect(jsonPath("$.review_info.review_interval").value(1));
    }

    @Disabled
    @Transactional
    @Test
    public void undoReview_success() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(undoReviewUrl, 1)
                .header("Authorization", "Bearer " + getTest1UserJwtToken());

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.review_info.review_level").value(2));
    }

    private static ShortAnswerFlashcardDto getShortAnswerFlashcardDtoTemplate() {
        ShortAnswerFlashcardDto shortAnswerFlashcardDto = new ShortAnswerFlashcardDto();
        shortAnswerFlashcardDto.setQuestion("New short-answer question");
        shortAnswerFlashcardDto.setShortAnswer("New short-answer answer");
        return shortAnswerFlashcardDto;
    }

    private void mockNoteServiceIsOwnerResponse(Boolean isOwner) {
        if (isOwner == null) {
            Mockito.when(noteServiceClient.isNoteOwner(anyLong(), anyString()))
                    .thenThrow(new ResourceNotFoundException("Note", "id", "100"));
        } else {
            Mockito.when(noteServiceClient.isNoteOwner(anyLong(), anyString()))
                    .thenReturn(new ResponseEntity<>(isOwner, HttpStatus.OK));
        }
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