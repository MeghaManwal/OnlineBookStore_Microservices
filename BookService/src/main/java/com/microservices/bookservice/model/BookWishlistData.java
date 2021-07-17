package com.microservices.bookservice.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import com.microservices.bookservice.dto.WishlistDataDTO;

import lombok.Data;

@Entity
@Table(name="wishlist_data")
public @Data class BookWishlistData {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(type="uuid-char")
	private UUID wishlistdataId;
		
	public String addedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
	
	//@JsonIgnore
	@ManyToOne()
        @JoinColumn(name = "bookId")
        public BookData bookdata;
	
	//@JsonIgnore
	@ManyToOne()
        @JoinColumn(name = "wishlistId")
        public WishlistDetails whishlistDetails;

	public BookWishlistData(UUID wishlistdataId, String addedAt, BookData bookdata, WishlistDetails whishlistDetails) {
		this.wishlistdataId = wishlistdataId;
		this.addedAt = addedAt;
		this.bookdata = bookdata;
		this.whishlistDetails = whishlistDetails;
	}
	
	public BookWishlistData(BookData bookdata, WishlistDetails whishlistDetails) {
		this.bookdata = bookdata;
		this.whishlistDetails = whishlistDetails;
	}
	
	public BookWishlistData() {
		
	}
	
	public BookWishlistData(WishlistDataDTO wishlistdto) {
		
	}

	public BookWishlistData(BookWishlistData bookwishlistdetails) {
		this.wishlistdataId = bookwishlistdetails.getWishlistdataId();
		this.addedAt = bookwishlistdetails.getAddedAt();
		this.bookdata = bookwishlistdetails.getBookdata();
		this.whishlistDetails = bookwishlistdetails.getWhishlistDetails();
	
	}
			
}

