package com.test.smartjob.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.test.smartjob.entity.ParameterEntity;
import com.test.smartjob.entity.UserEntity;
import com.test.smartjob.exception.BadDataException;
import com.test.smartjob.exception.BusinessException;
import com.test.smartjob.model.LoginModel;
import com.test.smartjob.model.UserModel;
import com.test.smartjob.repository.ParameterRepository;
import com.test.smartjob.repository.UserRepository;
import com.test.smartjob.security.JwtUtil;
import com.test.smartjob.util.UtilService;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private ParameterRepository parameterRepository;

	@Mock
	private UtilService utilService;

	@Mock
	private JwtUtil jwtUtil;

	@InjectMocks
	private UserServiceImpl userService;

	private UserModel testUserModel;
	private UserEntity testUserEntity;
	private ParameterEntity passwordRegexParameter;
	private LoginModel testLoginModel;

	@BeforeEach
	void setUp() {

		testUserModel = new UserModel();
		testUserModel.setName("Juan Perez");
		testUserModel.setEmail("juan.perez@example.com");
		testUserModel.setPassword("TestPass123!");

		testUserEntity = new UserEntity();
		testUserEntity.setId(UUID.randomUUID());
		testUserEntity.setName("Juan Perez");
		testUserEntity.setEmail("juan.perez@example.com");
		testUserEntity.setPassword("hashedPassword");
		testUserEntity.setCreated(LocalDateTime.now());
		testUserEntity.setModified(LocalDateTime.now());
		testUserEntity.setLastlogin(LocalDateTime.now());
		testUserEntity.setToken("some.jwt.token");

		passwordRegexParameter = new ParameterEntity();
		passwordRegexParameter.setName("password_pattern");
		passwordRegexParameter
		.setValueparameter("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,20}$");

		testLoginModel = new LoginModel();
		testLoginModel.setId(UUID.randomUUID());

		testLoginModel.setPassword("hashedPassword");
	}

	@Test
	void testRegisterUserServiceSuccess() {

		when(userRepository.findByEmail(testUserModel.getEmail())).thenReturn(Optional.empty());        
		when(parameterRepository.findByName("password_pattern")).thenReturn(Optional.of(passwordRegexParameter));       
		when(utilService.modelUserEntity(testUserModel)).thenReturn(testUserEntity);      
		when(jwtUtil.generateToken(testUserEntity.getId().toString())).thenReturn("mocked.jwt.token");     
		when(userRepository.save(any(UserEntity.class))).thenReturn(testUserEntity);  
		when(utilService.entityUserModel(testUserEntity)).thenReturn(testUserModel); 

		UserModel result = userService.registerUserService(testUserModel);

		assertNotNull(result);
		assertEquals(testUserModel.getEmail(), result.getEmail());
		assertEquals("mocked.jwt.token", testUserEntity.getToken());      
		verify(userRepository, times(1)).findByEmail(testUserModel.getEmail()); 
		verify(parameterRepository, times(1)).findByName("password_pattern");
		verify(utilService, times(1)).modelUserEntity(testUserModel);
		verify(jwtUtil, times(1)).generateToken(testUserEntity.getId().toString());
		verify(userRepository, times(1)).save(testUserEntity);
		verify(utilService, times(1)).entityUserModel(testUserEntity);
	}

	@Test
	void testRegisterUserServiceEmailAlreadyExists() {

		when(userRepository.findByEmail(testUserModel.getEmail())).thenReturn(Optional.of(new UserEntity()));

		BadDataException thrown = assertThrows(BadDataException.class, () -> {
			userService.registerUserService(testUserModel);
		});

		assertEquals("Ya existe un cliente con ese email", thrown.getMessage());
		verify(userRepository, times(1)).findByEmail(testUserModel.getEmail());
		verify(parameterRepository, never()).findByName(any(String.class));
		verify(userRepository, never()).save(any(UserEntity.class));
	}

	@Test
	void testRegisterUserServicePasswordInvalid() {

		when(userRepository.findByEmail(testUserModel.getEmail())).thenReturn(Optional.empty());	       
		when(parameterRepository.findByName("password_pattern")).thenReturn(Optional.of(passwordRegexParameter));      
		testUserModel.setPassword("short"); 

		BadDataException thrown = assertThrows(BadDataException.class, () -> {
			userService.registerUserService(testUserModel);
		});
		assertEquals("el password no cumple con la expresion definida", thrown.getMessage());

		verify(userRepository, times(1)).findByEmail(testUserModel.getEmail());
		verify(parameterRepository, times(1)).findByName("password_pattern");	      
		verify(utilService, never()).modelUserEntity(any(UserModel.class));
		verify(userRepository, never()).save(any(UserEntity.class));
	}

	@Test
	void testRegisterUserServicePasswordPatternNotFound() {

		when(userRepository.findByEmail(testUserModel.getEmail())).thenReturn(Optional.empty());		
		when(parameterRepository.findByName("password_pattern")).thenReturn(Optional.empty());

		BusinessException thrown = assertThrows(BusinessException.class, () -> {
			userService.registerUserService(testUserModel);
		});

		assertEquals("error consultando la expresión del password", thrown.getMessage());		
		verify(userRepository, times(1)).findByEmail(testUserModel.getEmail());
		verify(parameterRepository, times(1)).findByName("password_pattern");
		verify(utilService, never()).modelUserEntity(any(UserModel.class));
		verify(userRepository, never()).save(any(UserEntity.class));
	}

	@Test
	void testValidateUserServiceSuccess() {

		when(userRepository.findByIdAndPassword(testLoginModel.getId(), testLoginModel.getPassword()))
		.thenReturn(Optional.of(testUserEntity));

		when(jwtUtil.generateToken(testLoginModel.getId().toString())).thenReturn("new.generated.token");
		when(userRepository.save(any(UserEntity.class))).thenReturn(testUserEntity);

		LoginModel result = userService.validateUserService(testLoginModel);

		assertNotNull(result);
		assertEquals("new.generated.token", result.getToken());
		assertNotNull(testUserEntity.getLastlogin()); 
		assertEquals(null, result.getPassword()); 		
	}

	@Test
	void testValidateUserServiceUserNotFound() {
		
		when(userRepository.findByIdAndPassword(testLoginModel.getId(), testLoginModel.getPassword()))
		.thenReturn(Optional.empty());
		
		BusinessException thrown = assertThrows(BusinessException.class, () -> {
			userService.validateUserService(testLoginModel);
		});

		assertEquals("El usuario No existe", thrown.getMessage());		
		verify(userRepository, times(1)).findByIdAndPassword(testLoginModel.getId(), testLoginModel.getPassword());
		verify(jwtUtil, never()).generateToken(any(String.class)); 
		verify(userRepository, never()).save(any(UserEntity.class)); 
	}

}
