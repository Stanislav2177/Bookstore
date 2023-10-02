package com.project.bookstore.service;

import com.project.bookstore.dto.OrderDto;

import java.util.Map;

public interface ShoppingCartService {
    void addToCart(Long bookId, int quantity);
    void removeFromCart(Long bookId, int quantity);

    Map<Long, Integer> getCartContents();

    OrderDto finishOrder();

    double calculateTotalPrice();


}
