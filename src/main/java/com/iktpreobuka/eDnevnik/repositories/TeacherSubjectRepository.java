package com.iktpreobuka.eDnevnik.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.eDnevnik.entities.SubjectEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherSubjectEntity;

public interface TeacherSubjectRepository extends CrudRepository<TeacherSubjectEntity, Integer> {

	Boolean existsByTeacherAndSubject(TeacherEntity teacher, SubjectEntity subject);
	
	TeacherSubjectEntity findByTeacherAndSubject(TeacherEntity teacher, SubjectEntity subject);
	
	List<TeacherSubjectEntity> findByTeacher(TeacherEntity teacher);
	
	List<TeacherSubjectEntity> findBySubject(SubjectEntity subject);
	
}
