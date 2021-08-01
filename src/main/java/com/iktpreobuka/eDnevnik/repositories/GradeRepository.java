package com.iktpreobuka.eDnevnik.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.eDnevnik.entities.GradeEntity;
import com.iktpreobuka.eDnevnik.entities.StudentTeacherSubjectEntity;

public interface GradeRepository extends CrudRepository<GradeEntity, Integer> {

	public List<GradeEntity> findByStudentTeacherSubject(StudentTeacherSubjectEntity stse);
}
