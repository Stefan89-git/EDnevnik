package com.iktpreobuka.eDnevnik.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "teachers")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class TeacherEntity extends UserEntity{

	@OneToMany(mappedBy = "teacher", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonBackReference
	private List<TeacherSubjectEntity> teacherSubject;

	public TeacherEntity() {
		super();
	}

	public List<TeacherSubjectEntity> getTeacherSubject() {
		return teacherSubject;
	}

	public void setTeacherSubject(List<TeacherSubjectEntity> teacherSubject) {
		this.teacherSubject = teacherSubject;
	}

	

}
