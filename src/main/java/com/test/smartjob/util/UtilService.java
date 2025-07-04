package com.test.smartjob.util;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.test.smartjob.entity.PhoneEntity;
import com.test.smartjob.entity.UserEntity;
import com.test.smartjob.model.UserModel;

@Component
public class UtilService {

	public UserEntity modelUserEntity(UserModel userModel) {

		UserEntity userEntity = new UserEntity();
		userEntity.setEmail(userModel.getEmail());
		userEntity.setId(UUID.randomUUID());
		userEntity.setIsactive(true);
		userEntity.setModified(LocalDateTime.now());
		userEntity.setCreated(LocalDateTime.now());
		userEntity.setLastlogin(LocalDateTime.now());
		userEntity.setName(userModel.getName());
		userEntity.setPassword(userModel.getPassword());		

		List<PhoneEntity> phones = userModel.getPhones().stream().map(t -> {
			PhoneEntity phoneEntity = new PhoneEntity();
			phoneEntity.setNumber(t.getNumber());
			phoneEntity.setCitycode(t.getCitycode());
			phoneEntity.setCountryCode(t.getCountryCode());
			phoneEntity.setUserId(userEntity);
			return phoneEntity;
		}).toList();

		userEntity.setPhones(phones);

		return userEntity;
	}

	public UserModel entityUserModel(UserEntity userEntity) {

		UserModel userModel = new UserModel();
		userModel.setId(userEntity.getId());
		userModel.setCreated(userEntity.getCreated());;
		userModel.setModified(userEntity.getModified());
		userModel.setLastlogin(userEntity.getLastlogin());
		userModel.setToken(userEntity.getToken());
		userModel.setIsactive(userEntity.isIsactive());
		
		return userModel;
	}

}
