package com.project.bookstore.service;

import com.project.bookstore.entity.Book;
import com.project.bookstore.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @InjectMocks
    BookServiceImpl bookService;

    @Mock
    BookRepository dao;

    @Test
    void testAddBook(){
        Book book = new Book(1L, "Title", "horror", "Author", 15.99, "No desc");

        when(dao.save(book)).thenReturn(book);

        Book testBook = bookService.addBook(book);
        assertEquals(book.getId(), testBook.getId());
        verify(dao).save(book);
    }

    @Test
    void testSearchBookByTitle(){
        Book book1 = new Book(1L, "Title", "horror", "Author", 15.99, "No desc");
        Book book2 = new Book(1L, "Title2", "horror", "Author", 15.99, "No desc");

        List<Book> bookList = new ArrayList<>();
        bookList.add(book1);
        bookList.add(book2);

        when(dao.findAll()).thenReturn(bookList);

        Book expectedBookTitle = bookService.searchBookByTitle("Title");

        assertEquals(book1.getTitle(), expectedBookTitle.getTitle());

        verify(dao, times(1)).findAll();
    }

    @Test
    void testGetAllBookSortedByPriceMin(){
        Book book1 = new Book(1L, "Title", "horror", "Author", 10.1, "No desc");
        Book book2 = new Book(1L, "Title2", "horror", "Author", 9.0, "No desc");
        Book book3 = new Book(1L, "Title3", "horror", "Author", 8.0, "No desc");

        List<Book> bookList = new ArrayList<>();
        bookList.add(book1);
        bookList.add(book2);
        bookList.add(book3);

        when(dao.findAll()).thenReturn(bookList);

        List<Book> sorted = bookService.getAllBooksSortedByPrice("min");
        assertEquals(sorted.get(0).getPrice(), 8.0);

        verify(dao, times(1)).findAll();
    }

    @Test
    void testGetAllBookSortedByPriceMax(){
        Book book1 = new Book(1L, "Title", "horror", "Author", 10.1, "No desc");
        Book book2 = new Book(1L, "Title2", "horror", "Author", 9.0, "No desc");
        Book book3 = new Book(1L, "Title3", "horror", "Author", 8.0, "No desc");

        List<Book> bookList = new ArrayList<>();
        bookList.add(book1);
        bookList.add(book2);
        bookList.add(book3);

        when(dao.findAll()).thenReturn(bookList);

        List<Book> sorted = bookService.getAllBooksSortedByPrice("max");
        assertEquals(sorted.get(0).getPrice(), 10.1);

        verify(dao, times(1)).findAll();
    }


    @Test
    void testUpdateBookSuccess(){
        Book existingBook = new Book(1L, "Title", "horror", "Author", 10.1, "No desc");
        Book updatedBook = new Book(1L, "Title2", "horror", "Author", 9.0, "No desc");

        when(dao.findById(anyLong())).thenReturn(Optional.of(existingBook));
        when(dao.save(updatedBook)).thenReturn(updatedBook);

        Book result = bookService.updateBook(updatedBook, 1L);

        verify(dao, times(1)).findById(1L);

        // Add specific assertions for the attributes
        assertEquals(updatedBook.getId(), result.getId());
        assertEquals(updatedBook.getTitle(), result.getTitle());
        assertEquals(updatedBook.getAuthor(), result.getAuthor());
        assertEquals(updatedBook.getPrice(), result.getPrice(), 0.01); // Use appropriate tolerance for floating-point comparison
        assertEquals(updatedBook.getGenre(), result.getGenre());
        assertEquals(updatedBook.getDescription(), result.getDescription());
    }



}
