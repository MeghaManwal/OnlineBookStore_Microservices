package com.microservices.bookservice.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.bookservice.dto.ResponseDTO;
import com.microservices.bookservice.service.IOrderService;


@RestController
@RequestMapping("/order")
public class OrderDataController {
	
	@Autowired
	IOrderService orderservice;

	@PostMapping("/placeOrder/{customerId}")
	public ResponseEntity<ResponseDTO> placeNewOrder(@PathVariable UUID customerId,
			                                         @RequestParam Double totalprice,
			                                         @RequestHeader(value="token", required=false)String token) {
		String orderdetails=orderservice.placeAnOrder(totalprice,customerId, token);
		ResponseDTO respdto = new ResponseDTO("Your Order is Placed Successfully ", orderdetails);
		return new ResponseEntity<ResponseDTO>(respdto, HttpStatus.OK);
	}
}