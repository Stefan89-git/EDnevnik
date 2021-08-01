package com.iktpreobuka.eDnevnik.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "subjects")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class SubjectEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column
	@NotBlank(message = "Subject name must not be null.")
	private String name;
	
	@Column
	private Integer weeklyFond;
	
	@Column
	@JsonIgnore
	private Boolean isActive;
	
	@OneToMany(mappedBy = "subject", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonBackReference
	private List<TeacherSubjectEntity> teacherSubject;

	public SubjectEntity() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getWeeklyFond() {
		return weeklyFond;
	}

	public void setWeeklyFond(Integer weeklyFond) {
		this.weeklyFond = weeklyFond;
	}

	public List<TeacherSubjectEntity> getTeacherSubject() {
		return teacherSubject;
	}

	public void setTeacherSubject(List<TeacherSubjectEntity> teacherSubject) {
		this.teacherSubject = teacherSubject;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	

	
}
