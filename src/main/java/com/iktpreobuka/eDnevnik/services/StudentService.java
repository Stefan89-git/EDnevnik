package com.iktpreobuka.eDnevnik.services;

import com.iktpreobuka.eDnevnik.entities.StudentEntity;
import com.iktpreobuka.eDnevnik.entities.StudentTeacherSubjectEntity;
import com.iktpreobuka.eDnevnik.entities.dto.StudentDTO;

public interface StudentService {

	public StudentEntity createStudent(StudentDTO newStudent, Integer parentId, Integer classId);
	
	public StudentEntity changeStudent(Integer studentId, StudentDTO newStudent);
	
	public StudentEntity changeClassroomForStudent(Integer studentId, Integer classId);
	
	public StudentEntity deleteStudent(Integer studentId);
	
	public StudentTeacherSubjectEntity addTeacherAndSubjectToStudent(Integer studentId, Integer teacherId, 
																	 Integer subjectId);
}
