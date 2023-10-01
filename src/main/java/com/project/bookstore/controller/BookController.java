package com.project.bookstore.controller;

import com.project.bookstore.entity.Book;
import com.project.bookstore.exception.BookAlreadyExistException;
import com.project.bookstore.exception.BookNotFoundException;
import com.project.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/add")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book addedBook = bookService.addBook(book);
        if (addedBook != null) {
            return new ResponseEntity<>(addedBook, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/pageable/list")
    public ResponseEntity<Page<Book>> listBooksPage(Pageable pageable) {
        Page<Book> books = bookService.getBooks(pageable);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Book>> listBooks(){
        return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.searchBookById(id);
        if (book != null) {
            return new ResponseEntity<>(book, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        Book book = bookService.searchBookById(id);

        if (book != null) {
            bookService.deleteBook(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/title")
    public ResponseEntity<Book> getBookByTitle(@PathVariable String title){
        Book book = bookService.searchBookByTitle(title);

        if(book != null){
            return new ResponseEntity<>(book, HttpStatus.OK);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/sorted")
    public ResponseEntity<String> getAllBooksSortedByPrice(
            @RequestParam(name = "parameter") String parameter) {
        try {
            List<Book> sortedBooks = bookService.getAllBooksSortedByPrice(parameter);
            return ResponseEntity.ok(sortedBooks.toString());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body("Invalid parameter. Use 'min' or 'max'.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve and sort books: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@RequestBody Book newBook, @PathVariable Long id) {
        Book updatedBook;
        try {
            updatedBook = bookService.updateBook(newBook, id);
            return ResponseEntity.ok(updatedBook);
        } catch (BookAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (BookNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/random")
    public ResponseEntity<Book> getARandomBook() {
        Book randomBook;
        try {
            randomBook = bookService.getARandomBook();
            return ResponseEntity.ok(randomBook);
        } catch (BookNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<Book>> getAllBooksSortedByGenre(@PathVariable String genre) {
        List<Book> booksByGenre;
        try {
            booksByGenre = bookService.getAllBooksSortedByGenre(genre);
            return ResponseEntity.ok(booksByGenre);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}

