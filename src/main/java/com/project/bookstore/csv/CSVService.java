package com.project.bookstore.csv;

import java.io.IOException;
import java.util.List;

import com.project.bookstore.entity.Book;
import com.project.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



@Service
public class CSVService {
    @Autowired
    BookRepository repository;

    public void save(MultipartFile file) {
        try {
            List<Book> books = CSVHelper.csvtoBooks(file.getInputStream());
            repository.saveAll(books);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    public List<Book> getAllTutorials() {
        return repository.findAll();
    }
}
