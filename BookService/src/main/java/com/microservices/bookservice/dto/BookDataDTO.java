package com.microservices.bookservice.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

public @Data class BookDataDTO {

	@NotBlank(message = "Book Name cannot be null")
	private String bookname;
	
	@Pattern(regexp = "^[A-Z]{1}[a-z A-Z\\s]{2,}$", message = "Author Name Invalid")
	@NotBlank(message = "Author Name cannot be null")
	private String author;
	
	
	private double ratings;
	
	@Min(value = 1, message = "Book Price cannot be 0")
	private double price;
	
	@Min(value = 0)
	private int quantity;
	
}	
