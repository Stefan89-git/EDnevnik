package com.iktpreobuka.eDnevnik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.eDnevnik.entities.RoleEntity;
import com.iktpreobuka.eDnevnik.repositories.RoleRepository;

@RestController
@RequestMapping(path = "/api/v1/project")
public class RoleController {

	@Autowired
	private RoleRepository roleRepository;
	
	@RequestMapping(method = RequestMethod.POST, value = "/roles")
	public RoleEntity createRole(@RequestBody RoleEntity role) {
		return roleRepository.save(role);
	}
}
