package com.project.bookstore.dto;

import com.project.bookstore.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public Order mapToEntity(OrderDto orderDto) {
        Order orderEntity = new Order();
        orderEntity.setDatetime(orderDto.getDatetime());
        orderEntity.setOrderItems(orderDto.getOrderItems());
        orderEntity.setTotalAmount(orderDto.getTotalAmount());
        orderEntity.setStatus(orderDto.isStatus());

        return orderEntity;
    }
}
