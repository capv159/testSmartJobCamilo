package com.test.smartjob.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.test.smartjob.model.ApiError;


/**
 * 
 * clase que gestiona de forma globalla excepciones generadas en cualquier parte del servicios
 * */
@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError> handleValidationExceptions(MethodArgumentNotValidException ex) {
		MultiValueMap<String, String> errors = new LinkedMultiValueMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.add(fieldName, errorMessage);
		});

		log.error(errors.toString());
		return new ResponseEntity<>(ApiError.builder().message(errors.toString()).build(), HttpStatus.BAD_REQUEST);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BadDataException.class)
	public ResponseEntity<ApiError> badDataExceptionExceptions(BadDataException ex) {
		log.error(ex.getMessage());
		return new ResponseEntity<>(ApiError.builder().message(ex.getMessage()).build(), HttpStatus.BAD_REQUEST);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ApiError> businessExceptionExceptions(BusinessException ex) {
		log.error(ex.getMessage());
		return new ResponseEntity<>(ApiError.builder().message(ex.getMessage()).build(),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
