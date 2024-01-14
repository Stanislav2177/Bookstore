package com.project.bookstore.controller;

import com.project.bookstore.entity.BookImage;
import com.project.bookstore.service.BookImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/image")
public class BookImageController {

    @Autowired
    private final BookImageService imageBookService;

    public BookImageController(BookImageService imageBookService) {
        this.imageBookService = imageBookService;
    }

    @PostMapping("/download")
    public ResponseEntity<Void> downloadImage(@RequestBody BookImage book){
        imageBookService.downloadImage(book);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/receive")
    public ResponseEntity<BookImage> receiveImage(@RequestParam Long id){
        BookImage imageBook = imageBookService.receiveImage(id);

        return ResponseEntity.ok(imageBook);
    }

    @GetMapping("/receive/image/{title}")
    public ResponseEntity<BookImage> receiveImageByTitle(@PathVariable String title){
        BookImage bookImage = imageBookService.receiveImageByTitle(title);

        return ResponseEntity.ok(bookImage);
    }

//    @GetMapping("/receive/image/{title}")
//    public ResponseEntity<List<BookImage>> receiveImageByTitle(@PathVariable String title) {
//        List<BookImage> bookImages = imageBookService.receiveImageByTitle(title);
//
//        if (bookImages.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        return new ResponseEntity<>(bookImages, HttpStatus.OK);
//    }

}
