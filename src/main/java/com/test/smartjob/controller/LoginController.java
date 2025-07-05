package com.test.smartjob.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.smartjob.model.ApiError;
import com.test.smartjob.model.LoginModel;
import com.test.smartjob.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Validated
@RequestMapping("api/login")
@CrossOrigin(origins = "*")
@Tag(description = "Servicios de Login", name = " LoginController")
public class LoginController {

	@Autowired
	UserService userService;

	Logger log = LoggerFactory.getLogger(UserController.class);

	@Operation(summary = "servicio que permite hacer login por medio de id y password previamente registrado", responses = {
			@ApiResponse(responseCode = "200", description = "login exitoso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginModel.class))), 
			@ApiResponse(responseCode = "400", description = "bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
	})
	@PostMapping
	public ResponseEntity<LoginModel> login(@Valid @RequestBody LoginModel loginModel) {

		log.info("login: " + loginModel.toString());

		return new ResponseEntity<>(userService.validateUserService(loginModel), HttpStatus.OK);
	}

}
