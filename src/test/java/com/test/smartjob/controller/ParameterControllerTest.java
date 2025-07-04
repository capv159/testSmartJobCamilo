package com.test.smartjob.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.test.smartjob.model.ParameterModel;
import com.test.smartjob.service.ParameterService;

@ExtendWith(MockitoExtension.class)
public class ParameterControllerTest {

	@Mock
	private ParameterService parameterService;

	@InjectMocks
	private ParameterController parameterController;

	private MockMvc mockMvc;

	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {

		mockMvc = MockMvcBuilders.standaloneSetup(parameterController).build();
		objectMapper = new ObjectMapper();
	}

	@Test
	void testUpdateParameterControllerSuccess() throws Exception {

		ParameterModel requestModel = new ParameterModel();
		requestModel.setName("password_regex");
		requestModel.setValue("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,20}$");

		ParameterModel responseModel = new ParameterModel();
		responseModel.setName("password_regex");
		responseModel.setValue("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,20}$");

		when(parameterService.updateParameter(any(ParameterModel.class))).thenReturn(responseModel);

		mockMvc.perform(put("/api/v1/parameter/expresion").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(requestModel))).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(responseModel.getName()))
				.andExpect(jsonPath("$.value").value(responseModel.getValue()));
	}

	@Test

	void testUpdateParameterControllerInvalidInput() throws Exception {

		ParameterModel invalidModel = new ParameterModel();
		invalidModel.setName("");

		mockMvc.perform(put("/api/v1/parameter/expresion").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(invalidModel))).andExpect(status().isBadRequest());
	}

}
