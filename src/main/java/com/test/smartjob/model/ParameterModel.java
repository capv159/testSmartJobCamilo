package com.test.smartjob.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ParameterModel {
	@NotNull
	@NotEmpty
	private String name;
	@NotNull
	@NotEmpty
	private String value;

}
