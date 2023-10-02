package com.project.bookstore.exception;

public class BookOutOfStockException extends RuntimeException{
    public BookOutOfStockException(String message) {
        super(message);
    }
}
