package com.microservices.bookservice.dto;

import java.util.UUID;

import lombok.Data;

public @Data class UpdateCartDTO extends CartDataDTO {

	private UUID bookId;
	private Integer quantity;
	private Double totalprice;
	
}
