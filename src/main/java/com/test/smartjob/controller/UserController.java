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

import com.test.smartjob.model.UserModel;
import com.test.smartjob.service.UserService;

import jakarta.validation.Valid;

@RestController
@Validated
@RequestMapping("api/v1/user")
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	UserService userService;

	Logger log = LoggerFactory.getLogger(UserController.class);

	@PostMapping(value = "/register")
	public ResponseEntity<UserModel> registerUserController(@Valid @RequestBody UserModel userModel) {

		log.info("registerUserController: " + userModel.toString());

		return new ResponseEntity<>(userService.registerUserService(userModel), HttpStatus.OK);
	}

}
