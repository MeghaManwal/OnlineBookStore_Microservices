package com.microservices.bookservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservices.bookservice.dto.CartDataDTO;
import com.microservices.bookservice.dto.UpdateCartDTO;
import com.microservices.bookservice.exception.BookStoreException;
import com.microservices.bookservice.model.BookCartData;
import com.microservices.bookservice.model.BookData;
import com.microservices.bookservice.model.CartDetails;
import com.microservices.bookservice.model.UserData;
import com.microservices.bookservice.repository.BookCartRepository;
import com.microservices.bookservice.repository.BookDataRepository;
import com.microservices.bookservice.repository.CartDetailsRepository;
import com.microservices.bookservice.utils.Token;

@Service
public class CartService implements ICartService {

    Token jwtToken = new Token();
		
	@Autowired
	CartDetailsRepository cartrepository;
	
	@Autowired
	BookCartRepository bookcartrepository;
	
	@Autowired
	BookDataRepository bookdatarepository;
	
	@Autowired
        private RestTemplate restTemplate;
	
	@Override
	public String addBookToCart(CartDataDTO cartdto, String token) {
             UUID userId = jwtToken.decodeJWT(token);

             BookData bookById = bookdatarepository.findById(cartdto.getBookId()).
                                 orElseThrow(() -> new BookStoreException(BookStoreException.ExceptionTypes.BOOK_NOT_FOUND));

            if(bookById.isAdded()==true){
                   throw new BookStoreException(BookStoreException.ExceptionTypes.BOOK_ALREADY_PRESENT);
            }

            else {
            CartDetails cartDetails = getCart(token);
            BookCartData bookCartDetails = new BookCartData(cartdto);
            List<BookCartData> cartList = new ArrayList<>();

            cartList.add(bookCartDetails);
            cartDetails.getCartdata().add(bookCartDetails);
            cartDetails.setCartdata(cartList);
            cartrepository.save(cartDetails);
            bookCartDetails.setCartDetails(cartDetails);
            bookCartDetails.setBookdata(bookById);
            bookcartrepository.save(bookCartDetails);
            bookById.setAdded(true);
            bookdatarepository.save(bookById);
            return "Book Added To Cart Successfully";

        }
	}

	private CartDetails getCart(String userId) {
	        UserData user = findByUserId(userId);
	        UUID id = user.getUserId();

	        CartDetails cartdetails= cartrepository.findByUserId(id).
	                orElseThrow(() -> new BookStoreException(BookStoreException.ExceptionTypes.CART_NOT_PRESENT));
	        return cartdetails;

	}

	private UserData findByUserId(String userId) {
	        UserData userDetailsModel = restTemplate.
	                getForObject("http://localhost:8081/user/getuser?userEmailToken= "+userId,
	                        UserData.class);

	        if(userDetailsModel == null){
	            throw new BookStoreException(BookStoreException.ExceptionTypes.USER_NOT_FOUND);
	        }

	        return userDetailsModel;
	}

	@Override
	public CartDetails setCart(String token) {
		CartDetails cartdetails = new CartDetails();
		 UserData userdata = restTemplate.
	                getForObject("http://localhost:8081/user/getuser?userEmailToken= "+token,
	                        UserData.class);
		cartdetails.setUserId(userdata.getUserId());
		
		cartrepository.save(cartdetails);
		return cartdetails;
	}

	@Override
	public List<BookCartData> displayBooksFromCart(String token) {
		UUID userId = jwtToken.decodeJWT(token);
		CartDetails cartDetails = getCart(token);
		
		return bookcartrepository.findAll()
				.stream()
				.map(bookCartData -> new BookCartData(bookCartData))
				.collect(Collectors.toList());
	}

	@Override
	public String deleteBookFromCart(UUID bookId, String token) {
		UUID userId = jwtToken.decodeJWT(token);
		CartDetails cartDetails = getCart(token);
		 
		Optional<BookCartData> findbookById =bookcartrepository.findByBookdataBookId(bookId);
		if (!findbookById.isPresent()){
	            throw new BookStoreException(BookStoreException.ExceptionTypes.BOOK_NOT_FOUND);
	        }
		 
	        BookData searchbookbyId = bookdatarepository.findById(bookId)
				                   .orElseThrow(()-> new BookStoreException(BookStoreException.ExceptionTypes.BOOK_NOT_FOUND));

		bookdatarepository.save(searchbookbyId);
		bookcartrepository.deleteById(findbookById.get().getCartdataId());
		return "Book REMOVED Successfully";
	}

	@Override
	public String updateCartData(UpdateCartDTO updatedto, String token) {
		UUID userId = jwtToken.decodeJWT(token);
	     
		CartDetails cartDetails = getCart(token);
                 
		BookCartData cartdata = bookcartrepository.findByBookdataBookId(updatedto.getBookId())
				             .orElseThrow(() -> new BookStoreException(BookStoreException.ExceptionTypes.BOOK_NOT_FOUND));
		
		System.out.println(cartdata);
		 
		cartdata.setQuantity(updatedto.getQuantity());
		cartdata.setTotalprice(updatedto.getTotalprice());
	        bookcartrepository.save(cartdata);
	     
		return "Changes save Successfully";
	}

}
