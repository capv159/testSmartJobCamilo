package com.test.smartjob.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PhoneModel {

	@NotNull
	@NotEmpty
	@Size(min = 2, max = 10)
	private String number;
	@NotNull
	@NotEmpty
	@Size(min = 2, max = 10)
	private String citycode;
	@NotNull
	@NotEmpty
	@Size(min = 2, max = 10)
	private String countryCode;
}
