package com.project.bookstore.repository;

import com.project.bookstore.dto.OrderItemProjectionDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    //One possible way to achieve the loading data from repository is by executing the whole query
    //but somehow i got a error by using this approach, so i created a procedure directly in the database
    // in this format:
    //DELIMITER //
    //
    //CREATE PROCEDURE GetOrderData()
    //BEGIN
    //    SELECT
    //        o.id AS order_id,
    //        o.order_datetime,
    //        o.status,
    //        o.total_amount,
    //        oi.quantity,
    //        oi.item_name
    //    FROM
    //        orders o
    //    INNER JOIN
    //        order_items oi ON o.id = oi.order_id;
    //END //
    //
    //DELIMITER ;
    //
//    @Query("SELECT o.id AS order_id, o.order_datetime AS orderDatetime, o.status, o.total_amount AS totalAmount, oi.quantity, oi.item_name AS itemName " +
//            "FROM orders o " +
//            "INNER JOIN order_items oi ON o.id = oi.order_id")
//    List<OrderItemProjectionDto> customSelect();

}
