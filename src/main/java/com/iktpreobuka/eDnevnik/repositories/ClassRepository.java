package com.iktpreobuka.eDnevnik.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.eDnevnik.entities.ClassEntity;

public interface ClassRepository extends CrudRepository<ClassEntity, Integer>{

	Boolean existsByClassNumberAndSchoolYear(Integer classNumber, Integer schoolYear);
}
