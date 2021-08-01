package com.iktpreobuka.eDnevnik.entities.dto;

import com.iktpreobuka.eDnevnik.entities.ClassEntity;
import com.iktpreobuka.eDnevnik.entities.ParentEntity;

public class StudentDTO extends UserDTO{

	private ParentEntity parent;
	
	private ClassEntity classroom;

	public StudentDTO() {
		super();
	}

	public ParentEntity getParent() {
		return parent;
	}

	public void setParent(ParentEntity parent) {
		this.parent = parent;
	}

	public ClassEntity getClassroom() {
		return classroom;
	}

	public void setClassroom(ClassEntity classroom) {
		this.classroom = classroom;
	}
	
}
