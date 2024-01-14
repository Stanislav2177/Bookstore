package com.project.bookstore.controller;

import com.project.bookstore.dto.BookAndImageDto;
import com.project.bookstore.entity.Book;
import com.project.bookstore.exception.BookAlreadyExistException;
import com.project.bookstore.exception.BookNotFoundException;
import com.project.bookstore.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/add")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        logger.info("Received a request to add a new book: {}", book.getTitle());
        Book addedBook = bookService.addBook(book);
        if (addedBook != null) {
            logger.info("Book added successfully: {}", addedBook.getId());
            return new ResponseEntity<>(addedBook, HttpStatus.CREATED);
        } else {
            logger.error("Failed to add the book: {}", book.getTitle());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/pageable/list")
    public ResponseEntity<Page<Book>> listBooksPage(Pageable pageable) {
        logger.info("Received a request to list books with pageable.");
        Page<Book> books = bookService.getBooks(pageable);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

//    @GetMapping("/all")
//    public ResponseEntity<List<Book>> listBooks() {
//        logger.info("Received a request to list all books.");
//        return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
//    }

    @GetMapping("/all")
    public ResponseEntity<List<Book>> listBooks() {
        logger.info("Received a request to list all books.");

        List<Book> allBooks = bookService.getAllBooks();


        return new ResponseEntity<>(allBooks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        logger.info("Received a request to retrieve a book by ID: {}", id);
        Book book = bookService.searchBookById(id);
        if (book != null) {
            logger.info("Book found: {}", book.getId());
            return new ResponseEntity<>(book, HttpStatus.OK);
        } else {
            logger.warn("Book not found with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        logger.info("Received a request to delete a book by ID: {}", id);
        Book book = bookService.searchBookById(id);

        if (book != null) {
            bookService.deleteBook(id);
            logger.info("Book deleted successfully: {}", id);
            return ResponseEntity.noContent().build();
        } else {
            logger.warn("Book not found for deletion with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/title")
    public ResponseEntity<Book> getBookByTitle(@PathVariable String title) {
        logger.info("Received a request to retrieve a book by title: {}", title);
        Book book = bookService.searchBookByTitle(title);

        if (book != null) {
            logger.info("Book found by title: {}", title);
            return new ResponseEntity<>(book, HttpStatus.OK);
        } else {
            logger.warn("Book not found with title: {}", title);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<Book>> getAllBooksSortedByPrice(
            @RequestParam(name = "parameter") String parameter) {
        logger.info("Received a request to retrieve and sort books by price.");

        try {
            List<Book> sortedBooks = bookService.getAllBooksSortedByPrice(parameter);
            return ResponseEntity.ok(sortedBooks);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid parameter for sorting: {}", parameter);
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body("Invalid parameter for sorting: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Failed to retrieve and sort books: {}", e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Failed to retrieve and sort books: " + e.getMessage());
        }

        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@RequestBody Book newBook, @PathVariable Long id) {
        logger.info("Received a request to update a book with ID: {}", id);
        Book updatedBook;
        try {
            updatedBook = bookService.updateBook(newBook, id);
            logger.info("Book updated successfully: {}", id);
            return ResponseEntity.ok(updatedBook);
        } catch (BookAlreadyExistException e) {
            logger.error("Book update failed due to a conflict: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (BookNotFoundException e) {
            logger.warn("Book not found for update with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/random")
    public ResponseEntity<Book> getARandomBook() {
        logger.info("Received a request to retrieve a random book.");
        Book randomBook;
        try {
            randomBook = bookService.getARandomBook();
            logger.info("Random book retrieved: {}", randomBook.getId());
            return ResponseEntity.ok(randomBook);
        } catch (BookNotFoundException e) {
            logger.warn("No random book found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<Book>> getAllBooksSortedByGenre(@PathVariable String genre) {
        logger.info("Received a request to retrieve books by genre: {}", genre);
        List<Book> booksByGenre;
        try {
            booksByGenre = bookService.getAllBooksSortedByGenre(genre);
            logger.info("Books retrieved by genre: {}", genre);
            return ResponseEntity.ok(booksByGenre);
        } catch (Exception e) {
            logger.error("Failed to retrieve and sort books by genre: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/receive/BookAndImage")
    public ResponseEntity<List<BookAndImageDto>> receiveBookAndImage(){
        return ResponseEntity.ok(bookService.getAllBooksPlusImages());
    }
}
