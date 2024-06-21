package com.vincennlin.flashcardwebbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class NoteControllerTest {

    @Autowired
    MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Disabled
    @Test
    void getAllNotes() throws Exception{

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/notes");

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.totalElements", equalTo(5)))
                .andExpect(jsonPath("$.results[0].content", equalTo("Test note 1")))
                .andExpect(jsonPath("$.results[0].note_id", notNullValue()))
                .andExpect(jsonPath("$.results[0].date_created", notNullValue()))
                .andExpect(jsonPath("$.results[0].last_updated", notNullValue()));
    }

    @Disabled
    @Test
    void getNoteById() throws Exception{

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/notes/{id}", 1);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.content", equalTo("Test note 1")))
                .andExpect(jsonPath("$.note_id", notNullValue()))
                .andExpect(jsonPath("$.date_created", notNullValue()))
                .andExpect(jsonPath("$.last_updated", notNullValue()));
    }

    @Test
    void createNote() {
    }

    @Test
    void updateNote() {
    }

    @Test
    void deleteNoteById() {
    }
}