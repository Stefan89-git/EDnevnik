package com.iktpreobuka.eDnevnik.services;

import java.util.List;

import com.iktpreobuka.eDnevnik.entities.GradeEntity;
import com.iktpreobuka.eDnevnik.entities.StudentTeacherSubjectEntity;

public interface GradeService {

	public GradeEntity crateGradeByAdmin(Integer studentId, Integer teacherSubjectId, GradeEntity grade);
	
	public GradeEntity createGradeByTeacher(Integer studentId, Integer subjectId, GradeEntity newGrade);
	
	public GradeEntity changeGrade(Integer gradeId, GradeEntity newGrade);
	
	public GradeEntity deleteGrade(Integer gradeId);
	
	public List<GradeEntity> getAllGradesByAdmin();
	
	public List<GradeEntity> getAllGradesByTeacher();
	
	public List<GradeEntity> getAllGradesByStudent();
	
	public List<GradeEntity> getAllGradesByParent();
}
