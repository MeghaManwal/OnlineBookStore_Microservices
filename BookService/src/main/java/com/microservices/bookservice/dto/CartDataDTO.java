package com.microservices.bookservice.dto;

import java.util.UUID;

import javax.validation.constraints.Min;

import lombok.Data;

public @Data class CartDataDTO {
	
	private UUID bookId;
	
	@Min(value = 1, message = "Please Enter Valid Quantity")
	private Integer quantity;
	
	@Min(value = 1, message = "Please Enter Valid Quantity")
	private Double totalprice;

	public CartDataDTO(UUID bookId,  Integer quantity,  Double totalprice) {
		this.bookId = bookId;
		this.quantity = quantity;
		this.totalprice = totalprice;
	}

	public CartDataDTO() {
		super();
	}
			
}

