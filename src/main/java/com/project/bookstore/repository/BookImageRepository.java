package com.project.bookstore.repository;

import com.project.bookstore.entity.BookImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookImageRepository extends JpaRepository<BookImage, Long> {

    @Query(value = "SELECT * FROM book_image WHERE title = :title", nativeQuery = true)
    BookImage findBookImageByTitle(@Param("title") String title);


    @Query(value = "SELECT i FROM book_image WHERE i.author = :author", nativeQuery = true)
    List<BookImage> receiveBookImagesByAuthor(@Param("author")String author);
}
