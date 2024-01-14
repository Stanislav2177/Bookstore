package com.project.bookstore.service;

import com.project.bookstore.dto.BookAndImageDto;
import com.project.bookstore.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    Book addBook(Book book);

    void deleteBook(Long id);

    Book searchBookByTitle(String title);

    Book getARandomBook();

    List<Book> getAllBooks();

    Page<Book> getBooks(Pageable pageable);

    Book searchBookById(Long id);

    List<Book> getAllBooksSortedByPrice(String parameter);

    Book updateBook(Book book, Long id);

    List<Book> getAllBooksSortedByGenre(String genre);

    void updateQuantity(Long id, int newQuantity);

    List<BookAndImageDto> getAllBooksPlusImages();




}
