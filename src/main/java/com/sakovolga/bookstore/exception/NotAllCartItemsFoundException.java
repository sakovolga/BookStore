package com.sakovolga.bookstore.exception;

public class NotAllCartItemsFoundException extends RuntimeException {
    public NotAllCartItemsFoundException(String message) {
        super(message);
    }
}
