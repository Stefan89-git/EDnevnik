package com.iktpreobuka.eDnevnik.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.iktpreobuka.eDnevnik.entities.dto.ParentDTO;
import com.iktpreobuka.eDnevnik.repositories.UserRepository;

@Component
public class ParentCustomValidator implements Validator{

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return ParentDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ParentDTO parent = (ParentDTO) target;
		if(!parent.getPassword().equals(parent.getRepetedPass())) {
			errors.reject("400", "Password must match.");
		}else if(userRepo.existsByUsername(parent.getUsername())) {
			errors.reject("400", "Username already exists.");
		}
	}

}
