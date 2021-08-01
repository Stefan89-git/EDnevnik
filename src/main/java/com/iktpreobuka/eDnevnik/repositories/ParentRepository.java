package com.iktpreobuka.eDnevnik.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.eDnevnik.entities.ParentEntity;

public interface ParentRepository extends CrudRepository<ParentEntity, Integer> {

	Boolean existsByUsername(String username);
	
	ParentEntity findByUsername(String username);
	
}
