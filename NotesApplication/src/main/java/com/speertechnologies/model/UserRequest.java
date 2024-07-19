package com.speertechnologies.model;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRequest {

	//@NotNull(message = "userName should not be null")
	@NotEmpty
	private String userName;
	
	//@NotNull(message = "password should not be null")
	@NotEmpty
	private String password;
}
