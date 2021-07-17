package com.microservices.bookservice.service;

import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservices.bookservice.exception.BookStoreException;
import com.microservices.bookservice.model.BookCartData;
import com.microservices.bookservice.model.BookData;
import com.microservices.bookservice.model.CartDetails;
import com.microservices.bookservice.model.CustomerData;
import com.microservices.bookservice.model.OrderData;
import com.microservices.bookservice.model.UserData;
import com.microservices.bookservice.repository.BookCartRepository;
import com.microservices.bookservice.repository.BookDataRepository;
import com.microservices.bookservice.repository.CartDetailsRepository;
import com.microservices.bookservice.repository.CustomerDataRepository;
import com.microservices.bookservice.repository.OrderDataRepository;
import com.microservices.bookservice.utils.Token;


@Service
public class OrderService implements IOrderService{

    Token jwtToken = new Token();
	
	@Autowired
	CartDetailsRepository cartrepository;
	
	@Autowired
	BookCartRepository bookcartrepository;
	
	@Autowired
	CustomerDataRepository customerdatarepository;
	
	@Autowired
	OrderDataRepository orderdatarepository;
	
	@Autowired
	BookDataRepository bookdatarepository;
	
	@Autowired
        private RestTemplate restTemplate;
	
	@Override
	public String placeAnOrder(Double totalPrice, UUID customerId, String token) {
		UUID userId = jwtToken.decodeJWT(token);
		CartDetails cartDetails = getCart(token);
		
		List<BookCartData> booksInCart = bookcartrepository.findByCartDetailsCartIdAndOrderstatusIsFalse(cartDetails.getCartId());
		
		CustomerData customerdata=customerdatarepository.findByUserId(cartDetails.getUserId());
		System.out.println("data"+customerdata);
		
		Integer orderId = (int)Math.floor((Math.random() * 999999) + 100000);
		
		OrderData orderdata = new OrderData(orderId,totalPrice,LocalDate.now(),cartDetails,cartDetails.getUserId(),
				                            customerdata, booksInCart);
		orderdata.setCustomer(customerdata);
		OrderData saveorder = orderdatarepository.save(orderdata);
		booksInCart.forEach(cartbooks -> {
			cartbooks.setOrderstatus(true);
			cartbooks.setOrderdetails(orderdata);
			
			bookdatarepository.updatetheValues(cartbooks.getQuantity(),cartbooks.getBookdata().getBookId());
			
		});
		
		booksInCart.forEach(cartbooks -> {
			BookData searchbook = bookdatarepository.findById(cartbooks.getBookdata().getBookId())
					             .orElseThrow(() -> new BookStoreException(BookStoreException.ExceptionTypes.BOOK_NOT_FOUND));
			searchbook.setQuantity(searchbook.getQuantity() - cartbooks.getQuantity());
			bookdatarepository.save(searchbook);
		});
		
		bookcartrepository.updateOrderStatus(cartDetails.getCartId());
		return "Hurray!! Your Order is Confirmed for OrderId #"+saveorder.getOrderId()+" save the OrderId for further Communication";
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
}

