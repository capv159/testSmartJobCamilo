package com.test.smartjob.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class LoginModel {
	
	@NotNull
	private UUID id;
	
	@NotNull
	@NotEmpty
	@Size(min = 5, max = 30)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String password;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String Token;

}
