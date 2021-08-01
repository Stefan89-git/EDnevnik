package com.iktpreobuka.eDnevnik.entities.dto;

public class UserLogin {

	private String user;
	
	private String token;

	public UserLogin(String user, String token) {
		super();
		this.user = user;
		this.token = token;
	}

	public UserLogin() {
		super();
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
