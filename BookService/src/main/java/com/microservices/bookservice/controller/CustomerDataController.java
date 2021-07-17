package com.microservices.bookservice.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.bookservice.dto.CustomerDataDTO;
import com.microservices.bookservice.dto.ResponseDTO;
import com.microservices.bookservice.dto.UpdateCustomerDTO;
import com.microservices.bookservice.model.CustomerData;
import com.microservices.bookservice.service.ICustomerService;


@RestController
@RequestMapping("/customer")
public class CustomerDataController {

	@Autowired
	private ICustomerService customerservice;
	
	@PostMapping("/add")
	public ResponseEntity<ResponseDTO> addCustomerData(@Valid @RequestBody CustomerDataDTO customerdto,
			                                          @RequestHeader(value="token", required=false)String token) {
		CustomerData customerdata=customerservice.addNewCustomerDetails(customerdto, token);
		ResponseDTO respdto = new ResponseDTO("Customer Details Successfully Added", customerdata);
		return new ResponseEntity<ResponseDTO>(respdto, HttpStatus.OK);
	}
	
	@GetMapping("/view")
	public ResponseEntity<List<CustomerData>> displayAllCustomerDetails(@RequestHeader(value = "token") String Token) {
		List<CustomerData> customerList=customerservice.displayCustomerDetails(Token);
		return ResponseEntity.status(HttpStatus.OK).body(customerList);
	}
	
	@PutMapping("/update")
        public ResponseEntity updateCustomerDetails(@Valid @RequestBody UpdateCustomerDTO updatecustomerdto,
                                                    @RequestHeader(value = "token") String Token) {
                String msg = customerservice.updateCustomerData(updatecustomerdto, Token);
                ResponseDTO respdto = new ResponseDTO("Updating Customer details", msg);
		return new ResponseEntity<ResponseDTO>(respdto, HttpStatus.OK);
    }
	
}

