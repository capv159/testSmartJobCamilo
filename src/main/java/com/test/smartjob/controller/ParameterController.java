package com.test.smartjob.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.smartjob.exception.BadDataException;
import com.test.smartjob.model.ApiError;
import com.test.smartjob.model.ParameterModel;
import com.test.smartjob.service.ParameterService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Validated
@RequestMapping("api/v1/parameter")
@CrossOrigin(origins = "*")
@Tag(description = "Servicios de gestion de parametros", name = " LoginController")
public class ParameterController {

	@Autowired
	ParameterService parameterService;

	Logger log = LoggerFactory.getLogger(UserController.class);

	@Operation(summary = "servicio que actualizar un parametro existente", responses = {
			@ApiResponse(responseCode = "200", description = "actualizacion exitosa", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ParameterModel.class))), 
			@ApiResponse(responseCode = "400", description = "bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
			@ApiResponse(responseCode = "500", description = "error de negocio", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
	})
	@PutMapping(value = "/expresion")
	public ResponseEntity<ParameterModel> updateParameterController(@Valid @RequestBody ParameterModel parameterModel) {

		log.info("updateParameterController: " + parameterModel.toString());

		return new ResponseEntity<>(parameterService.updateParameter(parameterModel), HttpStatus.OK);
	}

	@Operation(summary = "servicio que consulta un parametro existente", responses = {
			@ApiResponse(responseCode = "200", description = "consulta exitosa", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ParameterModel.class))), 
			@ApiResponse(responseCode = "400", description = "bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
			@ApiResponse(responseCode = "500", description = "error de negocio", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
	})
	@GetMapping(value = "/expresion")
	public ResponseEntity<ParameterModel> consultParameterController(@RequestParam String name) {

		if (name == null || name.isBlank()) {
			throw new BadDataException("'name' no puede ser vacio");
		}

		log.info("consultParameterController: " + name);

		return new ResponseEntity<>(parameterService.consultParameter(name), HttpStatus.OK);
	}

}
