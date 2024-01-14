package com.project.bookstore.service;

import com.project.bookstore.controller.BookController;
import com.project.bookstore.dto.BookAndImageDto;
import com.project.bookstore.entity.Book;
import com.project.bookstore.entity.BookImage;
import com.project.bookstore.exception.AuthorNotFoundException;
import com.project.bookstore.exception.ProblemWithDownloadImage;
import com.project.bookstore.repository.BookImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class BookImageServiceImpl implements BookImageService {

    private final RestTemplate restTemplate;
    private final BookImageRepository repository;
    private final Logger logger = LoggerFactory.getLogger(BookController.class);


    public BookImageServiceImpl(RestTemplate restTemplate, BookImageRepository repository) {
        this.restTemplate = restTemplate;
        this.repository = repository;
    }

    @Override
    public BookImage downloadImage(BookImage book) {
        byte[] imageBytes = restTemplate.getForObject(book.getUrl(), byte[].class);


        if(imageBytes == null){
            throw new ProblemWithDownloadImage("Invalid Link");
        }

        book.setEncoded(imageBytes);

        repository.save(book);

        return book;
    }

    @Override
    public BookImage receiveImage(Long id) {
        return repository.getReferenceById(id);
    }

    @Override
    public BookImage receiveImageByTitle(String title){
        return repository.findBookImageByTitle(title);

    }



    @Override
    public List<BookImage> receiveBookImagesByAuthor(String author) {
        List<BookImage> bookImages = repository.receiveBookImagesByAuthor(author);

        if(bookImages.isEmpty()){
            throw new AuthorNotFoundException("Author not found");
        }

        return bookImages;
    }
}
