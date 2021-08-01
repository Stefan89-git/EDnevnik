package com.iktpreobuka.eDnevnik.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.eDnevnik.entities.RoleEntity;

public interface RoleRepository extends CrudRepository<RoleEntity, Integer> {

	RoleEntity findByName(String name);
	
}
