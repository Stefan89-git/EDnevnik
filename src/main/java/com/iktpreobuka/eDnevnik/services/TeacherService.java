package com.iktpreobuka.eDnevnik.services;

import java.util.List;

import com.iktpreobuka.eDnevnik.entities.SubjectEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherSubjectEntity;
import com.iktpreobuka.eDnevnik.entities.dto.TeacherDto;

public interface TeacherService {

	public TeacherEntity createTeacher(TeacherDto newTeacher);
	
	public TeacherEntity changeTeacher(Integer teacherId, TeacherDto newTeacher);
	
	public TeacherEntity deleteTeacher(Integer teacherId);
	
	public TeacherSubjectEntity addSubjectForTeacher(Integer teacherId, Integer subjectId);
	
	public List<SubjectEntity> getAllSubjetcsByTeacher(Integer teacherId);
	
	public List<SubjectEntity> getAllSubjetcsByTeacher(); 
}
