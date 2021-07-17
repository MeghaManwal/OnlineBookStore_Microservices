package com.microservices.bookservice.service;

import java.util.List;
import java.util.UUID;

import com.microservices.bookservice.dto.CartDataDTO;
import com.microservices.bookservice.dto.UpdateCartDTO;
import com.microservices.bookservice.model.BookCartData;
import com.microservices.bookservice.model.CartDetails;
import com.microservices.bookservice.model.UserData;


public interface ICartService {
	
	public String addBookToCart(CartDataDTO cartdto, String token);
	public CartDetails setCart(String token);
	public List<BookCartData> displayBooksFromCart(String token);
	public String deleteBookFromCart(UUID bookId, String token);
	public String updateCartData(UpdateCartDTO updatedto, String token);
	
}

