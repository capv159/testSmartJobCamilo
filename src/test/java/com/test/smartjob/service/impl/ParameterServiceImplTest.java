package com.test.smartjob.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.test.smartjob.entity.ParameterEntity;
import com.test.smartjob.exception.BadDataException;
import com.test.smartjob.exception.BusinessException;
import com.test.smartjob.model.ParameterModel;
import com.test.smartjob.repository.ParameterRepository;

@ExtendWith(MockitoExtension.class)
public class ParameterServiceImplTest {

	@Mock
	private ParameterRepository parameterRepository;

	@InjectMocks
	private ParameterServiceImpl parameterService;

	private ParameterModel validParameterModel;
	private ParameterEntity existingParameterEntity;

	@BeforeEach
	void setUp() {

		validParameterModel = new ParameterModel();
		validParameterModel.setName("password_regex");
		validParameterModel.setValue("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,20}$");

		existingParameterEntity = new ParameterEntity();
		existingParameterEntity.setId(1L);
		existingParameterEntity.setName("password_regex");
		existingParameterEntity.setValueparameter("old_regex_value");
	}

	@Test	   
	void testUpdateParameterSuccess() {

		when(parameterRepository.findByName(validParameterModel.getName()))
		.thenReturn(Optional.of(existingParameterEntity));	       
		when(parameterRepository.save(any(ParameterEntity.class))).thenReturn(existingParameterEntity);	      
		ParameterModel result = parameterService.updateParameter(validParameterModel);	      
		assertNotNull(result);
		assertEquals(validParameterModel.getName(), result.getName());
		assertEquals(validParameterModel.getValue(), result.getValue());	       
		verify(parameterRepository, times(1)).findByName(validParameterModel.getName());	   
		verify(parameterRepository, times(1)).save(any(ParameterEntity.class));	     
		assertEquals(validParameterModel.getValue(), existingParameterEntity.getValueparameter());
	}

	@Test
	void testUpdateParameterNotFound() {

		when(parameterRepository.findByName(validParameterModel.getName()))
		.thenReturn(Optional.empty());

		BadDataException thrown = assertThrows(BadDataException.class, () -> {
			parameterService.updateParameter(validParameterModel);
		});

		assertEquals("No existe parametro para actualizar", thrown.getMessage());

		verify(parameterRepository, times(1)).findByName(validParameterModel.getName());

		verify(parameterRepository, never()).save(any(ParameterEntity.class));
	}

	@Test
	void testUpdateParameterInvalidExpression() {

		ParameterModel invalidExpressionModel = new ParameterModel();
		invalidExpressionModel.setName("some_param");
		invalidExpressionModel.setValue("[invalid regex");

		BusinessException thrown = assertThrows(BusinessException.class, () -> {
			parameterService.updateParameter(invalidExpressionModel);
		});

		assertEquals("expresion invalida", thrown.getMessage());

		verify(parameterRepository, never()).findByName(any(String.class));
		verify(parameterRepository, never()).save(any(ParameterEntity.class));
	}

	@Test
	void testConsultParameterSuccess() {

		String paramName = "my_parameter";
		ParameterEntity foundEntity = new ParameterEntity();
		foundEntity.setId(2L);
		foundEntity.setName(paramName);
		foundEntity.setValueparameter("value_of_my_parameter");

		when(parameterRepository.findByName(paramName)).thenReturn(Optional.of(foundEntity));

		ParameterModel result = parameterService.consultParameter(paramName);

		assertNotNull(result);
		assertEquals(paramName, result.getName());
		assertEquals("value_of_my_parameter", result.getValue());

		verify(parameterRepository, times(1)).findByName(paramName);
	}

	@Test
	void testConsultParameterNotFound() {

		String paramName = "non_existent_param";

		when(parameterRepository.findByName(paramName)).thenReturn(Optional.empty());

		BadDataException thrown = assertThrows(BadDataException.class, () -> {
			parameterService.consultParameter(paramName);
		});

		assertEquals("No existe parametro", thrown.getMessage());
		verify(parameterRepository, times(1)).findByName(paramName);
	}

}
