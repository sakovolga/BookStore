package com.sakovolga.bookstore.exception;

public class NothingFoundException extends RuntimeException{
    public NothingFoundException(String message) {
        super(message);
    }
}
