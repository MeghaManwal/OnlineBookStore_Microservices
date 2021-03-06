package com.microservices.bookservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservices.bookservice.dto.CustomerDataDTO;
import com.microservices.bookservice.dto.UpdateCustomerDTO;
import com.microservices.bookservice.exception.BookStoreException;
import com.microservices.bookservice.model.CustomerData;
import com.microservices.bookservice.model.UserData;
import com.microservices.bookservice.repository.CustomerDataRepository;
import com.microservices.bookservice.utils.Token;

@Service
public class CustomerService implements ICustomerService{

    Token jwtToken = new Token();
	
	@Autowired
	CustomerDataRepository customerdatarepository;
	
	@Autowired
        private RestTemplate restTemplate;
	
	@Override
	public CustomerData addNewCustomerDetails(CustomerDataDTO customerdto, String token) {
		UUID userId = jwtToken.decodeJWT(token);
		UserData findExistingUser = findByUserId(token);
		 
		 List<CustomerData> customerdatalist = new ArrayList<>();
		 CustomerData customerdata = new CustomerData(customerdto);
		 customerdata.setUserId(userId);
		 customerdatalist.add(customerdata);
		 
		 CustomerData data = customerdatarepository.save(customerdata); 
		 return data;
	}

	private UserData findByUserId(String userId) {
		UserData userdata = restTemplate.
                getForObject("http://localhost:8081/user/getuser?userEmailToken= "+userId,
                        UserData.class);

                if(userdata == null){
                          throw new BookStoreException(BookStoreException.ExceptionTypes.USER_NOT_FOUND);
                }

                return userdata;
	}

	@Override
	public List<CustomerData> displayCustomerDetails(String token) {
		UUID userId = jwtToken.decodeJWT(token);
		UserData findExistingUser = findByUserId(token);
		
		return customerdatarepository.findAll()
				.stream()
				.collect(Collectors.toList());
	}

	@Override
	public String updateCustomerData(UpdateCustomerDTO updatedto, String token) {
		UUID userId = jwtToken.decodeJWT(token);
	     
		UserData findExistingUser = findByUserId(token);
         
                CustomerData customerdata = customerdatarepository.findByCustomerId(updatedto.getCustomerdataId())
	             .orElseThrow(() -> new BookStoreException(BookStoreException.ExceptionTypes.CUSTOMER_NOT_FOUND));
         
                customerdata.setAddress(updatedto.getAddress());
                customerdata.setCity(updatedto.getCity());
                customerdata.setLandmark(updatedto.getLandmark());
                customerdata.setState(updatedto.getState());
                customerdata.setPincode(updatedto.getPincode());
                customerdatarepository.save(customerdata);
         
		return "Customer Data Updated Successfully";
	}

}

