package com.vincennlin.userservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vincennlin.userservice.payload.LoginDto;
import com.vincennlin.userservice.payload.RegisterDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class UserServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(UserServiceApplicationTests.class);

	private final ObjectMapper objectMapper = new ObjectMapper();

	private final String registerUrl = "/api/v1/auth/register";
	private final String loginUrl = "/api/v1/auth/login";

	@Value("${spring.datasource.url}")
	private String datasourceUrl;

	@Test
	public void contextLoads() {
		System.out.println("Datasource URL: " + datasourceUrl);

		assertThat(datasourceUrl).isEqualTo("jdbc:h2:mem:testdb");
	}

	@Transactional
	@Test
	public void register_success() throws Exception {

		RegisterDto registerDto = getRegisterDtoTemplate();

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(registerUrl)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(registerDto));

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(201))
				.andExpect(jsonPath("$.message").value("User registered successfully!"))
				.andExpect(jsonPath("$.account_info.id").exists())
				.andExpect(jsonPath("$.account_info.name").value("test_user"))
				.andExpect(jsonPath("$.account_info.username").value("test_user"))
				.andExpect(jsonPath("$.account_info.email").value("test_user@gmail.com"))
				.andExpect(jsonPath("$.account_info.date_created").exists())
				.andExpect(jsonPath("$.account_info.last_updated").exists());
	}

	@Transactional
	@Test
	public void register_invalidEmail() throws Exception {

		RegisterDto registerDto = getRegisterDtoTemplate();
		registerDto.setEmail("invalid_email_format");

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(registerUrl)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(registerDto));

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(400));
	}

	@Transactional
	@Test
	public void register_invalidPassword() throws Exception {

		RegisterDto registerDto = getRegisterDtoTemplate();
		registerDto.setPassword("123");

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(registerUrl)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(registerDto));

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(400));
	}

	@Transactional
	@Test
	public void register_usernameAlreadyExists() throws Exception {

		RegisterDto registerDto = getRegisterDtoTemplate();
		registerDto.setUsername("test1");

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(registerUrl)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(registerDto));

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(400))
				.andExpect(jsonPath("$.message").value("Username is already taken!"));
	}

	@Transactional
	@Test
	public void register_emailAlreadyExists() throws Exception {

		RegisterDto registerDto = getRegisterDtoTemplate();
		registerDto.setEmail("test1@gmail.com");

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(registerUrl)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(registerDto));

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(400))
				.andExpect(jsonPath("$.message").value("Email is already taken!"));
	}

	@Transactional
	@Test
	public void register_invalidArguments() throws Exception {

		RegisterDto registerDto = getRegisterDtoTemplate();
		registerDto.setName("");

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(registerUrl)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(registerDto));

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(400));

		registerDto.setName("test_user");
		registerDto.setUsername("");

		requestBuilder = MockMvcRequestBuilders
				.post(registerUrl)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(registerDto));

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(400));

		registerDto.setUsername("test_user");
		registerDto.setEmail("");

		requestBuilder = MockMvcRequestBuilders
				.post(registerUrl)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(registerDto));

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(400));

		registerDto.setEmail("test_user@gmail.com");
		registerDto.setPassword("");

		requestBuilder = MockMvcRequestBuilders
				.post(registerUrl)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(registerDto));

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(400));
	}

	@Test
	public void login_success() throws Exception {

		LoginDto loginDto = new LoginDto();
		loginDto.setUsernameOrEmail("admin");
		loginDto.setPassword("admin");

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(loginUrl)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginDto));

		MvcResult adminResult = mockMvc.perform(requestBuilder)
				.andExpect(status().is(200))
				.andExpect(header().exists("access_token"))
				.andExpect(header().string("token_type", "Bearer"))
				.andReturn();

		loginDto.setUsernameOrEmail("test1");
		loginDto.setPassword("test1");

		requestBuilder = MockMvcRequestBuilders
				.post(loginUrl)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginDto));

		MvcResult test1Result = mockMvc.perform(requestBuilder)
				.andExpect(status().is(200))
				.andExpect(header().exists("access_token"))
				.andExpect(header().string("token_type", "Bearer"))
				.andReturn();

		loginDto.setUsernameOrEmail("test2");
		loginDto.setPassword("test2");

		requestBuilder = MockMvcRequestBuilders
				.post(loginUrl)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginDto));

		MvcResult test2Result = mockMvc.perform(requestBuilder)
				.andExpect(status().is(200))
				.andExpect(header().exists("access_token"))
				.andExpect(header().string("token_type", "Bearer"))
				.andReturn();

		logger.info("Admin token: " + adminResult.getResponse().getHeader("access_token"));
		logger.info("Test1 token: " + test1Result.getResponse().getHeader("access_token"));
		logger.info("Test2 token: " + test2Result.getResponse().getHeader("access_token"));
	}

	@Test
	public void login_invalidCredentials() throws Exception {

		LoginDto loginDto = new LoginDto();
		loginDto.setUsernameOrEmail("test1");
		loginDto.setPassword("wrongpassword");

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(loginUrl)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginDto));

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(401));
	}

	private RegisterDto getRegisterDtoTemplate() {
		RegisterDto registerDto = new RegisterDto();
		registerDto.setName("test_user");
		registerDto.setUsername("test_user");
		registerDto.setEmail("test_user@gmail.com");
		registerDto.setPassword("test_user");
		return registerDto;
	}
}
