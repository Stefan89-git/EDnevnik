package com.iktpreobuka.eDnevnik.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.eDnevnik.entities.AdminEntity;

public interface AdminRepository extends CrudRepository<AdminEntity, Integer> {

	Boolean existsByUsername(String username);
	
	AdminEntity findByUsername(String username);
}
