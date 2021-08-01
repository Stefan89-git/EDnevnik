package com.iktpreobuka.eDnevnik.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.eDnevnik.entities.ClassEntity;
import com.iktpreobuka.eDnevnik.repositories.ClassRepository;
import com.iktpreobuka.eDnevnik.validation.Validation;

@Service
public class ClassServiceImpl implements ClassService{

	@Autowired
	private ClassRepository classRepo;
	
	@Override
	public ClassEntity createClassroom(ClassEntity classroom) {
		ClassEntity classr = new ClassEntity();
		classr.setClassNumber(classroom.getClassNumber());
		classr.setSchoolYear(classroom.getSchoolYear());
		return classRepo.save(classr);
	}

	@Override
	public ClassEntity changeClassroom(Integer classId, ClassEntity classroom) {
		if(!classRepo.existsById(classId)) {
			return null;
		}
		ClassEntity classr = classRepo.findById(classId).get();
		classr.setClassNumber(Validation.setIfNotNull(classr.getClassNumber(), classroom.getClassNumber()));
		classr.setSchoolYear(Validation.setIfNotNull(classr.getSchoolYear(), classroom.getSchoolYear()));
		return classRepo.save(classr);
	}

	@Override
	public ClassEntity deleteClassroom(Integer classId) {
		if(!classRepo.existsById(classId)) {
			return null;
		}
		ClassEntity classr = classRepo.findById(classId).get();
		classRepo.delete(classr);
		return classr;
	}

}
