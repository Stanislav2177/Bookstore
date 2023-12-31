package com.project.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class OrderDto {

    private LocalDateTime datetime;
    private Map<String, Integer> orderItems;
    private double totalAmount;
    private boolean status;
}

