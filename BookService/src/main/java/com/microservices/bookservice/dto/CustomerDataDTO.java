package com.microservices.bookservice.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

public @Data class CustomerDataDTO {

	//@Pattern(regexp = "^[A-Z]{1}[a-z A-Z\\s]{2,}$", message = "Address Invalid")
	@NotBlank(message = "Address cannot be null")
	private String address;
	
	@NotBlank(message = "City cannot be null")
	private String city;
	
	private String landmark;
	
	@NotBlank(message = "State cannot be null")
	private String state;
	
	@NotBlank(message = "Pincode cannot be null")
	private String pincode;
		
}
