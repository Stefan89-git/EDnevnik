package com.iktpreobuka.eDnevnik.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

@Entity
@Table(name = "parent")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class ParentEntity extends UserEntity{

	
	@OneToMany(mappedBy = "parent", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonBackReference
	private List<StudentEntity> children;
	
	@NotNull
	@Column
	private String email;
	
	
	
	public ParentEntity() {
		super();
	}

	public List<StudentEntity> getChildren() {
		return children;
	}

	public void setChildren(List<StudentEntity> children) {
		this.children = children;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	
}
