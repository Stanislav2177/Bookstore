package com.project.bookstore.service;

import com.project.bookstore.dto.BookAndImageDto;
import com.project.bookstore.entity.BookImage;

import java.util.List;

public interface BookImageService {
    BookImage downloadImage(BookImage book);

    BookImage receiveImage(Long id);

    BookImage receiveImageByTitle(String title);

    List<BookImage> receiveBookImagesByAuthor(String author);


}
