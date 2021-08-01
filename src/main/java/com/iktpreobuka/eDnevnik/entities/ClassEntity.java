package com.iktpreobuka.eDnevnik.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "class")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class ClassEntity {

	@Id
	@GeneratedValue
	private Integer id;
	
	@Min(value = 1, message = "School year must be grater then 1")
	@Max(value = 8, message = "School year must me less then 8")
	private Integer schoolYear;
	
	@Min(value = 1, message = "Class number must be grater then 1")
	@Max(value = 6, message = "Class number must me less then 6")
	private Integer classNumber;
	
	@OneToMany(mappedBy = "attendingClass", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonBackReference
	private List<StudentEntity> students;

	public ClassEntity() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(Integer schoolYear) {
		this.schoolYear = schoolYear;
	}

	public Integer getClassNumber() {
		return classNumber;
	}

	public void setClassNumber(Integer classNumber) {
		this.classNumber = classNumber;
	}

	public List<StudentEntity> getStudents() {
		return students;
	}

	public void setStudents(List<StudentEntity> students) {
		this.students = students;
	}
	
}
