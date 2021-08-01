package com.iktpreobuka.eDnevnik.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.iktpreobuka.eDnevnik.entities.dto.TeacherDto;
import com.iktpreobuka.eDnevnik.repositories.UserRepository;

@Component
public class TeacherCustomValidator implements Validator{

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return TeacherDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		TeacherDto teacher = (TeacherDto) target;
		if(!teacher.getPassword().equals(teacher.getRepetedPass())) {
			errors.reject("400", "Password must match.");
		}else if(userRepo.existsByUsername(teacher.getUsername())) {
			errors.reject("400", "Username already exists.");
		}
		
	}

}
