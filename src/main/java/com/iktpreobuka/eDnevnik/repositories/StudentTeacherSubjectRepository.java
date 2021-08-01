package com.iktpreobuka.eDnevnik.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.eDnevnik.entities.StudentEntity;
import com.iktpreobuka.eDnevnik.entities.StudentTeacherSubjectEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherSubjectEntity;

public interface StudentTeacherSubjectRepository extends CrudRepository<StudentTeacherSubjectEntity, Integer> {
	
	Boolean existsByStudentAndTeacherSubject(StudentEntity student, TeacherSubjectEntity teacherSubject);
	
	StudentTeacherSubjectEntity findByStudentAndTeacherSubject(StudentEntity student, TeacherSubjectEntity teacherSubject);

	List<StudentTeacherSubjectEntity> findByStudent(StudentEntity student);
	
	List<StudentTeacherSubjectEntity> findByTeacherSubject(TeacherSubjectEntity teacherSubject);
}
