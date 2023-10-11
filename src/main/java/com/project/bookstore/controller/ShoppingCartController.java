package com.project.bookstore.controller;
import com.project.bookstore.dto.OrderDto;
import com.project.bookstore.dto.OrderItemProjectionDto;
import com.project.bookstore.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/view")
    public ResponseEntity<?> viewCart() {
        return ResponseEntity.ok(shoppingCartService.getCartContents());
    }

    @PostMapping("/add/{bookId}")
    public ResponseEntity<?> addToCart(@PathVariable Long bookId,
                                       @RequestParam int quantity) {
        shoppingCartService.addToCart(bookId, quantity);
        return ResponseEntity.ok("Item added to cart successfully");
    }



    @GetMapping("/order-details")
    public ResponseEntity<List<OrderItemProjectionDto>> getOrderDetails(){
        List<OrderItemProjectionDto> orderItemProjectionDtos = shoppingCartService.customSelect();

        System.out.println(orderItemProjectionDtos.toString());

        return ResponseEntity.ok(shoppingCartService.customSelect());
    }

    @PostMapping("/remove/{bookId}")
    public ResponseEntity<?> removeFromCart(@PathVariable Long bookId, @RequestParam int quantity) {
        shoppingCartService.removeFromCart(bookId, quantity);
        return ResponseEntity.ok("Item removed from cart successfully");
    }

    @GetMapping("/totalPrice")
    public ResponseEntity<?> calculateTotalPrice() {
        double totalPrice = shoppingCartService.calculateTotalPrice();
        return ResponseEntity.ok("Total Price: " + totalPrice);
    }

    @PostMapping("/finishOrder")
    public ResponseEntity<OrderDto> finishOrder() {
        OrderDto orderDto = shoppingCartService.finishOrder();
        return ResponseEntity.ok(orderDto);
    }
}

