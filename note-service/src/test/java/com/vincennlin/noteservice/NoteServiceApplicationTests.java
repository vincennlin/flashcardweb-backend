package com.vincennlin.noteservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vincennlin.noteservice.client.AiServiceClient;
import com.vincennlin.noteservice.client.FlashcardServiceClient;
import com.vincennlin.noteservice.payload.flashcard.dto.impl.FillInTheBlankFlashcardDto;
import com.vincennlin.noteservice.payload.flashcard.dto.impl.MultipleChoiceFlashcardDto;
import com.vincennlin.noteservice.payload.flashcard.type.FlashcardType;
import com.vincennlin.noteservice.payload.note.dto.NoteDto;
import com.vincennlin.noteservice.payload.flashcard.dto.FlashcardDto;
import com.vincennlin.noteservice.payload.flashcard.dto.impl.ShortAnswerFlashcardDto;
import com.vincennlin.noteservice.payload.flashcard.dto.impl.TrueFalseFlashcardDto;
import com.vincennlin.noteservice.payload.request.GenerateFlashcardRequest;
import com.vincennlin.noteservice.payload.request.GenerateFlashcardsRequest;
import com.vincennlin.noteservice.payload.request.TypeQuantity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
class NoteServiceApplicationTests {

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
	private FlashcardServiceClient flashcardServiceClient;

	@MockBean
	private AiServiceClient aiServiceClient;

	private final String getNotesUrl = "/api/v1/notes";
	private final String getNoteByIdUrl = "/api/v1/notes/{id}";
	private final String getNoteByUserIdUrl = "/api/v1/notes/user/{userId}";

	private final String createNoteUrl = "/api/v1/notes";

	private final String updateNoteUrl = "/api/v1/notes/{id}";

	private final String deleteNoteUrl = "/api/v1/notes/{id}";

	private final String generateFlashcardUrl = "/api/v1/notes/{noteId}/generate/flashcards";
	private final String generateFlashcardsUrl = "/api/v1/notes/{noteId}/generate/flashcards/bulk";

	@BeforeEach
	public void beforeEach() {
		mockFlashcardServiceClient();
	}

