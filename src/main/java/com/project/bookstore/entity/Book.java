package com.project.bookstore.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book")
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "title")
    private String title;

    @Column(name = "genre")
    private String genre;

    @Column(name = "author")
    private String author;

    @Column(name = "price")
    private Double price;

    @Column(name = "description")
    private String description;

    @Column(name = "quantity")
    private int quantity;



    public Book(String title, String genre, String author, Double price, String description, int quantity) {
        this.title = title;
        this.genre = genre;
        this.author = author;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
    }
}
