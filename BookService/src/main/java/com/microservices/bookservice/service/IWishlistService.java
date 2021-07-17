package com.microservices.bookservice.service;

import java.util.List;
import java.util.UUID;

import com.microservices.bookservice.dto.WishlistDataDTO;
import com.microservices.bookservice.model.BookWishlistData;
import com.microservices.bookservice.model.UserData;
import com.microservices.bookservice.model.WishlistDetails;

public interface IWishlistService {

	public String addBookToWishlist(WishlistDataDTO wishlistdto, String token);
	public List<BookWishlistData> displayBooksFromWishlist(String token);
	public String deleteBookFromWishlist(UUID bookId, String token);
	public WishlistDetails setWishlist(String token);
	
}