	@Test
	public void getAllNotes_admin() throws Exception{

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(getNotesUrl)
				.header("Authorization", "Bearer " + adminJwtToken);

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(200))
				.andExpect(jsonPath("$.totalElements", equalTo(5)))
				.andExpect(jsonPath("$.content[0].content", equalTo("Test note 1")))
				.andExpect(jsonPath("$.content[0].id", notNullValue()))
				.andExpect(jsonPath("$.content[0].date_created", notNullValue()))
				.andExpect(jsonPath("$.content[0].last_updated", notNullValue()))
				.andExpect(jsonPath("$.content[0].flashcards", notNullValue()));
	}

	@Test
	public void getAllNotes_user() throws Exception{

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(getNotesUrl)
				.header("Authorization", "Bearer " + test1UserJwtToken);

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(200))
				.andExpect(jsonPath("$.totalElements", equalTo(3)))
				.andExpect(jsonPath("$.content[0].content", equalTo("Test note 1")))
				.andExpect(jsonPath("$.content[0].id", notNullValue()))
				.andExpect(jsonPath("$.content[0].date_created", notNullValue()))
				.andExpect(jsonPath("$.content[0].last_updated", notNullValue()))
				.andExpect(jsonPath("$.content[0].flashcards", notNullValue()))
				.andExpect(jsonPath("$.content[0].user_id", equalTo(2)));

		requestBuilder = MockMvcRequestBuilders
				.get(getNotesUrl)
				.header("Authorization", "Bearer " + test2UserJwtToken);

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(200))
				.andExpect(jsonPath("$.totalElements", equalTo(2)))
				.andExpect(jsonPath("$.content[0].content", equalTo("Test note 4")))
				.andExpect(jsonPath("$.content[0].id", notNullValue()))
				.andExpect(jsonPath("$.content[0].date_created", notNullValue()))
				.andExpect(jsonPath("$.content[0].last_updated", notNullValue()))
				.andExpect(jsonPath("$.content[0].flashcards", notNullValue()))
				.andExpect(jsonPath("$.content[0].user_id", equalTo(3)));
	}

	@Test
	public void getNoteByUserId_success() throws Exception{

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(getNoteByUserIdUrl, 2)
				.header("Authorization", "Bearer " + adminJwtToken);

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(200))
				.andExpect(jsonPath("$.totalElements", equalTo(3)))
				.andExpect(jsonPath("$.content[0].content", equalTo("Test note 1")))
				.andExpect(jsonPath("$.content[0].id", notNullValue()))
				.andExpect(jsonPath("$.content[0].date_created", notNullValue()))
				.andExpect(jsonPath("$.content[0].last_updated", notNullValue()))
				.andExpect(jsonPath("$.content[0].flashcards", notNullValue()));

		requestBuilder = MockMvcRequestBuilders
				.get(getNoteByUserIdUrl, 3)
				.header("Authorization", "Bearer " + adminJwtToken);

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(200))
				.andExpect(jsonPath("$.totalElements", equalTo(2)))
				.andExpect(jsonPath("$.content[0].content", equalTo("Test note 4")))
				.andExpect(jsonPath("$.content[0].id", notNullValue()))
				.andExpect(jsonPath("$.content[0].date_created", notNullValue()))
				.andExpect(jsonPath("$.content[0].last_updated", notNullValue()))
				.andExpect(jsonPath("$.content[0].flashcards", notNullValue()));
	}

	@Test
	public void getNoteById_success() throws Exception{

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(getNoteByIdUrl, 1)
				.header("Authorization", "Bearer " + test1UserJwtToken);

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(200))
				.andExpect(jsonPath("$.content", equalTo("Test note 1")))
				.andExpect(jsonPath("$.id", notNullValue()))
				.andExpect(jsonPath("$.date_created", notNullValue()))
				.andExpect(jsonPath("$.last_updated", notNullValue()))
				.andExpect(jsonPath("$.user_id", equalTo(2)));
	}

	@Test
	public void getNoteById_notFound() throws Exception{

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(getNoteByIdUrl, 100)
				.header("Authorization", "Bearer " + test1UserJwtToken);

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(404));
	}

	@Disabled
	@Test
	public void getNoteById_unauthenticated() throws Exception{

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(getNoteByIdUrl, 1);

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(401));
	}

	@Disabled
	@Test
	public void getNoteById_unauthorized() throws Exception{

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(getNoteByIdUrl, 4)
				.header("Authorization", "Bearer " + test1UserJwtToken);

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(403));
	}


	@Transactional
	@Test
	public void createNote_success() throws Exception{

		NoteDto noteDto = new NoteDto();
		noteDto.setContent("New note");

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(createNoteUrl)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(noteDto))
				.header("Authorization", "Bearer " + test1UserJwtToken);

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(201))
				.andExpect(jsonPath("$.content", equalTo("New note")))
				.andExpect(jsonPath("$.id", notNullValue()))
				.andExpect(jsonPath("$.date_created", notNullValue()))
				.andExpect(jsonPath("$.last_updated", notNullValue()))
				.andExpect(jsonPath("$.user_id", equalTo(2)));
	}

	@Test
	public void createNote_illegalArgument() throws Exception{

		NoteDto noteDto = new NoteDto();
		noteDto.setContent("");

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(createNoteUrl)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(noteDto))
				.header("Authorization", "Bearer " + test1UserJwtToken);

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(400));
	}

	@Transactional
	@Test
	public void updateNote_success() throws Exception{

		NoteDto noteDto = new NoteDto();
		noteDto.setContent("Updated Note");

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(updateNoteUrl, 1)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(noteDto))
				.header("Authorization", "Bearer " + test1UserJwtToken);

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(200))
				.andExpect(jsonPath("$.content", equalTo("Updated Note")))
				.andExpect(jsonPath("$.id", equalTo(1)))
				.andExpect(jsonPath("$.date_created", notNullValue()))
				.andExpect(jsonPath("$.last_updated", notNullValue()))
				.andExpect(jsonPath("$.user_id", equalTo(2)));
	}

	@Test
	public void updateNote_illegalArgument() throws Exception{

		NoteDto noteDto = new NoteDto();
		noteDto.setContent("");

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(updateNoteUrl, 1)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(noteDto))
				.header("Authorization", "Bearer " + test1UserJwtToken);

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(400));
	}

	@Test
	public void updateNote_noteNotFound() throws Exception{

		NoteDto noteDto = new NoteDto();
		noteDto.setContent("Updated Note");

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(updateNoteUrl, 100)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(noteDto))
				.header("Authorization", "Bearer " + test1UserJwtToken);

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(404));
	}

	@Transactional
	@Test
	public void deleteNoteById_success() throws Exception{

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete(deleteNoteUrl, 1)
				.header("Authorization", "Bearer " + test1UserJwtToken);

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(204));
	}

	@Transactional
	@Test
	public void deleteNoteById_noteNotFound() throws Exception{

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete(deleteNoteUrl, 100)
				.header("Authorization", "Bearer " + test1UserJwtToken);

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(404));
	}

	@Test
	public void getAllNotes_pagination() throws Exception{

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(getNotesUrl)
				.header("Authorization", "Bearer " + adminJwtToken)
				.param("pageNo", "0")
				.param("pageSize", "2");

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(200))
				.andExpect(jsonPath("$.pageNo", equalTo(0)))
				.andExpect(jsonPath("$.pageSize", equalTo(2)))
				.andExpect(jsonPath("$.totalElements", equalTo(5)))
				.andExpect(jsonPath("$.totalPages", equalTo(3)))
				.andExpect(jsonPath("$.last", equalTo(false)))
				.andExpect(jsonPath("$.content[0].content", equalTo("Test note 1")))
				.andExpect(jsonPath("$.content[0].id", notNullValue()))
				.andExpect(jsonPath("$.content[0].user_id", equalTo(2)));
	}

	@Test
	public void getAllNotes_pagination_illegalArgument() throws Exception{

		RequestBuilder requestBuilder1 = MockMvcRequestBuilders
				.get(getNotesUrl)
				.header("Authorization", "Bearer " + adminJwtToken)
				.param("pageNo", "0")
				.param("pageSize", "0");

		mockMvc.perform(requestBuilder1)
				.andExpect(status().is(400));

		RequestBuilder requestBuilder2 = MockMvcRequestBuilders
				.get(getNotesUrl)
				.header("Authorization", "Bearer " + adminJwtToken)
				.param("pageNo", "0")
				.param("pageSize", "200");

		mockMvc.perform(requestBuilder2)
				.andExpect(status().is(400));

		RequestBuilder requestBuilder3 = MockMvcRequestBuilders
				.get(getNotesUrl)
				.header("Authorization", "Bearer " + adminJwtToken)
				.param("pageNo", "-1")
				.param("pageSize", "10");

		mockMvc.perform(requestBuilder3)
				.andExpect(status().is(400));
	}

	@Test
	public void getAllNotes_sorting() throws Exception{

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(getNotesUrl)
				.header("Authorization", "Bearer " + adminJwtToken)
				.param("sortBy", "content")
				.param("sortDir", "desc");

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(200))
				.andExpect(jsonPath("$.totalElements", equalTo(5)))
				.andExpect(jsonPath("$.content[0].content", equalTo("Test note 5")))
				.andExpect(jsonPath("$.content[0].id", notNullValue()))
				.andExpect(jsonPath("$.content[0].user_id", equalTo(3)))
				.andExpect(jsonPath("$.content[4].content", equalTo("Test note 1")))
				.andExpect(jsonPath("$.content[4].id", notNullValue()))
				.andExpect(jsonPath("$.content[4].user_id", equalTo(2)));
	}

	@Test
	public void getAllNotes_sorting_illegalArgument() throws Exception{

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(getNotesUrl)
				.header("Authorization", "Bearer " + adminJwtToken)
				.param("sortBy", "illegalArgument")
				.param("sortDir", "desc");

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(400));
	}

	@Test
	public void generateFlashcard_success() throws Exception{

		mockAiServiceClient();

		GenerateFlashcardRequest request = getGenerateFlashcardRequest(FlashcardType.SHORT_ANSWER);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(generateFlashcardUrl, 1)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))
				.header("Authorization", "Bearer " + test1UserJwtToken);

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(200))
				.andExpect(jsonPath("$.type", equalTo(FlashcardType.SHORT_ANSWER.name())))
				.andExpect(jsonPath("$.question").exists())
				.andExpect(jsonPath("$.short_answer").exists())
				.andExpect(jsonPath("$.note_id", equalTo(1)))
				.andExpect(jsonPath("$.user_id", equalTo(2)))
				.andExpect(jsonPath("$.id", notNullValue()));

	}

	@Test
	public void generateFlashcards_success() throws Exception {

		mockAiServiceClient();

		GenerateFlashcardsRequest request = getGenerateFlashcardsRequest();

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(generateFlashcardsUrl, 1)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))
				.header("Authorization", "Bearer " + test1UserJwtToken);

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(200))
				.andExpect(jsonPath("$", hasSize(4)));
	}

	private ResponseEntity<FlashcardDto> getMockFlashcardServiceFlashcardResponse() {

		ShortAnswerFlashcardDto flashcardDto = (ShortAnswerFlashcardDto) FlashcardType.SHORT_ANSWER.getFlashcardExampleDto();
		flashcardDto.setId(1L);
		flashcardDto.setNoteId(1L);
		flashcardDto.setUserId(2L);

		return new ResponseEntity<>(flashcardDto, HttpStatus.OK);
	}

	private ResponseEntity<List<FlashcardDto>> getMockFlashcardServiceFlashcardsResponse() {

		ShortAnswerFlashcardDto flashcardDto1 = (ShortAnswerFlashcardDto) FlashcardType.SHORT_ANSWER.getFlashcardExampleDto();
		flashcardDto1.setId(1L);
		flashcardDto1.setNoteId(1L);
		flashcardDto1.setUserId(2L);

		FillInTheBlankFlashcardDto flashcardDto2 = (FillInTheBlankFlashcardDto) FlashcardType.FILL_IN_THE_BLANK.getFlashcardExampleDto();
		flashcardDto2.setId(2L);
		flashcardDto2.setNoteId(1L);
		flashcardDto2.setUserId(2L);

		MultipleChoiceFlashcardDto flashcardDto3 = (MultipleChoiceFlashcardDto) FlashcardType.MULTIPLE_CHOICE.getFlashcardExampleDto();
		flashcardDto3.setId(3L);
		flashcardDto3.setNoteId(1L);
		flashcardDto3.setUserId(2L);

		TrueFalseFlashcardDto flashcardDto4 = (TrueFalseFlashcardDto) FlashcardType.TRUE_FALSE.getFlashcardExampleDto();
		flashcardDto4.setId(4L);
		flashcardDto4.setNoteId(1L);
		flashcardDto4.setUserId(2L);

		List<FlashcardDto> abstractFlashcardList = List.of(flashcardDto1, flashcardDto2, flashcardDto3, flashcardDto4);

		return new ResponseEntity<>(abstractFlashcardList, HttpStatus.OK);
	}

	private void mockFlashcardServiceClient() {
		Mockito.when(flashcardServiceClient.getFlashcardsByNoteId(Mockito.anyLong(), Mockito.anyString()))
				.thenReturn(getMockFlashcardServiceFlashcardsResponse());

		Mockito.when(flashcardServiceClient.createFlashcard(Mockito.anyLong(), Mockito.any(), Mockito.anyString()))
				.thenReturn(getMockFlashcardServiceFlashcardResponse());

		Mockito.when(flashcardServiceClient.createFlashcards(Mockito.anyLong(), Mockito.any(), Mockito.anyString()))
				.thenReturn(getMockFlashcardServiceFlashcardsResponse());
	}

	private ResponseEntity<FlashcardDto> getMockAiGenerateFlashcardResponse() {
		return new ResponseEntity<>(FlashcardType.SHORT_ANSWER.getFlashcardExampleDto(), HttpStatus.OK);
	}

	private ResponseEntity<List<FlashcardDto>> getMockAiGenerateFlashcardsResponse() {
		List<FlashcardDto> generatedFlashcard = List.of(
				FlashcardType.SHORT_ANSWER.getFlashcardExampleDto(),
				FlashcardType.FILL_IN_THE_BLANK.getFlashcardExampleDto(),
				FlashcardType.MULTIPLE_CHOICE.getFlashcardExampleDto(),
				FlashcardType.TRUE_FALSE.getFlashcardExampleDto()
		);
		return new ResponseEntity<>(generatedFlashcard, HttpStatus.OK);
	}

	private void mockAiServiceClient() {
		Mockito.when(aiServiceClient.generateFlashcard(Mockito.any(), Mockito.anyString()))
				.thenReturn(getMockAiGenerateFlashcardResponse());

		Mockito.when(aiServiceClient.generateFlashcards(Mockito.any(), Mockito.anyString()))
				.thenReturn(getMockAiGenerateFlashcardsResponse());
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

		NoteDto note = new NoteDto();
		note.setContent("content");

		return new GenerateFlashcardsRequest(note, typeQuantities);
	}

}
