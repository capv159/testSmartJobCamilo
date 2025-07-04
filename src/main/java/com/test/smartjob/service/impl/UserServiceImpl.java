package com.test.smartjob.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.smartjob.entity.ParameterEntity;
import com.test.smartjob.entity.UserEntity;
import com.test.smartjob.exception.BadDataException;
import com.test.smartjob.exception.BusinessException;
import com.test.smartjob.model.LoginModel;
import com.test.smartjob.model.UserModel;
import com.test.smartjob.repository.ParameterRepository;
import com.test.smartjob.repository.UserRepository;
import com.test.smartjob.security.JwtUtil;
import com.test.smartjob.service.UserService;
import com.test.smartjob.util.UtilService;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	ParameterRepository parameterRepository;

	@Autowired
	UtilService utilService;

	@Autowired
	JwtUtil jwtUtil;

	@Override
	public UserModel registerUserService(UserModel userModel) {

		if (validateEmailUser(userModel)) {

			if (validatePasswordUser(userModel)) {

				UserEntity userEntity = utilService.modelUserEntity(userModel);
				userEntity.setToken(jwtUtil.generateToken(userEntity.getId().toString()));
				userRepository.save(userEntity);
				return utilService.entityUserModel(userEntity);
			} else {
				throw new BadDataException("el password no cumple con la expresion definida");
			}
		}

		throw new BusinessException("error registrando un usuario");
	}

	@Override
	public LoginModel validateUserService(LoginModel loginModel) {

		Optional<UserEntity> optionalUser = userRepository.findByIdAndPassword(loginModel.getId(),
				loginModel.getPassword());

		if (optionalUser.isPresent()) {

			String token = jwtUtil.generateToken(loginModel.getId().toString());

			UserEntity UserEntity = optionalUser.get();
			UserEntity.setToken(token);
			UserEntity.setLastlogin(LocalDateTime.now());
			userRepository.save(UserEntity);

			loginModel.setToken(token);
			loginModel.setPassword(null);
			return loginModel;

		}

		throw new BusinessException("El usuario No existe");

	}

	private boolean validateEmailUser(UserModel userModel) {

		Optional<UserEntity> optionalUser = userRepository.findByEmail(userModel.getEmail());

		if (optionalUser.isPresent()) {
			throw new BadDataException("Ya existe un cliente con ese email");
		}

		return true;
	}

	private boolean validatePasswordUser(UserModel userModel) {

		Optional<ParameterEntity> optionalParameter = parameterRepository.findByName("password_pattern");

		if (optionalParameter.isPresent()) {

			log.info("password:" + userModel.getPassword());
			log.info("expresion:" + optionalParameter.get().getValueparameter());

			return userModel.getPassword().matches(optionalParameter.get().getValueparameter());

		}

		throw new BusinessException("error consultando la expresión del password");
	}

}
