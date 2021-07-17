package com.microservices.userservices.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public @Data class ResponseDTO {

	private String message;
	private Object data;
	
	public ResponseDTO(String message, Object data) {
		this.message = message;
		this.data = data;
	}
}
