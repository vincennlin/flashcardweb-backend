package com.vincennlin.flashcardservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vincennlin.flashcardservice.client.NoteServiceClient;
import com.vincennlin.flashcardservice.exception.ResourceNotFoundException;
import com.vincennlin.flashcardservice.payload.flashcard.dto.impl.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
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

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
class FlashcardServiceApplicationTests {

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

	@Test
	public void getFlashcardsByNoteId_success() throws Exception{

		mockNoteServiceIsOwnerResponse(true);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/v1/notes/{noteId}/flashcards", 1)
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(200))
				.andExpect(jsonPath("$", hasSize(4)))
				.andExpect(jsonPath("$[0].question").value("Test question 1"))
				.andExpect(jsonPath("$[0].short_answer").value("Test short answer 1"))
				.andExpect(jsonPath("$[0].id").isNumber())
				.andExpect(jsonPath("$[0].user_id").value(2));
	}

	@Test
	public void getFlashcardsByNoteId_noteNotExists() throws Exception{

		mockNoteServiceIsOwnerResponse(null);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/v1/notes/{noteId}/flashcards", 100)
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(404))
		;
	}

	@Test
	public void getFlashcardsByNoteId_tryToAccessOtherUsersNote() throws Exception{

		ResponseEntity<Boolean> mockForbiddenResponse = new ResponseEntity<>(false, HttpStatus.FORBIDDEN);

		Mockito.when(noteServiceClient.isNoteOwner(anyLong(), anyString()))
				.thenReturn(mockForbiddenResponse);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/v1/notes/{noteId}/flashcards", 4)
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(403))
		;
	}

	@Test
	public void getFlashcardById_shortAnswer() throws Exception{

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/v1/flashcards/{flashcardId}", 1)
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(200))
				.andExpect(jsonPath("$.type").value("SHORT_ANSWER"))
				.andExpect(jsonPath("$.question").value("Test question 1"))
				.andExpect(jsonPath("$.short_answer").value("Test short answer 1"))
				.andExpect(jsonPath("$.type").value("SHORT_ANSWER"))
				.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.user_id").value(2));
	}

	@Test
	public void getFlashcardById_fillInTheBlank() throws Exception{

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/v1/flashcards/{flashcardId}", 2)
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(200))
				.andExpect(jsonPath("$.question").value("Test question 2"))
				.andExpect(jsonPath("$.type").value("FILL_IN_THE_BLANK"))
				.andExpect(jsonPath("$.in_blank_answers", hasSize(3)))
				.andExpect(jsonPath("$.in_blank_answers[0].text").value("Blank answer 1"))
				.andExpect(jsonPath("$.full_answer").value("Test fill in the blank answer 1"))
				.andExpect(jsonPath("$.type").value("FILL_IN_THE_BLANK"))
				.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.user_id").value(2));
	}

	@Test
	public void getFlashcardById_multipleChoice() throws Exception{

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/v1/flashcards/{flashcardId}", 3)
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(200))
				.andExpect(jsonPath("$.type").value("MULTIPLE_CHOICE"))
				.andExpect(jsonPath("$.question").value("Test question 3"))
				.andExpect(jsonPath("$.options", hasSize(3)))
				.andExpect(jsonPath("$.options[0].text").value("Test option A"))
				.andExpect(jsonPath("$.options[1].text").value("Test option B"))
				.andExpect(jsonPath("$.options[2].text").value("Test option C"))
				.andExpect(jsonPath("$.answer_option.text").value("Test option A"))
				.andExpect(jsonPath("$.type").value("MULTIPLE_CHOICE"))
				.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.user_id").value(2));
	}

	@Test
	public void getFlashcardById_trueFalse() throws Exception{

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/v1/flashcards/{flashcardId}", 4)
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(200))
				.andExpect(jsonPath("$.type").value("TRUE_FALSE"))
				.andExpect(jsonPath("$.question").value("Test question 4"))
				.andExpect(jsonPath("$.true_false_answer").value("true"))
				.andExpect(jsonPath("$.type").value("TRUE_FALSE"))
				.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.user_id").value(2));
	}

	@Test
	public void getFlashcardById_flashcardNotExists() throws Exception{

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/v1/flashcards/{flashcardId}", 100)
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(404))
		;
	}

	@Test
	public void getFlashcardById_invalidFlashcardId() throws Exception{

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/v1/flashcards/{flashcardId}", "invalid")
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(400))
		;
	}

	@Test
	public void getFlashcardById_flashcardIdNegative() throws Exception{

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/v1/flashcards/{flashcardId}", -1)
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(400))
		;
	}

	@Test
	public void getFlashcardById_tryToAccessOtherUsersFlashcard() throws Exception{

		mockNoteServiceIsOwnerResponse(false);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/v1/flashcards/{flashcardId}", 5)
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(403))
		;
	}

	@Transactional
	@Test
	public void createShortAnswerFlashcard_success() throws Exception{

		mockNoteServiceIsOwnerResponse(true);

		ShortAnswerFlashcardDto shortAnswerFlashcardDto = getShortAnswerFlashcardDtoTemplate();

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/v1/notes/{noteId}/flashcards/short-answer", 1)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(shortAnswerFlashcardDto))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(201))
				.andExpect(jsonPath("$.question").value("New short-answer question"))
				.andExpect(jsonPath("$.short_answer").value("New short-answer answer"))
				.andExpect(jsonPath("$.type").value("SHORT_ANSWER"))
				.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.note_id").value(1));
	}



	@Transactional
	@Test
	public void createShortAnswerFlashcard_noteNotExists() throws Exception{

		mockNoteServiceIsOwnerResponse(null);

		ShortAnswerFlashcardDto shortAnswerFlashcardDto = getShortAnswerFlashcardDtoTemplate();

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/v1/notes/{noteId}/flashcards/short-answer", 100)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(shortAnswerFlashcardDto))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(404));
	}

	@Transactional
	@Test
	public void createShortAnswerFlashcard_invalidArguments() throws Exception{

		ShortAnswerFlashcardDto shortAnswerFlashcardDto = getShortAnswerFlashcardDtoTemplate();
		shortAnswerFlashcardDto.setQuestion("");

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/v1/notes/{noteId}/flashcards/short-answer", 1)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(shortAnswerFlashcardDto))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(400));

		shortAnswerFlashcardDto.setQuestion("New short-answer question");
		shortAnswerFlashcardDto.setShortAnswer("");

		requestBuilder = MockMvcRequestBuilders
				.post("/api/v1/notes/{noteId}/flashcards/short-answer", 1)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(shortAnswerFlashcardDto))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(400));
	}

	@Transactional
	@Test
	public void createShortAnswerFlashcard_tryToAccessOtherUsersNote() throws Exception{

		mockNoteServiceIsOwnerResponse(false);

		ShortAnswerFlashcardDto shortAnswerFlashcardDto = getShortAnswerFlashcardDtoTemplate();

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/v1/notes/{noteId}/flashcards/short-answer", 4)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(shortAnswerFlashcardDto))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(403));
	}

	@Transactional
	@Test
	public void createFillInTheBlankFlashcard_success() throws Exception{

		mockNoteServiceIsOwnerResponse(true);

		FillInTheBlankFlashcardDto fillInTheBlankFlashcardDto = getFillInTheBlankFlashcardDtoTemplate();

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/v1/notes/{noteId}/flashcards/fill-in-the-blank", 1)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(fillInTheBlankFlashcardDto))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(201))
				.andExpect(jsonPath("$.question").value("New fill-in-the-blank question"))
				.andExpect(jsonPath("$.in_blank_answers", hasSize(2)))
				.andExpect(jsonPath("$.in_blank_answers[0].text").value("New blank answer 1"))
				.andExpect(jsonPath("$.in_blank_answers[1].text").value("New blank answer 2"))
				.andExpect(jsonPath("$.full_answer").value("New fill-in-the-blank full answer"))
				.andExpect(jsonPath("$.type").value("FILL_IN_THE_BLANK"))
				.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.note_id").value(1));
	}

	@Transactional
	@Test
	public void createFillInTheBlankFlashcard_invalidArguments() throws Exception {

		FillInTheBlankFlashcardDto fillInTheBlankFlashcardDto = getFillInTheBlankFlashcardDtoTemplate();
		fillInTheBlankFlashcardDto.setQuestion("");

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/v1/notes/{noteId}/flashcards/fill-in-the-blank", 1)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(fillInTheBlankFlashcardDto))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(400));

		fillInTheBlankFlashcardDto.setQuestion("New fill-in-the-blank question");
		fillInTheBlankFlashcardDto.setFullAnswer("");

		requestBuilder = MockMvcRequestBuilders
				.post("/api/v1/notes/{noteId}/flashcards/fill-in-the-blank", 1)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(fillInTheBlankFlashcardDto))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(400));

		fillInTheBlankFlashcardDto.setFullAnswer("New fill-in-the-blank full answer");
		fillInTheBlankFlashcardDto.setInBlankAnswers(List.of());

		requestBuilder = MockMvcRequestBuilders
				.post("/api/v1/notes/{noteId}/flashcards/fill-in-the-blank", 1)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(fillInTheBlankFlashcardDto))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(400));

	}

	@Transactional
	@Test
	public void createMultipleChoiceFlashcard_success() throws Exception{

		mockNoteServiceIsOwnerResponse(true);

		MultipleChoiceFlashcardDto multipleChoiceFlashcardDto = getMultipleChoiceFlashcardDtoTemplate();

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/v1/notes/{noteId}/flashcards/multiple-choice", 1)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(multipleChoiceFlashcardDto))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(201))
				.andExpect(jsonPath("$.question").value("New multiple-choice question"))
				.andExpect(jsonPath("$.options", hasSize(3)))
				.andExpect(jsonPath("$.options[0].text").value("Option A"))
				.andExpect(jsonPath("$.options[1].text").value("Option B"))
				.andExpect(jsonPath("$.options[2].text").value("Option C"))
				.andExpect(jsonPath("$.answer_option.text").value("Option A"))
				.andExpect(jsonPath("$.type").value("MULTIPLE_CHOICE"))
				.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.note_id").value(1));
	}



	@Transactional
	@Test
	public void createMultipleChoiceFlashcard_invalidArguments() throws Exception{

		MultipleChoiceFlashcardDto multipleChoiceFlashcardDto = getMultipleChoiceFlashcardDtoTemplate();
		multipleChoiceFlashcardDto.setAnswerIndex(0);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/v1/notes/{noteId}/flashcards/multiple-choice", 1)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(multipleChoiceFlashcardDto))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(400));

		multipleChoiceFlashcardDto.setAnswerIndex(1);
		multipleChoiceFlashcardDto.setOptions(List.of());

		requestBuilder = MockMvcRequestBuilders
				.post("/api/v1/notes/{noteId}/flashcards/multiple-choice", 1)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(multipleChoiceFlashcardDto))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(400));
	}

	@Transactional
	@Test
	public void createMultipleChoiceFlashcard_noteNotExists() throws Exception{

		mockNoteServiceIsOwnerResponse(null);

		MultipleChoiceFlashcardDto multipleChoiceFlashcardDto = getMultipleChoiceFlashcardDtoTemplate();

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/v1/notes/{noteId}/flashcards/multiple-choice", 100)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(multipleChoiceFlashcardDto))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(404));
	}

	@Transactional
	@Test
	public void createMultipleChoiceFlashcard_tryToAccessOtherUsersNote() throws Exception{

		mockNoteServiceIsOwnerResponse(false);

		MultipleChoiceFlashcardDto multipleChoiceFlashcardDto = getMultipleChoiceFlashcardDtoTemplate();

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/v1/notes/{noteId}/flashcards/multiple-choice", 4)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(multipleChoiceFlashcardDto))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(403));
	}

	@Transactional
	@Test
	public void createMultipleChoiceFlashcard_invalidArguments_answerIndexExceedsOptionsSize() throws Exception{

		mockNoteServiceIsOwnerResponse(true);

		MultipleChoiceFlashcardDto multipleChoiceFlashcardDto = getMultipleChoiceFlashcardDtoTemplate();
		multipleChoiceFlashcardDto.setAnswerIndex(4);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/v1/notes/{noteId}/flashcards/multiple-choice", 1)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(multipleChoiceFlashcardDto))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(400));
	}

	@Transactional
	@Test
	public void createTrueFalseFlashcard_success() throws Exception{

		mockNoteServiceIsOwnerResponse(true);

		TrueFalseFlashcardDto trueFalseFlashcardDto = getTrueFalseFlashcardDtoTemplate();

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/v1/notes/{noteId}/flashcards/true-false", 1)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(trueFalseFlashcardDto))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(201))
				.andExpect(jsonPath("$.question").value("New true-false question"))
				.andExpect(jsonPath("$.true_false_answer").value("true"))
				.andExpect(jsonPath("$.type").value("TRUE_FALSE"))
				.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.note_id").value(1));
	}

	@Transactional
	@Test
	public void createTrueFalseFlashcard_invalidArguments() throws Exception{

		TrueFalseFlashcardDto trueFalseFlashcardDto = getTrueFalseFlashcardDtoTemplate();
		trueFalseFlashcardDto.setQuestion("");

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/v1/notes/{noteId}/flashcards/true-false", 1)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(trueFalseFlashcardDto))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(400));

		trueFalseFlashcardDto.setQuestion("New true-false question");
		trueFalseFlashcardDto.setTrueFalseAnswer(null);

		requestBuilder = MockMvcRequestBuilders
				.post("/api/v1/notes/{noteId}/flashcards/true-false", 1)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(trueFalseFlashcardDto))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(400));
	}

	@Transactional
	@Test
	public void createTrueFalseFlashcard_noteNotExists() throws Exception{

		mockNoteServiceIsOwnerResponse(null);

		TrueFalseFlashcardDto trueFalseFlashcardDto = getTrueFalseFlashcardDtoTemplate();

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/v1/notes/{noteId}/flashcards/true-false", 100)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(trueFalseFlashcardDto))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(404));
	}

	@Transactional
	@Test
	public void createTrueFalseFlashcard_tryToAccessOtherUsersNote() throws Exception{

		mockNoteServiceIsOwnerResponse(false);

		TrueFalseFlashcardDto trueFalseFlashcardDto = getTrueFalseFlashcardDtoTemplate();

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/v1/notes/{noteId}/flashcards/true-false", 4)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(trueFalseFlashcardDto))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(403));
	}

	@Transactional
	@Test
	public void updateShortAnswerFlashcard_success() throws Exception{

		ShortAnswerFlashcardDto shortAnswerFlashcardDto = getShortAnswerFlashcardDtoTemplate();
		shortAnswerFlashcardDto.setQuestion("Updated short-answer question");
		shortAnswerFlashcardDto.setShortAnswer("Updated short-answer answer");

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/api/v1/flashcards/{flashcardId}/short-answer", 1)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(shortAnswerFlashcardDto))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(200))
				.andExpect(jsonPath("$.question").value("Updated short-answer question"))
				.andExpect(jsonPath("$.short_answer").value("Updated short-answer answer"))
				.andExpect(jsonPath("$.type").value("SHORT_ANSWER"))
				.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.note_id").value(1));
	}

	@Transactional
	@Test
	public void updateShortAnswerFlashcard_invalidArguments() throws Exception{

		ShortAnswerFlashcardDto shortAnswerFlashcardDto = getShortAnswerFlashcardDtoTemplate();
		shortAnswerFlashcardDto.setQuestion("");
		shortAnswerFlashcardDto.setShortAnswer("Updated short-answer answer");

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/api/v1/flashcards/{flashcardId}/short-answer", 1)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(shortAnswerFlashcardDto))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(400));

		shortAnswerFlashcardDto.setQuestion("Updated short-answer question");
		shortAnswerFlashcardDto.setShortAnswer("");

		requestBuilder = MockMvcRequestBuilders
				.put("/api/v1/flashcards/{flashcardId}/short-answer", 1)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(shortAnswerFlashcardDto))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(400));
	}

	@Transactional
	@Test
	public void updateShortAnswerFlashcard_flashcardNotExists() throws Exception{

		ShortAnswerFlashcardDto shortAnswerFlashcardDto = getShortAnswerFlashcardDtoTemplate();
		shortAnswerFlashcardDto.setQuestion("Updated short-answer question");
		shortAnswerFlashcardDto.setShortAnswer("Updated short-answer answer");

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/api/v1/flashcards/{flashcardId}/short-answer", 100)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(shortAnswerFlashcardDto))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(404));
	}

	@Transactional
	@Test
	public void updateShortAnswerFlashcard_flashcardTypeMismatch() throws Exception{

		ShortAnswerFlashcardDto shortAnswerFlashcardDto = getShortAnswerFlashcardDtoTemplate();
		shortAnswerFlashcardDto.setQuestion("Updated short-answer question");
		shortAnswerFlashcardDto.setShortAnswer("Updated short-answer answer");

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/api/v1/flashcards/{flashcardId}/short-answer", 2)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(shortAnswerFlashcardDto))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(400));
	}

	@Transactional
	@Test
	public void updateShortAnswerFlashcard_tryToAccessOtherUsersFlashcard() throws Exception{

		mockNoteServiceIsOwnerResponse(false);

		ShortAnswerFlashcardDto shortAnswerFlashcardDto = getShortAnswerFlashcardDtoTemplate();
		shortAnswerFlashcardDto.setQuestion("Updated short-answer question");
		shortAnswerFlashcardDto.setShortAnswer("Updated short-answer answer");

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/api/v1/flashcards/{flashcardId}/short-answer", 5)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(shortAnswerFlashcardDto))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(403));
	}

	@Transactional
	@Test
	public void updateFillInTheBlankFlashcard_success() throws Exception {

		FillInTheBlankFlashcardDto fillInTheBlankFlashcardDto = getFillInTheBlankFlashcardDtoTemplate();
		fillInTheBlankFlashcardDto.setQuestion("Updated fill-in-the-blank question");
		fillInTheBlankFlashcardDto.setFullAnswer("Updated fill-in-the-blank full answer");

		InBlankAnswerDto inBlankAnswerDto1 = new InBlankAnswerDto();
		inBlankAnswerDto1.setText("Updated blank answer 1");

		InBlankAnswerDto inBlankAnswerDto2 = new InBlankAnswerDto();
		inBlankAnswerDto2.setText("Updated blank answer 2");

		InBlankAnswerDto inBlankAnswerDto3 = new InBlankAnswerDto();
		inBlankAnswerDto3.setText("Updated blank answer 3");

		fillInTheBlankFlashcardDto.setInBlankAnswers(List.of(inBlankAnswerDto1, inBlankAnswerDto2, inBlankAnswerDto3));

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/api/v1/flashcards/{flashcardId}/fill-in-the-blank", 2)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(fillInTheBlankFlashcardDto))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(200))
				.andExpect(jsonPath("$.question").value("Updated fill-in-the-blank question"))
				.andExpect(jsonPath("$.in_blank_answers", hasSize(3)))
				.andExpect(jsonPath("$.in_blank_answers[0].text").value("Updated blank answer 1"))
				.andExpect(jsonPath("$.in_blank_answers[1].text").value("Updated blank answer 2"))
				.andExpect(jsonPath("$.in_blank_answers[2].text").value("Updated blank answer 3"))
				.andExpect(jsonPath("$.full_answer").value("Updated fill-in-the-blank full answer"))
				.andExpect(jsonPath("$.type").value("FILL_IN_THE_BLANK"))
				.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.note_id").value(1));
	}

	@Transactional
	@Test
	public void updateFillInTheBlankFlashcard_changeOptionsSize() throws Exception {

		FillInTheBlankFlashcardDto fillInTheBlankFlashcardDto = getFillInTheBlankFlashcardDtoTemplate();

		InBlankAnswerDto inBlankAnswerDto1 = new InBlankAnswerDto();
		inBlankAnswerDto1.setText("Updated blank answer 1");

		fillInTheBlankFlashcardDto.setInBlankAnswers(List.of(inBlankAnswerDto1));

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/api/v1/flashcards/{flashcardId}/fill-in-the-blank", 2)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(fillInTheBlankFlashcardDto))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(400));
	}

	@Transactional
	@Test
	public void updateFillInTheBlankFlashcard_invalidArguments() throws Exception {

		FillInTheBlankFlashcardDto fillInTheBlankFlashcardDto = getFillInTheBlankFlashcardDtoTemplate();
		fillInTheBlankFlashcardDto.setQuestion("");

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/api/v1/flashcards/{flashcardId}/fill-in-the-blank", 2)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(fillInTheBlankFlashcardDto))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(400));

		fillInTheBlankFlashcardDto.setQuestion("Updated fill-in-the-blank question");
		fillInTheBlankFlashcardDto.setFullAnswer("");

		requestBuilder = MockMvcRequestBuilders
				.put("/api/v1/flashcards/{flashcardId}/fill-in-the-blank", 2)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(fillInTheBlankFlashcardDto))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(400));

		fillInTheBlankFlashcardDto.setFullAnswer("Updated fill-in-the-blank full answer");
		fillInTheBlankFlashcardDto.setInBlankAnswers(List.of());

		requestBuilder = MockMvcRequestBuilders
				.put("/api/v1/flashcards/{flashcardId}/fill-in-the-blank", 2)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(fillInTheBlankFlashcardDto))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(400));
	}

	@Transactional
	@Test
	public void updateFillInTheBlankFlashcard_flashcardNotExists() throws Exception{

		FillInTheBlankFlashcardDto fillInTheBlankFlashcardDto = getFillInTheBlankFlashcardDtoTemplate();
		fillInTheBlankFlashcardDto.setQuestion("Updated fill-in-the-blank question");

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/api/v1/flashcards/{flashcardId}/fill-in-the-blank", 100)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(fillInTheBlankFlashcardDto))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(404));
	}

	@Transactional
	@Test
	public void updateFillInTheBlankFlashcard_flashcardTypeMismatch() throws Exception{

		FillInTheBlankFlashcardDto fillInTheBlankFlashcardDto = getFillInTheBlankFlashcardDtoTemplate();
		fillInTheBlankFlashcardDto.setQuestion("Updated fill-in-the-blank question");

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/api/v1/flashcards/{flashcardId}/fill-in-the-blank", 1)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(fillInTheBlankFlashcardDto))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(400));
	}

	@Transactional
	@Test
	public void updateFillInTheBlankFlashcard_tryToAccessOtherUsersFlashcard() throws Exception{

		mockNoteServiceIsOwnerResponse(false);

		FillInTheBlankFlashcardDto fillInTheBlankFlashcardDto = getFillInTheBlankFlashcardDtoTemplate();
		fillInTheBlankFlashcardDto.setQuestion("Updated fill-in-the-blank question");
		fillInTheBlankFlashcardDto.setFullAnswer("Updated fill-in-the-blank full answer");

		InBlankAnswerDto inBlankAnswerDto1 = new InBlankAnswerDto();
		inBlankAnswerDto1.setText("Updated blank answer 1");

		InBlankAnswerDto inBlankAnswerDto2 = new InBlankAnswerDto();
		inBlankAnswerDto2.setText("Updated blank answer 2");

		InBlankAnswerDto inBlankAnswerDto3 = new InBlankAnswerDto();
		inBlankAnswerDto3.setText("Updated blank answer 3");

		fillInTheBlankFlashcardDto.setInBlankAnswers(List.of(inBlankAnswerDto1, inBlankAnswerDto2, inBlankAnswerDto3));

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/api/v1/flashcards/{flashcardId}/fill-in-the-blank", 5)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(fillInTheBlankFlashcardDto))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(403));
	}

	@Transactional
	@Test
	public void updateMultipleChoiceFlashcard_success() throws Exception{

		MultipleChoiceFlashcardDto multipleChoiceFlashcardDto = getMultipleChoiceFlashcardDtoTemplate();
		multipleChoiceFlashcardDto.setQuestion("Updated multiple-choice question");
		multipleChoiceFlashcardDto.setAnswerIndex(1);

		OptionDto optionDto1 = new OptionDto();
		optionDto1.setText("Updated Option A");

		OptionDto optionDto2 = new OptionDto();
		optionDto2.setText("Updated Option B");

		OptionDto optionDto3 = new OptionDto();
		optionDto3.setText("Updated Option C");

		multipleChoiceFlashcardDto.setOptions(List.of(optionDto1, optionDto2, optionDto3));

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/api/v1/flashcards/{flashcardId}/multiple-choice", 3)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(multipleChoiceFlashcardDto))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(200))
				.andExpect(jsonPath("$.question").value("Updated multiple-choice question"))
				.andExpect(jsonPath("$.options", hasSize(3)))
				.andExpect(jsonPath("$.options[0].text").value("Updated Option A"))
				.andExpect(jsonPath("$.options[1].text").value("Updated Option B"))
				.andExpect(jsonPath("$.options[2].text").value("Updated Option C"))
				.andExpect(jsonPath("$.answer_option.text").value("Updated Option A"))
				.andExpect(jsonPath("$.type").value("MULTIPLE_CHOICE"))
				.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.note_id").value(1));
	}

	@Transactional
	@Test
	public void updateMultipleChoiceFlashcard_invalidArguments() throws Exception{

		MultipleChoiceFlashcardDto multipleChoiceFlashcardDto = getMultipleChoiceFlashcardDtoTemplate();
		multipleChoiceFlashcardDto.setAnswerIndex(0);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/api/v1/flashcards/{flashcardId}/multiple-choice", 3)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(multipleChoiceFlashcardDto))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(400));

		multipleChoiceFlashcardDto.setAnswerIndex(1);
		multipleChoiceFlashcardDto.setOptions(List.of());

		requestBuilder = MockMvcRequestBuilders
				.put("/api/v1/flashcards/{flashcardId}/multiple-choice", 3)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(multipleChoiceFlashcardDto))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(400));
	}

	@Transactional
	@Test
	public void updateMultipleChoiceFlashcard_invalidArguments_answerIndexExceedsOptionsSize() throws Exception{

		MultipleChoiceFlashcardDto multipleChoiceFlashcardDto = getMultipleChoiceFlashcardDtoTemplate();
		multipleChoiceFlashcardDto.setAnswerIndex(4);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/api/v1/flashcards/{flashcardId}/multiple-choice", 3)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(multipleChoiceFlashcardDto))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(400));
	}

	@Transactional
	@Test
	public void updateMultipleChoiceFlashcard_flashcardNotExists() throws Exception{

		MultipleChoiceFlashcardDto multipleChoiceFlashcardDto = getMultipleChoiceFlashcardDtoTemplate();
		multipleChoiceFlashcardDto.setQuestion("Updated multiple-choice question");

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/api/v1/flashcards/{flashcardId}/multiple-choice", 100)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(multipleChoiceFlashcardDto))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(404));
	}

	@Transactional
	@Test
	public void updateMultipleChoiceFlashcard_flashcardTypeMismatch() throws Exception{

		MultipleChoiceFlashcardDto multipleChoiceFlashcardDto = getMultipleChoiceFlashcardDtoTemplate();
		multipleChoiceFlashcardDto.setQuestion("Updated multiple-choice question");

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/api/v1/flashcards/{flashcardId}/multiple-choice", 1)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(multipleChoiceFlashcardDto))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(400));
	}

	@Transactional
	@Test
	public void updateMultipleChoiceFlashcard_tryToAccessOtherUsersFlashcard() throws Exception{

		mockNoteServiceIsOwnerResponse(false);

		MultipleChoiceFlashcardDto multipleChoiceFlashcardDto = getMultipleChoiceFlashcardDtoTemplate();
		multipleChoiceFlashcardDto.setQuestion("Updated multiple-choice question");
		multipleChoiceFlashcardDto.setAnswerIndex(1);

		OptionDto optionDto1 = new OptionDto();
		optionDto1.setText("Updated Option A");

		OptionDto optionDto2 = new OptionDto();
		optionDto2.setText("Updated Option B");

		OptionDto optionDto3 = new OptionDto();
		optionDto3.setText("Updated Option C");

		multipleChoiceFlashcardDto.setOptions(List.of(optionDto1, optionDto2, optionDto3));

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/api/v1/flashcards/{flashcardId}/multiple-choice", 5)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(multipleChoiceFlashcardDto))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(403));
	}

	@Transactional
	@Test
	public void updateTrueFalseFlashcard_success() throws Exception{

		TrueFalseFlashcardDto trueFalseFlashcardDto = getTrueFalseFlashcardDtoTemplate();
		trueFalseFlashcardDto.setQuestion("Updated true-false question");
		trueFalseFlashcardDto.setTrueFalseAnswer(false);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/api/v1/flashcards/{flashcardId}/true-false", 4)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(trueFalseFlashcardDto))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(200))
				.andExpect(jsonPath("$.question").value("Updated true-false question"))
				.andExpect(jsonPath("$.true_false_answer").value("false"))
				.andExpect(jsonPath("$.type").value("TRUE_FALSE"))
				.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.note_id").value(1));
	}

	@Transactional
	@Test
	public void updateTrueFalseFlashcard_invalidArguments() throws Exception{

		TrueFalseFlashcardDto trueFalseFlashcardDto = getTrueFalseFlashcardDtoTemplate();
		trueFalseFlashcardDto.setQuestion("");

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/api/v1/flashcards/{flashcardId}/true-false", 4)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(trueFalseFlashcardDto))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(400));

		trueFalseFlashcardDto.setQuestion("Updated true-false question");
		trueFalseFlashcardDto.setTrueFalseAnswer(null);

		requestBuilder = MockMvcRequestBuilders
				.put("/api/v1/flashcards/{flashcardId}/true-false", 4)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(trueFalseFlashcardDto))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(400));
	}

	@Transactional
	@Test
	public void updateTrueFalseFlashcard_flashcardNotExists() throws Exception{

		TrueFalseFlashcardDto trueFalseFlashcardDto = getTrueFalseFlashcardDtoTemplate();
		trueFalseFlashcardDto.setQuestion("Updated true-false question");

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/api/v1/flashcards/{flashcardId}/true-false", 100)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(trueFalseFlashcardDto))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(404));
	}

	@Transactional
	@Test
	public void updateTrueFalseFlashcard_flashcardTypeMismatch() throws Exception{

		TrueFalseFlashcardDto trueFalseFlashcardDto = getTrueFalseFlashcardDtoTemplate();
		trueFalseFlashcardDto.setQuestion("Updated true-false question");

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/api/v1/flashcards/{flashcardId}/true-false", 1)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(trueFalseFlashcardDto))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(400));
	}

	@Transactional
	@Test
	public void updateTrueFalseFlashcard_tryToAccessOtherUsersFlashcard() throws Exception{

		mockNoteServiceIsOwnerResponse(false);

		TrueFalseFlashcardDto trueFalseFlashcardDto = getTrueFalseFlashcardDtoTemplate();
		trueFalseFlashcardDto.setQuestion("Updated true-false question");
		trueFalseFlashcardDto.setTrueFalseAnswer(false);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/api/v1/flashcards/{flashcardId}/true-false", 5)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(trueFalseFlashcardDto))
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(403));
	}

	@Transactional
	@Test
	public void deleteFlashcard_success() throws Exception{

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/api/v1/flashcards/{flashcardId}", 1)
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(204));

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(404));

		requestBuilder = MockMvcRequestBuilders
				.get("/api/v1/flashcards/{flashcardId}", 1)
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(404));
	}

	@Transactional
	@Test
	public void deleteFlashcard_flashcardNotExists() throws Exception{

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/api/v1/flashcards/{flashcardId}", 100)
				.header("Authorization", "Bearer " + getTest1UserJwtToken());

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(404));
	}

	private static ShortAnswerFlashcardDto getShortAnswerFlashcardDtoTemplate() {
		ShortAnswerFlashcardDto shortAnswerFlashcardDto = new ShortAnswerFlashcardDto();
		shortAnswerFlashcardDto.setQuestion("New short-answer question");
		shortAnswerFlashcardDto.setShortAnswer("New short-answer answer");
		return shortAnswerFlashcardDto;
	}

	private static FillInTheBlankFlashcardDto getFillInTheBlankFlashcardDtoTemplate() {
		FillInTheBlankFlashcardDto fillInTheBlankFlashcardDto = new FillInTheBlankFlashcardDto();
		fillInTheBlankFlashcardDto.setQuestion("New fill-in-the-blank question");
		fillInTheBlankFlashcardDto.setFullAnswer("New fill-in-the-blank full answer");

		InBlankAnswerDto inBlankAnswerDto1 = new InBlankAnswerDto();
		inBlankAnswerDto1.setText("New blank answer 1");

		InBlankAnswerDto inBlankAnswerDto2 = new InBlankAnswerDto();
		inBlankAnswerDto2.setText("New blank answer 2");

		fillInTheBlankFlashcardDto.setInBlankAnswers(List.of(inBlankAnswerDto1, inBlankAnswerDto2));
		return fillInTheBlankFlashcardDto;
	}

	private static MultipleChoiceFlashcardDto getMultipleChoiceFlashcardDtoTemplate() {
		MultipleChoiceFlashcardDto multipleChoiceFlashcardDto = new MultipleChoiceFlashcardDto();
		multipleChoiceFlashcardDto.setQuestion("New multiple-choice question");
		multipleChoiceFlashcardDto.setAnswerIndex(1);

		OptionDto optionDto1 = new OptionDto();
		optionDto1.setText("Option A");

		OptionDto optionDto2 = new OptionDto();
		optionDto2.setText("Option B");

		OptionDto optionDto3 = new OptionDto();
		optionDto3.setText("Option C");

		multipleChoiceFlashcardDto.setOptions(List.of(optionDto1, optionDto2, optionDto3));
		return multipleChoiceFlashcardDto;
	}

	private static TrueFalseFlashcardDto getTrueFalseFlashcardDtoTemplate() {
		TrueFalseFlashcardDto trueFalseFlashcardDto = new TrueFalseFlashcardDto();
		trueFalseFlashcardDto.setQuestion("New true-false question");
		trueFalseFlashcardDto.setTrueFalseAnswer(true);
		return trueFalseFlashcardDto;
	}

	private String getAdminJwtToken() throws Exception {
		return adminJwtToken;
	}

	private String getTest1UserJwtToken() throws Exception {
		return test1UserJwtToken;
	}

	private String getTest2UserJwtToken() throws Exception {
		return test2UserJwtToken;
	}

	private void mockNoteServiceIsOwnerResponse(Boolean isOwner) {
		if (isOwner == null) {
			Mockito.when(noteServiceClient.isNoteOwner(anyLong(), anyString()))
					.thenThrow(new ResourceNotFoundException("Note", "id", 100L));
		} else {
			Mockito.when(noteServiceClient.isNoteOwner(anyLong(), anyString()))
					.thenReturn(new ResponseEntity<>(isOwner, HttpStatus.OK));
		}
	}
}
