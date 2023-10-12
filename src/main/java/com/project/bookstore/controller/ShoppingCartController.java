package com.project.bookstore.controller;

import com.project.bookstore.dto.OrderDto;
import com.project.bookstore.dto.OrderItemProjectionDto;
import com.project.bookstore.service.ShoppingCartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    private final Logger logger = LoggerFactory.getLogger(ShoppingCartController.class);

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/view")
    public ResponseEntity<?> viewCart() {
        logger.info("Received a request to view the shopping cart.");
        return ResponseEntity.ok(shoppingCartService.getCartContents());
    }

    @PostMapping("/add/{bookId}")
    public ResponseEntity<?> addToCart(@PathVariable Long bookId, @RequestParam int quantity) {
        logger.info("Received a request to add book with ID {} " +
                "to the cart with quantity {}.", bookId, quantity);

        shoppingCartService.addToCart(bookId, quantity);

        logger.info("Book with ID {} added to cart successfully.", bookId);
        return ResponseEntity.ok("Item added to cart successfully");
    }

    @GetMapping("/order-details")
    public ResponseEntity<List<OrderItemProjectionDto> > getOrderDetails() {

        logger.info("Received a request to get order details.");

        List<OrderItemProjectionDto> orderItemProjectionDtos
                = shoppingCartService.customSelect();

        logger.info("Order details retrieved: {}", orderItemProjectionDtos.toString());
        return ResponseEntity.ok(orderItemProjectionDtos);
    }

    @PostMapping("/remove/{bookId}")
    public ResponseEntity<?> removeFromCart(@PathVariable Long bookId, @RequestParam int quantity) {
        logger.info("Received a request to remove book with ID {} " +
                "from the cart with quantity {}.", bookId, quantity);

        shoppingCartService.removeFromCart(bookId, quantity);
        logger.info("Book with ID {} removed from cart successfully.", bookId);

        return ResponseEntity.ok("Item removed from cart successfully");
    }

    @GetMapping("/totalPrice")
    public ResponseEntity<?> calculateTotalPrice() {
        logger.info("Received a request to calculate the total price.");

        double totalPrice = shoppingCartService.calculateTotalPrice();

        logger.info("Total Price calculated: {}", totalPrice);
        return ResponseEntity.ok("Total Price: " + totalPrice);
    }

    @PostMapping("/finishOrder")
    public ResponseEntity<OrderDto> finishOrder() {
        logger.info("Received a request to finish the order.");

        OrderDto orderDto = shoppingCartService.finishOrder();

        logger.info("Order finished successfully.");
        return ResponseEntity.ok(orderDto);
    }
}
