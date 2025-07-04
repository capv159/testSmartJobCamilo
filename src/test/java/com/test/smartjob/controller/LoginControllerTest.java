package com.test.smartjob.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.smartjob.model.LoginModel;
import com.test.smartjob.service.UserService;

@ExtendWith(MockitoExtension.class)
public class LoginControllerTest {

	@Mock
	private UserService userService;

	@InjectMocks
	private LoginController loginController;

	private MockMvc mockMvc;

	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {

		mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();// nueva forma para cargar solo una parte del
																			// contexto
		objectMapper = new ObjectMapper();
	}

	@Test

	void testLoginOk() throws Exception {

		LoginModel requestLoginModel = new LoginModel();
		requestLoginModel.setId(UUID.randomUUID());
		requestLoginModel.setPassword("password123");

		LoginModel expectedLoginModel = new LoginModel();
		expectedLoginModel.setId(UUID.randomUUID());

		when(userService.validateUserService(any(LoginModel.class))).thenReturn(expectedLoginModel);

		mockMvc.perform(post("/api/login").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(requestLoginModel))).andExpect(status().isOk());

	}

	@Test

	void testLoginBadrequest() throws Exception {

		LoginModel invalidLoginModel = new LoginModel();
		invalidLoginModel.setId(UUID.randomUUID());
		invalidLoginModel.setPassword("");

		mockMvc.perform(post("/api/login").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(invalidLoginModel))).andExpect(status().isBadRequest());
	}

}
