package com.vincennlin.flashcardwebbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vincennlin.flashcardwebbackend.payload.note.NoteDto;
import com.vincennlin.flashcardwebbackend.payload.auth.LoginResponse;
import com.vincennlin.flashcardwebbackend.payload.auth.LoginDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void getAllNotes_admin() throws Exception{

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/notes")
                .header("Authorization", "Bearer " + getAdminJwtToken());

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
                .header("Authorization", "Bearer " + getTest1UserJwtToken());

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.totalElements", equalTo(3)))
                .andExpect(jsonPath("$.content[0].content", equalTo("Test note 1")))
                .andExpect(jsonPath("$.content[0].id", notNullValue()))
                .andExpect(jsonPath("$.content[0].date_created", notNullValue()))
                .andExpect(jsonPath("$.content[0].last_updated", notNullValue()))
                .andExpect(jsonPath("$.content[0].flashcards", notNullValue()))
                .andExpect(jsonPath("$.content[0].user_id", equalTo(2)));

        String test2UserJwtToken = getTest2UserJwtToken();

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
                .header("Authorization", "Bearer " + getAdminJwtToken());

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
                .header("Authorization", "Bearer " + getAdminJwtToken());

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
                .header("Authorization", "Bearer " + getTest1UserJwtToken());

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
                .header("Authorization", "Bearer " + getTest1UserJwtToken());

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(404));
    }

    @Test
    public void getNoteById_unauthenticated() throws Exception{

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/notes/{id}", 1);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(401));
    }

    @Test
    public void getNoteById_unauthorized() throws Exception{

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/notes/{id}", 4)
                .header("Authorization", "Bearer " + getTest1UserJwtToken());

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
                .header("Authorization", "Bearer " + getTest1UserJwtToken());

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
                .header("Authorization", "Bearer " + getTest1UserJwtToken());

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
                .header("Authorization", "Bearer " + getTest1UserJwtToken());

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
                .header("Authorization", "Bearer " + getTest1UserJwtToken());

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
                .header("Authorization", "Bearer " + getTest1UserJwtToken());

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(404));
    }

    @Transactional
    @Test
    public void deleteNoteById_success() throws Exception{

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/v1/notes/{id}", 1)
                .header("Authorization", "Bearer " + getTest1UserJwtToken());

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(204));
    }

    @Transactional
    @Test
    public void deleteNoteById_noteNotFound() throws Exception{

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/v1/notes/{id}", 100)
                .header("Authorization", "Bearer " + getTest1UserJwtToken());

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(404));
    }

    @Test
    public void getAllNotes_pagination() throws Exception{

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/notes")
                .header("Authorization", "Bearer " + getAdminJwtToken())
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
                .header("Authorization", "Bearer " + getAdminJwtToken())
                .param("pageNo", "0")
                .param("pageSize", "0");

        mockMvc.perform(requestBuilder1)
                .andExpect(status().is(400));

        RequestBuilder requestBuilder2 = MockMvcRequestBuilders
                .get("/api/v1/notes")
                .header("Authorization", "Bearer " + getAdminJwtToken())
                .param("pageNo", "0")
                .param("pageSize", "200");

        mockMvc.perform(requestBuilder2)
                .andExpect(status().is(400));

        RequestBuilder requestBuilder3 = MockMvcRequestBuilders
                .get("/api/v1/notes")
                .header("Authorization", "Bearer " + getAdminJwtToken())
                .param("pageNo", "-1")
                .param("pageSize", "10");

        mockMvc.perform(requestBuilder3)
                .andExpect(status().is(400));
    }

    @Test
    public void getAllNotes_sorting() throws Exception{

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/notes")
                .header("Authorization", "Bearer " + getAdminJwtToken())
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
                .header("Authorization", "Bearer " + getAdminJwtToken())
                .param("sortBy", "illegalArgument")
                .param("sortDir", "desc");

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    private String getAdminJwtToken() throws Exception {

        String loginUrl = "/api/v1/auth/login";

        LoginDto loginDto = new LoginDto();
        loginDto.setUsernameOrEmail("admin");
        loginDto.setPassword("admin");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(loginUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDto));

        MvcResult result = mockMvc.perform(requestBuilder)
                .andReturn();

        LoginResponse loginResponse = objectMapper.readValue(mockMvc.perform(requestBuilder)
                .andReturn()
                .getResponse()
                .getContentAsString(), LoginResponse.class);

        return loginResponse.getAccessToken();
    }

    private String getTest1UserJwtToken() throws Exception {

        String loginUrl = "/api/v1/auth/login";

        LoginDto loginDto = new LoginDto();
        loginDto.setUsernameOrEmail("test1");
        loginDto.setPassword("test1");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(loginUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDto));


        LoginResponse loginResponse = objectMapper.readValue(mockMvc.perform(requestBuilder)
                .andReturn()
                .getResponse()
                .getContentAsString(), LoginResponse.class);

        return loginResponse.getAccessToken();
    }

    private String getTest2UserJwtToken() throws Exception {

        String loginUrl = "/api/v1/auth/login";

        LoginDto loginDto = new LoginDto();
        loginDto.setUsernameOrEmail("test2");
        loginDto.setPassword("test2");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(loginUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDto));

        LoginResponse loginResponse = objectMapper.readValue(mockMvc.perform(requestBuilder)
                .andReturn()
                .getResponse()
                .getContentAsString(), LoginResponse.class);

        return loginResponse.getAccessToken();
    }
}