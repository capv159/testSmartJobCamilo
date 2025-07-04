package com.test.smartjob.service;

import com.test.smartjob.model.ParameterModel;

public interface ParameterService {

	public ParameterModel updateParameter(ParameterModel parameterModel);
	
	public ParameterModel consultParameter(String name);

}
