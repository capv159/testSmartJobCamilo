package com.test.smartjob.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
import com.test.smartjob.model.PhoneModel;
import com.test.smartjob.model.UserModel;
import com.test.smartjob.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

	@Mock
	private UserService userService;

	@InjectMocks
	private UserController userController;

	private MockMvc mockMvc;

	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {

		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
		objectMapper = new ObjectMapper();
	}

	@Test
	void testRegisterUserControllerSuccess() throws Exception {

		PhoneModel phoneModel = new PhoneModel();
		phoneModel.setCitycode("57");
		phoneModel.setCountryCode("57");
		phoneModel.setNumber("30089898");
		List<PhoneModel> phones = new ArrayList<>();
		phones.add(phoneModel);

		UserModel requestUserModel = new UserModel();
		requestUserModel.setName("Juan Perez");
		requestUserModel.setEmail("juan.perez@dominio.cl");
		requestUserModel.setPassword("123abc");
		requestUserModel.setPhones(phones);

		UserModel expectedServiceResponse = new UserModel();
		expectedServiceResponse.setName("Juan Perez");
		expectedServiceResponse.setEmail("juan.perez@example.com");
		expectedServiceResponse.setPassword("123abc");
		expectedServiceResponse.setId(UUID.randomUUID());
		expectedServiceResponse.setCreated(LocalDateTime.now());
		expectedServiceResponse.setModified(LocalDateTime.now());

		when(userService.registerUserService(any(UserModel.class))).thenReturn(expectedServiceResponse);

		mockMvc.perform(post("/api/v1/user/register").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(requestUserModel)))

				.andExpect(status().isOk());
	}

	@Test
	void testRegisterUserControllerbadRequest() throws Exception {

		PhoneModel phoneModel = new PhoneModel();
		phoneModel.setCitycode("57");
		phoneModel.setCountryCode("57");
		phoneModel.setNumber("30089898");
		List<PhoneModel> phones = new ArrayList<>();
		phones.add(phoneModel);

		UserModel requestUserModel = new UserModel();
		requestUserModel.setName("Juan Perez");
		requestUserModel.setEmail("juan.perez@dominiooo.cl");
		requestUserModel.setPassword("123abc");
		requestUserModel.setPhones(phones);

		mockMvc.perform(post("/api/v1/user/register").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(requestUserModel))).andExpect(status().isBadRequest());
	}

}
