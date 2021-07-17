package com.microservices.bookservice.model;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
public @Data class OrderData {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(type="uuid-char")
	private UUID id;
	
	private Integer orderId;
	private Double totalprice;
	private LocalDate orderplacedAt;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "cartId")
	public CartDetails cartdetails;
	 
	@JsonIgnore
	@Type(type="uuid-char")
        public UUID userId;
	 
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "customerId")
	public CustomerData customer;
	 
	@JsonIgnore
	@OneToMany(mappedBy = "orderdetails")
	List<BookCartData> bookcartdata;

	public OrderData(Integer orderId, Double totalprice, LocalDate orderplacedAt, CartDetails cartdetails, UUID userId,
			CustomerData customerdata, List<BookCartData> bookcartdata) {
		this.orderId = orderId;
		this.totalprice = totalprice;
		this.orderplacedAt = orderplacedAt;
		this.cartdetails = cartdetails;
		this.userId = userId;
		this.customer = customerdata;
		this.bookcartdata = bookcartdata;
	}

//	public UUID setCustomer(UUID customerId) {
//		return customerId;
//	}
	 	
}

