package com.microservices.bookservice.service;

import java.util.List;

import com.microservices.bookservice.dto.BookDataDTO;
import com.microservices.bookservice.model.BookData;

public interface IBookService {
	
	public BookData addNewBook(BookDataDTO bookdatadto);
	public List<BookData> getAllBooks(); 
	public String getbookscount();
	public List<BookData> displaybyHighertoLowerPrice();
	public List<BookData> displaybyLowertoHigherPrice();
	public List<BookData> displayBooksbyDateOfArrival();
	
}
