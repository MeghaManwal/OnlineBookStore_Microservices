package com.microservices.bookservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservices.bookservice.dto.WishlistDataDTO;
import com.microservices.bookservice.exception.BookStoreException;
import com.microservices.bookservice.model.BookData;
import com.microservices.bookservice.model.BookWishlistData;
import com.microservices.bookservice.model.UserData;
import com.microservices.bookservice.model.WishlistDetails;
import com.microservices.bookservice.repository.BookDataRepository;
import com.microservices.bookservice.repository.BookWishlistRepository;
import com.microservices.bookservice.repository.WishlistDetailsRepository;
import com.microservices.bookservice.utils.Token;



@Service
public class WishlistService implements IWishlistService{

    Token jwtToken = new Token();
	
	@Autowired
	WishlistDetailsRepository wishlistrepository;
	
	@Autowired
	BookWishlistRepository bookwishlistrepository;
	
	@Autowired
	BookDataRepository bookdatarepository;
	
	@Autowired
        private RestTemplate restTemplate;
	
	@Override
	public String addBookToWishlist(WishlistDataDTO wishlistdto, String token) {
		if(token == null) {
			throw new BookStoreException(BookStoreException.ExceptionTypes.TOKEN_NOT_FOUND);
		}else {
		UUID userId = jwtToken.decodeJWT(token);
		UserData findExistingUser = findByUserId(userId);
		
		WishlistDetails wishlistdetails = wishlistrepository.findByUserData(findExistingUser).orElseThrow(() -> new
				BookStoreException(BookStoreException.ExceptionTypes.WISHLIST_NOT_PRESENT));
		
		BookData bookById = bookdatarepository.findById(wishlistdto.getBookId())
	            .orElseThrow(() -> new
	              BookStoreException(BookStoreException.ExceptionTypes.BOOK_NOT_FOUND));
		
		List<BookWishlistData> wishlistdatalist = new ArrayList<>();
		BookWishlistData data = new BookWishlistData(wishlistdto);
		
		wishlistdatalist.add(data);
		wishlistdetails.getWishlistdata().add(data);
		wishlistdetails.setWishlistdata(wishlistdatalist);
		wishlistrepository.save(wishlistdetails);
		
		data.setWhishlistDetails(wishlistdetails);
		data.setBookdata(bookById);
		BookWishlistData savebooksTocart = bookwishlistrepository.save(data);
		
		return "Book added successfully";
		}
	}

	private UserData findByUserId(UUID userId) {
		UserData userdata = restTemplate.
                getForObject("http://localhost:8081/user/getuser?userEmailToken= "+userId,
                        UserData.class);

                if(userdata == null){
                         throw new BookStoreException(BookStoreException.ExceptionTypes.USER_NOT_FOUND);
                }

                return userdata;
	}

	@Override
	public List<BookWishlistData> displayBooksFromWishlist(String token) {
		UUID userId = jwtToken.decodeJWT(token);
		UserData findExistingUser = findByUserId(userId);
	
		 return bookwishlistrepository.findAll()
					.stream()
					.map(bookwishlistdetails-> new BookWishlistData(bookwishlistdetails))
					.collect(Collectors.toList());
	}

	@Override
	public String deleteBookFromWishlist(UUID bookId, String token) {
		UUID userId = jwtToken.decodeJWT(token);
		UserData findExistingUser = findByUserId(userId);
		
		 Optional<BookWishlistData> findbookById = bookwishlistrepository.findByBookdataBookId(bookId);
		 if (!findbookById.isPresent()){
	            throw new BookStoreException(BookStoreException.ExceptionTypes.BOOK_NOT_FOUND);
	         }
		  
		 BookData searchbookbyId = bookdatarepository.findById(bookId)
                 .orElseThrow(()-> new BookStoreException(BookStoreException.ExceptionTypes.BOOK_NOT_FOUND));

                 bookdatarepository.save(searchbookbyId);
                 bookwishlistrepository.deleteById(findbookById.get().getWishlistdataId());
                 return "Book REMOVED Successfully";
	}

	@Override
	public WishlistDetails setWishlist(String token) {
		 WishlistDetails wishlistdetails = new WishlistDetails();
		 UserData userdata = restTemplate.
	                getForObject("http://localhost:8081/user/getuser?userEmailToken= "+token,
	                        UserData.class);
		 wishlistdetails.setUserData(userdata);
		
		 wishlistrepository.save(wishlistdetails);
		 return wishlistdetails;
	}

	
}

