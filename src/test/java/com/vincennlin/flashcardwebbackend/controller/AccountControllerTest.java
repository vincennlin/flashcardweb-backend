package com.vincennlin.flashcardwebbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vincennlin.flashcardwebbackend.payload.account.AccountInfoDto;
import com.vincennlin.flashcardwebbackend.payload.auth.LoginDto;
import com.vincennlin.flashcardwebbackend.payload.auth.LoginResponse;
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

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String loginUrl = "/api/v1/auth/login";

    @Transactional
    @Test
    public void getCurrentAccountInfo_success_user() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/account/info")
                .header("Authorization", "Bearer " + getTest1UserJwtToken());

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("test1"))
                .andExpect(jsonPath("$.username").value("test1"))
                .andExpect(jsonPath("$.email").value("test1@gmail.com"))
                .andExpect(jsonPath("$.roles[0].name").value("ROLE_USER"))
                .andExpect(jsonPath("$.date_created").exists())
                .andExpect(jsonPath("$.last_updated").exists());
    }

    @Transactional
    @Test
    public void getCurrentAccountInfo_success_admin() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/account/info")
                .header("Authorization", "Bearer " + getAdminJwtToken());

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("admin"))
                .andExpect(jsonPath("$.username").value("admin"))
                .andExpect(jsonPath("$.email").value("admin@gmail.com"))
                .andExpect(jsonPath("$.roles[0].name").value("ROLE_ADMIN"))
                .andExpect(jsonPath("$.date_created").exists())
                .andExpect(jsonPath("$.last_updated").exists());
    }

    @Transactional
    @Test
    public void getAllUsers_success() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/account/admin/all")
                .header("Authorization", "Bearer " + getAdminJwtToken());

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].username").value("admin"))
                .andExpect(jsonPath("$[0].email").value("admin@gmail.com"))
                .andExpect(jsonPath("$[0].roles[0].name").value("ROLE_ADMIN"))
                .andExpect(jsonPath("$[1].roles[0].name").value("ROLE_USER"))
                .andExpect(jsonPath("$[2].roles[0].name").value("ROLE_USER"));
    }

    @Transactional
    @Test
    public void getAllUsers_unauthorized() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/account/admin/all")
                .header("Authorization", "Bearer " + getTest1UserJwtToken());

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(401));
    }

    @Transactional
    @Test
    public void updateAccountInfo_success() throws Exception {

        AccountInfoDto accountInfoDto = getAccountInfoDtoTemplate();
        accountInfoDto.setName("test_user_updated");
        accountInfoDto.setUsername("test_user_updated");
        accountInfoDto.setEmail("test_user_updated@gmail.com");
        accountInfoDto.setPassword("test_user_updated");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/v1/account/info")
                .header("Authorization", "Bearer " + getTest1UserJwtToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountInfoDto));

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.message").value("User updated successfully! Please login again."))
                .andExpect(jsonPath("$.account_info.id").exists())
                .andExpect(jsonPath("$.account_info.name").value("test_user_updated"))
                .andExpect(jsonPath("$.account_info.username").value("test_user_updated"))
                .andExpect(jsonPath("$.account_info.email").value("test_user_updated@gmail.com"))
                .andExpect(jsonPath("$.account_info.roles[0].name").value("ROLE_USER"))
                .andExpect(jsonPath("$.account_info.date_created").exists())
                .andExpect(jsonPath("$.account_info.last_updated").exists());
    }

    private String getAdminJwtToken() throws Exception {

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

    private AccountInfoDto getAccountInfoDtoTemplate() {
        AccountInfoDto accountInfoDto = new AccountInfoDto();
        accountInfoDto.setName("test_user");
        accountInfoDto.setUsername("test_user");
        accountInfoDto.setEmail("test_user@gmail.com");
        accountInfoDto.setPassword("test_user");
        return accountInfoDto;
    }
}