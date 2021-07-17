package com.microservices.bookservice.model;

import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.microservices.bookservice.dto.CustomerDataDTO;

import lombok.Data;

@Entity
@Table(name="customer_data")
public @Data class CustomerData {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(type="uuid-char")
	private UUID customerId;
	
	private String address;
	private String city;
	private String landmark;
	private String state;
	private String pincode;
	
	//@JsonIgnore
        @Type(type="uuid-char")
	private UUID userId;
	
        @OneToMany(mappedBy = "customer")
        List<OrderData> orderDataList;
    
	public CustomerData() {}
	
	public CustomerData(CustomerDataDTO customerdto) {
		this.address = customerdto.getAddress();
		this.city = customerdto.getCity();
		this.landmark = customerdto.getLandmark();
		this.state = customerdto.getState();
		this.pincode = customerdto.getPincode();
	}

	public CustomerData(String address, String city, String landmark, String state, String pincode, UUID userId) {
		this.address = address;
		this.city = city;
		this.landmark = landmark;
		this.state = state;
		this.pincode = pincode;
		this.userId = userId;
	}

	public CustomerData get(int i) {
		return null;
	}

}
