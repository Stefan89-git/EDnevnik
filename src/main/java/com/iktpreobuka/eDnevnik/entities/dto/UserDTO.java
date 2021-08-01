package com.iktpreobuka.eDnevnik.entities.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDTO {

	@NotNull
	@Size(min = 2, max = 30, message = "Name must have between {min} and {max} characters.")
	private String name;
	
	@NotBlank
	@Size(min = 2, max = 30, message = "Last name must have between {min} and {max} characters.")
	private String lastName;
	
	@NotNull
	@Size(min = 6, max = 30, message = "Username must have between {min} and {max} characters.")
	private String username;
	
	@Size(min = 6, max = 30, message = "Password must have between {min} and {max} characters.")
	private String password;
	
	@Size(min = 6, max = 30, message = "Password must have between {min} and {max} characters.")
	private String repetedPass;

	public UserDTO() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRepetedPass() {
		return repetedPass;
	}

	public void setRepetedPass(String repetedPass) {
		this.repetedPass = repetedPass;
	}
	
}
