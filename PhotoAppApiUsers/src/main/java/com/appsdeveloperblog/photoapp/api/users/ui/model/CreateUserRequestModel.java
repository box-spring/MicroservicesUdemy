package com.appsdeveloperblog.photoapp.api.users.ui.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateUserRequestModel {
	
	@NotNull(message="Firstname can not be null.")
	@Size(min=2,max=16,message="Firstname should contain between 2 and 16 characters.")
	private String firstName;
	
	@NotNull(message="Lastname can not be null.")
	@Size(min=2,max=16,message="Lastname should contain between 2 and 16 characters.")
	private String lastName;
	
	@NotNull(message="Password can not be null.")
	@Size(min=8,max=16,message="Password should contain between 2 and 16 characters.")
	private String password;
	
	@NotNull(message="Email can not be null.")
	@Email
	private String email;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

}
