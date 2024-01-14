package com.project.bookstore.exception;

public class ProblemWithDownloadImage extends RuntimeException{
    public ProblemWithDownloadImage(String message) {
        super(message);
    }
}
