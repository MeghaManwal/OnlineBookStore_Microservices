package com.microservices.bookservice.exception;

public class BookStoreException extends RuntimeException {
	
    public ExceptionTypes exceptionTypes;

    public BookStoreException(ExceptionTypes exceptionTypes) {
        this.exceptionTypes = exceptionTypes;
    }
    
    public enum ExceptionTypes {
    	 USER_ALREADY_PRESENT("User already Present!!"),
    	 USER_NOT_FOUND("User not found!!"),
    	 BOOK_ALREADY_PRESENT("Book already Present!!"),	
    	 CART_NOT_PRESENT("Cart not Present!!"),
    	 BOOK_NOT_FOUND("Book not found!!"),
    	 WISHLIST_NOT_PRESENT("No Whislist found!!"),
    	 TOKEN_NOT_FOUND("Please Enter Valid Token"),
    	 CUSTOMER_NOT_FOUND("Please Enter Valid Customer Id"),
    	 TOKEN_ABSENT("Jwt Token Cannot Be null or Empty");
    	 
    	 public String errorMsg;

         ExceptionTypes(String errorMsg) {
             this.errorMsg = errorMsg;
         }
    }
    
    public BookStoreException(String message, BookStoreException.ExceptionTypes exceptionTypes) {
        super(message);
    }

}

