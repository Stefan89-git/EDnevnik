package com.iktpreobuka.eDnevnik.entities.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ParentDTO extends UserDTO{

	@NotNull
	@Size(min = 6, max = 30, message = "Email must have between {min} and {max} characters.")
	private String email;
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	
	
}
