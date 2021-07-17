package com.microservices.bookservice.dto;

import java.util.UUID;

import javax.validation.constraints.NotBlank;

import lombok.Data;

public @Data class UpdateCustomerDTO {

	private UUID customerdataId; 
	
	private String address;
	
	private String city;
	
	private String landmark;
	
	private String state;
	
	private String pincode;
}

