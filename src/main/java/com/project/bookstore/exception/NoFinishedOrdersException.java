package com.project.bookstore.exception;

public class NoFinishedOrdersException extends RuntimeException{
    public NoFinishedOrdersException(String message) {
        super(message);
    }
}
