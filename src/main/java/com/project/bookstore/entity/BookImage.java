package com.project.bookstore.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BookImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "url")
    private String url;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] encoded;


//    public ImageBook(Long id, String title, String author, String url) {
//        this.id = id;
//        this.title = title;
//        this.author = author;
//        this.url = url;
//    }
}
