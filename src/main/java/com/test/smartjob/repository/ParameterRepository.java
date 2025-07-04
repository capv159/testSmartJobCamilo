package com.test.smartjob.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.test.smartjob.entity.ParameterEntity;

public interface ParameterRepository extends CrudRepository<ParameterEntity, Long> {
	
	Optional<ParameterEntity> findByName(String name);
}
