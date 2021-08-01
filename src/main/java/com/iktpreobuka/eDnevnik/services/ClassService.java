package com.iktpreobuka.eDnevnik.services;

import com.iktpreobuka.eDnevnik.entities.ClassEntity;

public interface ClassService {

	public ClassEntity createClassroom (ClassEntity classroom);
	
	public ClassEntity changeClassroom (Integer classId, ClassEntity classroom);
	
	public ClassEntity deleteClassroom (Integer classId);
}
