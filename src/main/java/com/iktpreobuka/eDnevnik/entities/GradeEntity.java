package com.iktpreobuka.eDnevnik.entities;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "grade")
public class GradeEntity {

	@Id
	@GeneratedValue
	private Integer id;
	
	@NotNull
	@Min(value = 1)
	@Max(value = 5)
	private Integer value;
	
	@Column
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy")
	private LocalDate date;
	
	@Column
	private Boolean isActive;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "studentTeacherSubject")
	private StudentTeacherSubjectEntity studentTeacherSubject;

	public GradeEntity() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	

	public Boolean isActive() {
		return isActive;
	}

	public void setActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public StudentTeacherSubjectEntity getStudentTeacherSubject() {
		return studentTeacherSubject;
	}

	public void setStudentTeacherSubject(StudentTeacherSubjectEntity studentTeacherSubject) {
		this.studentTeacherSubject = studentTeacherSubject;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	
	
}
