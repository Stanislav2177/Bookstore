package com.project.bookstore.repository;

import com.project.bookstore.dto.OrderItemProjectionDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDetailsRepository {
    private final JdbcTemplate jdbcTemplate;


    public OrderDetailsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<OrderItemProjectionDto> getCustomProcedure(){
        String sql = "Call GetOrderData()";

        List<OrderItemProjectionDto> query = jdbcTemplate
                .query(sql,
                        new BeanPropertyRowMapper<>(OrderItemProjectionDto.class));


        System.out.println("Data received from query: " + query.toString());
        return query;
    }

}
