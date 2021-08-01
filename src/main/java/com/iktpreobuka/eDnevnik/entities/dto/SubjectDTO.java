package com.iktpreobuka.eDnevnik.entities.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class SubjectDTO {

	@Size(min = 5, max = 30, message = "Subject name must be between {min} and {max} characters.")
	private String name;
	
	@Min(value = 1, message = "Fond must be grater then 1")
	@Max(value = 10, message = "Fond must me less then 10")
	private Integer weeklyFond;

	public SubjectDTO() {
		super();
		// TODO Auto-generated constructor stub
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
	
	
}
