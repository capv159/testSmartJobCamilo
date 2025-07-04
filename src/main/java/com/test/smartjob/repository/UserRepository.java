package com.test.smartjob.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.test.smartjob.entity.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, UUID> {
	
	Optional<UserEntity> findByEmail(String email);
	
	Optional<UserEntity> findByIdAndPassword(UUID id, String password);

}
