package com.iktpreobuka.eDnevnik.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.iktpreobuka.eDnevnik.entities.dto.StudentDTO;
import com.iktpreobuka.eDnevnik.repositories.UserRepository;

@Component
public class StudentCustomValidator implements Validator{

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return StudentDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		StudentDTO student = (StudentDTO) target;
		if(!student.getPassword().equals(student.getRepetedPass())) {
			errors.reject("400", "Password must match.");
		}else if(userRepo.existsByUsername(student.getUsername())) {
			errors.reject("400", "Username already exists.");
		}
		
	}

}
