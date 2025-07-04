package com.test.smartjob.service;

import com.test.smartjob.model.LoginModel;
import com.test.smartjob.model.UserModel;

public interface UserService {
	
	public UserModel registerUserService (UserModel userModel);
	
	public LoginModel validateUserService (LoginModel loginModel);

}
