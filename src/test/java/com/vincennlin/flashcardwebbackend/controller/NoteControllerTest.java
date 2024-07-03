package com.vincennlin.flashcardwebbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vincennlin.flashcardwebbackend.payload.note.NoteDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
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

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void getAllNotes() throws Exception{

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/notes");

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
    public void getNoteById_success() throws Exception{

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/notes/{id}", 1);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.content", equalTo("Test note 1")))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.date_created", notNullValue()))
                .andExpect(jsonPath("$.last_updated", notNullValue()));
    }

    @Test
    public void getNoteById_notFound() throws Exception{

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/notes/{id}", 100);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(404));
    }

    @Transactional
    @Test
    public void createNote_success() throws Exception{

        NoteDto noteDto = new NoteDto();
        noteDto.setContent("New note");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(noteDto));

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.content", equalTo("New note")))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.date_created", notNullValue()))
                .andExpect(jsonPath("$.last_updated", notNullValue()));
    }

    @Test
    public void createNote_illegalArgument() throws Exception{

        NoteDto noteDto = new NoteDto();
        noteDto.setContent("");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(noteDto));

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
                .content(objectMapper.writeValueAsString(noteDto));

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.content", equalTo("Updated Note")))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.date_created", notNullValue()))
                .andExpect(jsonPath("$.last_updated", notNullValue()));
    }

    @Test
    public void updateNote_illegalArgument() throws Exception{

        NoteDto noteDto = new NoteDto();
        noteDto.setContent("");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/v1/notes/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(noteDto));

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
                .content(objectMapper.writeValueAsString(noteDto));

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(404));
    }

    @Transactional
    @Test
    public void deleteNoteById_success() throws Exception{

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/v1/notes/{id}", 1);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(204));
    }

    @Transactional
    @Test
    public void deleteNoteById_noteNotFound() throws Exception{

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/v1/notes/{id}", 100);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(404));
    }

    @Test
    public void getAllNotes_pagination() throws Exception{

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/notes")
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
                .andExpect(jsonPath("$.content[0].id", notNullValue()));
    }

    @Test
    public void getAllNotes_pagination_illegalArgument() throws Exception{

        RequestBuilder requestBuilder1 = MockMvcRequestBuilders
                .get("/api/v1/notes")
                .param("pageNo", "0")
                .param("pageSize", "0");

        mockMvc.perform(requestBuilder1)
                .andExpect(status().is(400));

        RequestBuilder requestBuilder2 = MockMvcRequestBuilders
                .get("/api/v1/notes")
                .param("pageNo", "0")
                .param("pageSize", "200");

        mockMvc.perform(requestBuilder2)
                .andExpect(status().is(400));

        RequestBuilder requestBuilder3 = MockMvcRequestBuilders
                .get("/api/v1/notes")
                .param("pageNo", "-1")
                .param("pageSize", "10");

        mockMvc.perform(requestBuilder3)
                .andExpect(status().is(400));
    }

    @Test
    public void getAllNotes_sorting() throws Exception{

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/notes")
                .param("sortBy", "content")
                .param("sortDir", "desc");

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.totalElements", equalTo(5)))
                .andExpect(jsonPath("$.content[0].content", equalTo("Test note 5")))
                .andExpect(jsonPath("$.content[0].id", notNullValue()))
                .andExpect(jsonPath("$.content[4].content", equalTo("Test note 1")))
                .andExpect(jsonPath("$.content[4].id", notNullValue()));
    }

    @Test
    public void getAllNotes_sorting_illegalArgument() throws Exception{

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/notes")
                .param("sortBy", "illegalArgument")
                .param("sortDir", "desc");

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }
}