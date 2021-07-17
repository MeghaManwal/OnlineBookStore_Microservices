package com.microservices.userservices.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

public @Data class UserLoginDTO {

	@Pattern(regexp = "^[A-Z a-z 0-9]+([._+-][0-9 a-z A-Z]+)*@[0-9 a-z A-Z]+.[a-z A-Z]{2,3}([.][a-z A-Z]{2})*$", message = "EmailId is Invalid")
	@NotBlank(message = "EmailId cannot be null")
	public String emailId;
	
	@Pattern(regexp = "^(?=[0-9 A-Z a-z !@#$%^&*();:]{8,}$)(?=.*[A-Z]{1,})(?=.*[0-9]{1,})(?=.*[!@#$%^&*();:]{1,}).*$", message = "Password is Invalid")
	@NotBlank(message = "Password cannot be null")
	public String password;
}