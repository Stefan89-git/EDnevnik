package com.iktpreobuka.eDnevnik.services;

import com.iktpreobuka.eDnevnik.entities.SubjectEntity;
import com.iktpreobuka.eDnevnik.entities.dto.SubjectDTO;

public interface SubjectService {

	public SubjectEntity createSubject(SubjectDTO newSubject);
	
	public SubjectEntity changeSubject(Integer subjectId, SubjectDTO newSubject);
	
	public SubjectEntity deleteSubject(Integer subjectId);
}
