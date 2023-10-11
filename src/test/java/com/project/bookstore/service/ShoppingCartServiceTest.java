package com.project.bookstore.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.project.bookstore.entity.Book;
import com.project.bookstore.exception.BookNotFoundException;
import com.project.bookstore.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShoppingCartServiceTest {

    @InjectMocks
    private ShoppingCartServiceImpl shoppingCartService;

    @Mock
    private BookService bookService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddToCart() {
        Long bookId = 1L;
        int quantity = 2;
        Book book = new Book();
        book.setId(bookId);
        book.setPrice(10.0);

        when(bookService.searchBookById(bookId)).thenReturn(book);

        shoppingCartService.addToCart(bookId, quantity);

        Map<Long, Integer> cartContents = shoppingCartService.getCartContents();
        assertEquals(2, cartContents.get(bookId));
    }

    @Test
    public void testAddToCartBookNotFound() {
        Long bookId = 1L;
        int quantity = 2;

        when(bookService.searchBookById(bookId)).thenReturn(null);

        try {
            shoppingCartService.addToCart(bookId, quantity);
        } catch (BookNotFoundException e) {
            assertEquals("Book with ID 1 not found", e.getMessage());
        }
    }



}

