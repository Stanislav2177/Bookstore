package com.project.bookstore.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
public class OrderItemProjectionDto {
    private Long order_id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderDatetime;
    private boolean status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
    private double totalAmount;
    private int quantity;
    private String itemName;

}
