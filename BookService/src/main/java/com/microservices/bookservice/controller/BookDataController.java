package com.microservices.bookservice.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.bookservice.dto.BookDataDTO;
import com.microservices.bookservice.dto.ResponseDTO;
import com.microservices.bookservice.model.BookData;
import com.microservices.bookservice.service.IBookService;

@RestController
@RequestMapping("/book")
public class BookDataController {

	@Autowired
	private IBookService bookservice;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDTO> addBookDetails(@Valid @RequestBody BookDataDTO bookdatadto) {
		BookData bookdata = bookservice.addNewBook(bookdatadto);
		ResponseDTO respdto = new ResponseDTO("New Book Data has been Successfully added", bookdata);
		return new ResponseEntity<ResponseDTO>(respdto, HttpStatus.OK);
	}
	
	@GetMapping("/view")
	public ResponseEntity<List<BookData>> getBooksList() {
		List<BookData> booksList=bookservice.getAllBooks();
		return ResponseEntity.status(HttpStatus.OK).body(booksList);
	}

	@GetMapping("/count")
	public ResponseEntity getTotalBooksCount() {
		return ResponseEntity.status(HttpStatus.OK).body(bookservice.getbookscount());
	}
	
	@GetMapping("/lowTohigh")
	public ResponseEntity<List<BookData>> sortbyLowertoHigherPrice() {
		List<BookData> booksList = bookservice.displaybyLowertoHigherPrice();
		return ResponseEntity.status(HttpStatus.OK).body(booksList);
	}
	
	@GetMapping("/highTolow")
	public ResponseEntity<List<BookData>> sortbyHighertoLowerPrice() {
		List<BookData> booksList = bookservice.displaybyHighertoLowerPrice();
		return ResponseEntity.status(HttpStatus.OK).body(booksList);
	}
	
	@GetMapping("/newest")
	public ResponseEntity<List<BookData>> sortbyDateOfArrival() {
		List<BookData> booksList = bookservice.displayBooksbyDateOfArrival();
		return ResponseEntity.status(HttpStatus.OK).body(booksList);
	}
}
