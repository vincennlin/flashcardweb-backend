package com.vincennlin.noteservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vincennlin.noteservice.client.FlashcardServiceClient;
import com.vincennlin.noteservice.payload.NoteDto;
import com.vincennlin.noteservice.payload.flashcard.FlashcardDto;
import com.vincennlin.noteservice.payload.flashcard.concrete.ShortAnswerFlashcardDto;
import com.vincennlin.noteservice.payload.flashcard.concrete.TrueFalseFlashcardDto;
import feign.Response;
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

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
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

	@BeforeEach
	public void beforeEach() {

		ResponseEntity<List<FlashcardDto>> mockResponse = getMockFlashcardsResponse();

		Mockito.when(flashcardServiceClient.getFlashcardsByNoteId(Mockito.anyLong(), Mockito.anyString()))
				.thenReturn(mockResponse);
	}

	@Test
	public void getAllNotes_admin() throws Exception{

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/v1/notes")
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
				.get("/api/v1/notes")
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
				.get("/api/v1/notes")
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
				.get("/api/v1/notes/user/{id}", 2)
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
				.get("/api/v1/notes/user/{id}", 3)
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
				.get("/api/v1/notes/{id}", 1)
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
				.get("/api/v1/notes/{id}", 100)
				.header("Authorization", "Bearer " + test1UserJwtToken);

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(404));
	}

	@Disabled
	@Test
	public void getNoteById_unauthenticated() throws Exception{

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/v1/notes/{id}", 1);

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(401));
	}

	@Disabled
	@Test
	public void getNoteById_unauthorized() throws Exception{

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/v1/notes/{id}", 4)
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
				.post("/api/v1/notes")
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
				.post("/api/v1/notes")
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
				.put("/api/v1/notes/{id}", 1)
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
				.put("/api/v1/notes/{id}", 1)
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
				.put("/api/v1/notes/{id}", 100)
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
				.delete("/api/v1/notes/{id}", 1)
				.header("Authorization", "Bearer " + test1UserJwtToken);

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(204));
	}

	@Transactional
	@Test
	public void deleteNoteById_noteNotFound() throws Exception{

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/api/v1/notes/{id}", 100)
				.header("Authorization", "Bearer " + test1UserJwtToken);

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(404));
	}

	@Test
	public void getAllNotes_pagination() throws Exception{

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/v1/notes")
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
				.get("/api/v1/notes")
				.header("Authorization", "Bearer " + adminJwtToken)
				.param("pageNo", "0")
				.param("pageSize", "0");

		mockMvc.perform(requestBuilder1)
				.andExpect(status().is(400));

		RequestBuilder requestBuilder2 = MockMvcRequestBuilders
				.get("/api/v1/notes")
				.header("Authorization", "Bearer " + adminJwtToken)
				.param("pageNo", "0")
				.param("pageSize", "200");

		mockMvc.perform(requestBuilder2)
				.andExpect(status().is(400));

		RequestBuilder requestBuilder3 = MockMvcRequestBuilders
				.get("/api/v1/notes")
				.header("Authorization", "Bearer " + adminJwtToken)
				.param("pageNo", "-1")
				.param("pageSize", "10");

		mockMvc.perform(requestBuilder3)
				.andExpect(status().is(400));
	}

	@Test
	public void getAllNotes_sorting() throws Exception{

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/v1/notes")
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
				.get("/api/v1/notes")
				.header("Authorization", "Bearer " + adminJwtToken)
				.param("sortBy", "illegalArgument")
				.param("sortDir", "desc");

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(400));
	}

	private ResponseEntity<List<FlashcardDto>> getMockFlashcardsResponse() {

		ShortAnswerFlashcardDto flashcardDto1 = new ShortAnswerFlashcardDto();
		flashcardDto1.setId(1L);
		flashcardDto1.setQuestion("What is Java?");
		flashcardDto1.setShortAnswer("Java is a programming language.");
		flashcardDto1.setNoteId(1L);
		flashcardDto1.setUserId(2L);

		TrueFalseFlashcardDto flashcardDto2 = new TrueFalseFlashcardDto();
		flashcardDto2.setId(2L);
		flashcardDto2.setQuestion("Java is a programming language.");
		flashcardDto2.setTrueFalseAnswer(true);
		flashcardDto2.setNoteId(1L);
		flashcardDto2.setUserId(2L);

		List<FlashcardDto> flashcardDtos = List.of(flashcardDto1, flashcardDto2);

		return new ResponseEntity<>(flashcardDtos, HttpStatus.OK);
	}
}
