package com.iktpreobuka.eDnevnik.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "student_teacher_subject")
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
public class StudentTeacherSubjectEntity {

	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "student")
	private StudentEntity student;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "teacherSubject")
	private TeacherSubjectEntity teacherSubject;
	
	@OneToMany(mappedBy = "studentTeacherSubject", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonBackReference
	private List<GradeEntity> grades;

	public StudentTeacherSubjectEntity() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public StudentEntity getStudent() {
		return student;
	}

	public void setStudent(StudentEntity student) {
		this.student = student;
	}

	public TeacherSubjectEntity getTeacherSubject() {
		return teacherSubject;
	}

	public void setTeacherSubject(TeacherSubjectEntity teacherSubject) {
		this.teacherSubject = teacherSubject;
	}

	public List<GradeEntity> getGrades() {
		return grades;
	}

	public void setGrades(List<GradeEntity> grades) {
		this.grades = grades;
	}
	
}
