package com.test.smartjob.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ApiError {

	private String message;

}
