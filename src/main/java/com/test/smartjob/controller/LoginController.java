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

import com.test.smartjob.model.LoginModel;
import com.test.smartjob.service.UserService;

import jakarta.validation.Valid;

@RestController
@Validated
@RequestMapping("api/login")
@CrossOrigin(origins = "*")
public class LoginController {

	@Autowired
	UserService userService;

	Logger log = LoggerFactory.getLogger(UserController.class);

	@PostMapping
	public ResponseEntity<LoginModel> login(@Valid @RequestBody LoginModel loginModel) {

		log.info("login: " + loginModel.toString());

		return new ResponseEntity<>(userService.validateUserService(loginModel), HttpStatus.OK);
	}

}
