package com.project.bookstore.repository;

import com.project.bookstore.dto.OrderItemProjectionDto;
import com.project.bookstore.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
