package com.project.bookstore.dto;


import com.project.bookstore.entity.Book;
import com.project.bookstore.entity.BookImage;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class BookAndImageDto {
    private String title;
    private String genre;
    private String author;
    private Double price;
    private String description;
    private int quantity;
    private byte[] encoded;
}
