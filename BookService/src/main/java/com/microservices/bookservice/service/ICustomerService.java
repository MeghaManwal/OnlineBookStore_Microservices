package com.microservices.bookservice.service;

import java.util.List;

import com.microservices.bookservice.dto.CustomerDataDTO;
import com.microservices.bookservice.dto.UpdateCustomerDTO;
import com.microservices.bookservice.model.CustomerData;


public interface ICustomerService {

	public CustomerData addNewCustomerDetails(CustomerDataDTO customerdto, String token);
	public List<CustomerData> displayCustomerDetails(String token);
	public String updateCustomerData(UpdateCustomerDTO updatedto, String token);
}

