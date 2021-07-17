package com.microservices.userservices.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

public @Data class UserDataDTO {

	@Pattern(regexp = "^[A-Z]{1}[a-z A-Z\\s]{2,}$", message = "Person Name Invalid")
	@NotBlank(message = "Name cannot be null")
	private String fullName;
	
	@Pattern(regexp = "^[A-Z a-z 0-9]+([._+-][0-9 a-z A-Z]+)*@[0-9 a-z A-Z]+.[a-z A-Z]{2,3}([.][a-z A-Z]{2})*$", message = "EmailId is Invalid")
	@NotBlank(message = "EmailId cannot be null")
	public String emailId;
	
	@Pattern(regexp = "^(?=[0-9 A-Z a-z !@#$%^&*();:]{8,}$)(?=.*[A-Z]{1,})(?=.*[0-9]{1,})(?=.*[!@#$%^&*();:]{1,}).*$", message = "Password is Invalid")
	@NotBlank(message = "Password cannot be null")
	private String password;
	

	@Pattern(regexp = "^[+][0-9]{1,}[1-9]{1}[0-9]{9}$", message = "PhoneNumber is Invalid")
	@NotBlank(message = "PhoneNumber cannot be null")
	private String phoneNumber;
}
