package com.project.bookstore.exception;

public class BookAlreadyExistException extends RuntimeException{
    public BookAlreadyExistException(String message) {
        super(message);
    }
}
