package com.microservices.userservices.exception;

public class BookStoreException extends RuntimeException {
	
    public ExceptionTypes exceptionTypes;

    public BookStoreException(ExceptionTypes exceptionTypes) {
        this.exceptionTypes = exceptionTypes;
    }
    
    public enum ExceptionTypes {
    	 USER_ALREADY_PRESENT,
    	 USER_NOT_FOUND,
    	 BOOK_ALREADY_PRESENT,	 
    }

    public BookStoreException(String message, BookStoreException.ExceptionTypes exceptionTypes) {
        super(message);
        this.exceptionTypes = exceptionTypes;
    }

}
