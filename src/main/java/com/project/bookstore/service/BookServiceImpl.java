package com.project.bookstore.service;

import com.project.bookstore.dto.BookAndImageDto;
import com.project.bookstore.entity.Book;
//import com.project.bookstore.entity.ImageBook;
import com.project.bookstore.entity.BookImage;
import com.project.bookstore.exception.BookAlreadyExistException;
import com.project.bookstore.exception.BookNotFoundException;
import com.project.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private final BookRepository repository;
    private final BookImageService bookImageService;


    public BookServiceImpl(BookRepository repository, BookImageService imageBookService) {
        this.repository = repository;
        this.bookImageService = imageBookService;
    }
    @Override
    public Book addBook(Book book) {
        try{
            List<Book> allBooks = getAllBooks();

            if(!allBooks.contains(book)){
//                System.out.println(book.getImageBook());
//                imageBookService.downloadImage(book.getImageBook().toString());

                repository.save(book);
                return book;
            }

            return null;
        }catch (Exception e){
            throw new BookAlreadyExistException("Book with title " + book.getTitle() + " already exist");
        }
    }
    @Override
    public Page<Book> getBooks(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public void deleteBook(Long id) {
        try{
            repository.deleteById(id);
        }catch (Exception e){
            throw new BookNotFoundException("Book with ID" + id + " don't exist  ");
        }
    }

    @Override
    public Book searchBookByTitle(String title) {
        try {
            List<Book> allBooks = getAllBooks();

            for (Book book : allBooks) {
                if(book.getTitle().equals(title)){
                    return book;
                }
            }

            return null;
        }catch (Exception e){
            throw new BookNotFoundException("Book with title " + title + " don't exist");
        }
    }

    @Override
    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    @Override
    public List<BookAndImageDto> getAllBooksPlusImages(){
        List<Book> books = repository.findAll();
        List<BookAndImageDto> all = new ArrayList<>();
        BookAndImageDto result = new BookAndImageDto();

        System.out.println(books.size());

        for (Book book : books) {
            BookImage bookImage =
                    bookImageService.receiveImageByTitle(book.getTitle());
            System.out.println("bookimage " + bookImage);
            System.out.println("book " + book.getTitle());

            if(bookImage != null){
                result.setTitle(book.getTitle());
                result.setPrice(book.getPrice());
                result.setDescription(book.getDescription());
                result.setAuthor(book.getAuthor());
                result.setGenre(book.getGenre());
                result.setQuantity(book.getQuantity());
                result.setEncoded(bookImage.getEncoded());
                all.add(result);
            }

        }

        System.out.println(all.size());

        return all;
    }

    @Override
    public List<Book> getAllBooksSortedByPrice(String parameter) {
        try{
            List<Book> allBooks = getAllBooks();

            if(parameter.equals("min")){
                allBooks.sort(Comparator
                        .comparingDouble(Book::getPrice));
            } else if (parameter.equals("max")) {
                allBooks.sort(Comparator
                        .comparingDouble(Book::getPrice).reversed());
            }

            return allBooks;
        }catch (Exception e){
            throw new RuntimeException();
        }
    }

    @Override
    public Book updateBook(Book newBook, Long id) {
        try {
            Book oldBook = searchBookById(id);

            if (newBook.equals(oldBook)) {
                throw new BookAlreadyExistException("Book is up to date");
            }

            oldBook.setId(newBook.getId());
            oldBook.setAuthor(newBook.getAuthor());
            oldBook.setPrice(newBook.getPrice());
            oldBook.setGenre(newBook.getGenre());
            oldBook.setDescription(newBook.getDescription());
            oldBook.setTitle(newBook.getTitle());

            return oldBook;
        } catch (Exception e) {
            throw new BookNotFoundException("Book with id " + id + " cannot be found");
        }
    }

    @Override
    public Book getARandomBook() {
        List<Book> allBooks = getAllBooks();

        if (allBooks.isEmpty()) {
            throw new BookNotFoundException("No books found in the database");
        }

        Random random = new Random();
        int randomIndex = random.nextInt(allBooks.size());

        return allBooks.get(randomIndex);
    }

    @Override
    public List<Book> getAllBooksSortedByGenre(String genre) {
        try {
            List<Book> allBooks = getAllBooks();
            List<Book> filtered = new ArrayList<>();

            for (Book book : allBooks) {
                if(book.getGenre().equals(genre)){
                    filtered.add(book);
                }
            }

            return filtered;
        }catch (Exception e){
            throw new RuntimeException();
        }
    }

    @Override
    public Book searchBookById(Long id) {
        try {
            Optional<Book> foundBook = getAllBooks()
                    .stream()
                    .filter(book -> Objects.equals(book.getId(), id))
                    .findFirst();

            return foundBook.orElse(null);

        } catch (Exception e) {
            throw new BookNotFoundException("Book with ID " + id + " not found");
        }
    }

    @Override
    public void updateQuantity(Long id, int newQuantity){
        Book book = searchBookById(id);
        book.setQuantity(newQuantity);
        repository.save(book);
    }
}
