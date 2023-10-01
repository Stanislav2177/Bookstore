package com.project.bookstore.service;

import com.project.bookstore.entity.Book;
import com.project.bookstore.exception.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final Map<Long, Integer> cartItems; // Map book IDs to quantities

    @Autowired
    private BookService bookService;

    public ShoppingCartServiceImpl() {
        this.cartItems = new HashMap<>();
    }


    @Override
    public void addToCart(Long bookId, int quantity) {
        Book book = bookService.searchBookById(bookId);
        if (book == null) {
            throw new BookNotFoundException("Book with ID " + bookId + " not found");
        }

        // Add to the cart or update quantity
        cartItems.put(bookId, cartItems.getOrDefault(bookId, 0) + quantity);
    }
    @Override
    public void removeFromCart(Long bookId, int quantity) {
        if (cartItems.containsKey(bookId)) {
            int currentQuantity = cartItems.get(bookId);
            if (quantity >= currentQuantity) {
                cartItems.remove(bookId);
            } else {
                cartItems.put(bookId, currentQuantity - quantity);
            }
        }
    }

    @Override
    public Map<Long, Integer> getCartContents() {
        return Collections.unmodifiableMap(cartItems);
    }

    @Override
    public double calculateTotalPrice() {
        double totalPrice = 0;
        for (Map.Entry<Long, Integer> entry : cartItems.entrySet()) {
            Book book = bookService.searchBookById(entry.getKey());
            if (book != null) {
                totalPrice += book.getPrice() * entry.getValue();
            }
        }
        return totalPrice;
    }
}

