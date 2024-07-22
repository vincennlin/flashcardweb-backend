package com.vincennlin.aiservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vincennlin.aiservice.payload.flashcard.dto.impl.ShortAnswerFlashcardDto;
import com.vincennlin.aiservice.payload.flashcard.type.FlashcardType;
import com.vincennlin.aiservice.payload.note.NoteDto;
import com.vincennlin.aiservice.payload.request.GenerateFlashcardRequest;
import com.vincennlin.aiservice.payload.request.GenerateFlashcardsRequest;
import com.vincennlin.aiservice.payload.request.TypeQuantity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.api.OpenAiApi;
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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
class AiServiceApplicationTests {

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
	public OpenAiChatModel openAiChatModel;

	private final String generateFlashcardUrl = "/api/v1/ai/generate/flashcard";
	private final String generateFlashcardsUrl = "/api/v1/ai/generate/flashcards";

    @Test
	public void generateShortAnswerFlashcard_success() throws Exception {

		FlashcardType type = FlashcardType.SHORT_ANSWER;

		ShortAnswerFlashcardDto flashcardDto = (ShortAnswerFlashcardDto) type.getFlashcardExampleDto();

		mockChatModelSingleFlashcardResponse(true, type);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(generateFlashcardUrl)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(getGenerateFlashcardRequest(type)))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(200))
				.andExpect(jsonPath("$.type").value(FlashcardType.SHORT_ANSWER.name()))
				.andExpect(jsonPath("$.question").value(flashcardDto.getQuestion()))
				.andExpect(jsonPath("$.short_answer").value(flashcardDto.getShortAnswer()));
	}

	@Test
	public void generateMultipleChoiceFlashcard_success() throws Exception {

		FlashcardType type = FlashcardType.MULTIPLE_CHOICE;

		mockChatModelSingleFlashcardResponse(true, type);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(generateFlashcardUrl)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(getGenerateFlashcardRequest(type)))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(200))
				.andExpect(jsonPath("$.type").value(FlashcardType.MULTIPLE_CHOICE.name()))
				.andExpect(jsonPath("$.question").exists())
				.andExpect(jsonPath("$.options").exists())
				.andExpect(jsonPath("$.answer_index").exists());
	}

	@Test
	public void generateFillInTheBlankFlashcard_success() throws Exception {

		FlashcardType type = FlashcardType.FILL_IN_THE_BLANK;

		mockChatModelSingleFlashcardResponse(true, type);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(generateFlashcardUrl)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(getGenerateFlashcardRequest(type)))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(200))
				.andExpect(jsonPath("$.type").value(FlashcardType.FILL_IN_THE_BLANK.name()))
				.andExpect(jsonPath("$.question").exists())
				.andExpect(jsonPath("$.in_blank_answers").exists())
				.andExpect(jsonPath("$.full_answer").exists());
	}

	@Test
	public void generateTrueFalseFlashcard_success() throws Exception {

		FlashcardType type = FlashcardType.TRUE_FALSE;

		mockChatModelSingleFlashcardResponse(true, type);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(generateFlashcardUrl)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(getGenerateFlashcardRequest(type)))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(200))
				.andExpect(jsonPath("$.type").value(FlashcardType.TRUE_FALSE.name()))
				.andExpect(jsonPath("$.question").exists())
				.andExpect(jsonPath("$.true_false_answer").exists());
	}

	@Test
	public void generateFlashcards_success() throws Exception {

		mockChatModelMultipleFlashcardsResponse(true);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(generateFlashcardsUrl)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(getGenerateFlashcardsRequest()))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(200))
				.andExpect(jsonPath("$", hasSize(4)));
	}

	private void mockChatModelSingleFlashcardResponse(Boolean isFormatCorrect, FlashcardType type) throws Exception{
		if (isFormatCorrect) {
			Mockito.when(openAiChatModel.call(any(Prompt.class)))
					.thenReturn(new ChatResponse(new ArrayList<>(List.of(
									new Generation(objectMapper.writeValueAsString(
													type.getFlashcardExampleDto()))))));
		} else {
			Mockito.when(openAiChatModel.call((Prompt) any(Prompt.class)))
					.thenReturn(new ChatResponse(new ArrayList<>(List.of(
									new Generation("invalid json")))));
		}
	}

	private void mockChatModelMultipleFlashcardsResponse(Boolean isFormatCorrect) throws Exception{
		if (isFormatCorrect) {
			Mockito.when(openAiChatModel.call(any(Prompt.class)))
					.thenReturn(new ChatResponse(new ArrayList<>(List.of(
									new Generation(objectMapper.writeValueAsString(
											new ArrayList<>(List.of(
													FlashcardType.SHORT_ANSWER.getFlashcardExampleDto(),
													FlashcardType.MULTIPLE_CHOICE.getFlashcardExampleDto(),
													FlashcardType.FILL_IN_THE_BLANK.getFlashcardExampleDto(),
													FlashcardType.TRUE_FALSE.getFlashcardExampleDto())
											)
									))))));
		} else {
			Mockito.when(openAiChatModel.call((Prompt) any(Prompt.class)))
					.thenReturn(new ChatResponse(new ArrayList<>(List.of(
									new Generation("invalid json")))));
		}
	}

	private GenerateFlashcardRequest getGenerateFlashcardRequest(FlashcardType type) {
		return new GenerateFlashcardRequest("content", type);
	}

	private GenerateFlashcardsRequest getGenerateFlashcardsRequest() {

		List<TypeQuantity> typeQuantities = new ArrayList<>(List.of(
				new TypeQuantity(FlashcardType.SHORT_ANSWER, 1),
				new TypeQuantity(FlashcardType.FILL_IN_THE_BLANK, 1),
				new TypeQuantity(FlashcardType.MULTIPLE_CHOICE, 1),
				new TypeQuantity(FlashcardType.TRUE_FALSE, 1)
		));

		NoteDto note = new NoteDto("content");

		return new GenerateFlashcardsRequest(note, typeQuantities);
	}

	private String getTest1UserJwtToken() {
		return test1UserJwtToken;
	}
}
