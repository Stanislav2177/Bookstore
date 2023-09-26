package com.project.bookstore.service;

import com.project.bookstore.entity.Book;
import java.util.List;

public interface BookService {
    Book addBook(Book book);

    void deleteBook(Long id);

    Book searchBookByTitle(String title);

    Book getARandomBook();

    List<Book> getAllBooks();

    List<Book> getAllBooksSortedByPrice(String parameter);

    Book updateBook(Book book, Long id);

    List<Book> getAllBooksSortedByGenre(String genre);






}
