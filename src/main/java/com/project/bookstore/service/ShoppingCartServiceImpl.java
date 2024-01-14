package com.project.bookstore.service;

import com.project.bookstore.dto.OrderDto;
import com.project.bookstore.dto.OrderItemProjectionDto;
import com.project.bookstore.dto.OrderMapper;
import com.project.bookstore.entity.Book;
import com.project.bookstore.entity.Order;
import com.project.bookstore.exception.BookNotFoundException;
import com.project.bookstore.exception.BookOutOfStockException;
import com.project.bookstore.exception.NoFinishedOrdersException;
import com.project.bookstore.repository.OrderDetailsRepository;
import com.project.bookstore.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final Map<Long, Integer> cartItems;
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    OrderDetailsRepository orderItemInfoRepository;

    private final Map<String, Integer> cartItemsWithNames;

    @Autowired
    private BookService bookService;

    public ShoppingCartServiceImpl( Map<String, Integer> cartItemsWithNames) {
        this.cartItemsWithNames = cartItemsWithNames;
        this.cartItems = new HashMap<>();
    }

    @Override
    public List<OrderItemProjectionDto> customSelect() {
        List<OrderItemProjectionDto> orderItemProjectionDtos = orderItemInfoRepository.getCustomProcedure();

        if(orderItemProjectionDtos.isEmpty()){
            throw new NoFinishedOrdersException("No orders which to be presented");
        }

        return orderItemProjectionDtos;

    }

    private String getFormattedOrderDatetime(LocalDateTime orderDatetime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return orderDatetime.format(formatter);
    }



    @Override
    public void addToCart(Long bookId, int quantity) {
        Book book = bookService.searchBookById(bookId);
        if (book == null) {
            throw new BookNotFoundException("Book with ID " + bookId + " not found");
        }

        cartItems.put(bookId, cartItems.getOrDefault(bookId, 0) + quantity);
        quantity = 0;
        cartItemsWithNames.put(book.getTitle(), cartItems.getOrDefault(bookId, 0) + quantity);
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
    @Override
    public OrderDto finishOrder() {
        OrderDto orderDto = new OrderDto();

        orderDto.setOrderItems(new HashMap<>());

        boolean status = false;
        int requiredQ = 0;

        for (Long id : cartItems.keySet()) {
            Book book = bookService.searchBookById(id);
            requiredQ = cartItems.get(id);

            status = validateOrderQuantity(book, requiredQ);
        }

        //Format LocalDateTime to custom patter
        //before saving it to the database
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        LocalDateTime parsedDateTime = LocalDateTime.parse(formattedDateTime, formatter);

        orderDto.setDatetime(parsedDateTime);
        orderDto.setOrderItems(cartItemsWithNames);

        // Format totalAmount to two decimal places
        //before saving it to the database
        double totalAmount = calculateTotalPrice();
        Locale locale = new Locale("en", "US");
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(locale);
        DecimalFormat decimalFormat = new DecimalFormat("0.00", symbols);
        String formattedTotalAmount = decimalFormat.format(totalAmount);
        orderDto.setTotalAmount(Double.parseDouble(formattedTotalAmount));

        orderDto.setStatus(status);

        if(status){
            Order orderEntity = orderMapper.mapToEntity(orderDto);
            orderRepository.save(orderEntity);

            cartItems.clear();
        }
        return orderDto;
    }

    private boolean validateOrderQuantity(Book book, int requiredQ){
        int oldQ = book.getQuantity();

        if(oldQ >= requiredQ){
            bookService.updateQuantity(book.getId(), oldQ - requiredQ);
            return true;
        }else {
            throw new BookOutOfStockException("Book with title: " + book.getTitle() +
                    " is out of stock, required quantity = " + requiredQ +
                    ", but only " + book.getQuantity() + " in stock");
        }
    }
}

