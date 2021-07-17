package com.microservices.bookservice.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.microservices.bookservice.dto.CartDataDTO;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="cart_data")
@Getter
@Setter
public class BookCartData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(type="uuid-char")
	private UUID cartdataId;
	
	private int quantity;
	private double totalprice;
	private boolean orderstatus;
	public String addedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
	
	@JsonIgnore
	@ManyToOne()
        @JoinColumn(name = "bookId")
        public BookData bookdata;
	
	@ManyToOne()
        @JoinColumn(name = "cartId")
        public CartDetails cartDetails;
	
	@ManyToOne
        @JoinColumn(name = "id")
        public OrderData orderdetails;
	
	public BookCartData() { }

	public BookCartData(CartDataDTO cartdto,BookCartData cartdata) {
		this.cartdataId = cartdata.getCartdataId();
		this.quantity = cartdto.getQuantity();
		this.totalprice = cartdto.getTotalprice();
		this.orderstatus=false;
		this.addedAt= LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
		this.bookdata = cartdata.getBookdata();
		this.cartDetails = cartdata.getCartDetails();
		
	}
	
	public BookCartData(BookCartData cartdata) {
		this.cartdataId = cartdata.getCartdataId();
		this.quantity = cartdata.getQuantity();;
		this.totalprice = cartdata.getTotalprice();
		this.orderstatus = cartdata.orderstatus;
		this.addedAt = cartdata.getAddedAt();
		this.bookdata = cartdata.getBookdata();
		this.cartDetails = cartdata.getCartDetails();
	}

	public BookCartData(CartDataDTO cartdto) {
		this.quantity = cartdto.getQuantity();
		this.totalprice = cartdto.getTotalprice();
		this.orderstatus=false;
		this.addedAt= LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
	}
	
}
