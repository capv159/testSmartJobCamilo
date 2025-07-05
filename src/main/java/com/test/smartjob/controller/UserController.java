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
import com.test.smartjob.model.UserModel;
import com.test.smartjob.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@Validated
@RequestMapping("api/v1/user")
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	UserService userService;

	Logger log = LoggerFactory.getLogger(UserController.class);

	@Operation(summary = "servicio que registra un usuario", responses = {
			@ApiResponse(responseCode = "200", description = "registro exitoso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserModel.class))), 
			@ApiResponse(responseCode = "400", description = "bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
			@ApiResponse(responseCode = "500", description = "error de negocio", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
	})
	@PostMapping(value = "/register")
	public ResponseEntity<UserModel> registerUserController(@Valid @RequestBody UserModel userModel) {

		log.info("registerUserController: " + userModel.toString());

		return new ResponseEntity<>(userService.registerUserService(userModel), HttpStatus.OK);
	}

}
