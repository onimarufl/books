package com.manage.books.service;

import com.manage.books.entity.Books;
import com.manage.books.models.BooksRequest;
import com.manage.books.repository.BooksRepository;
import com.manage.books.utils.DateConvert;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BooksService {
    private final BooksRepository booksRepository;
    private final DateConvert dateConvert;

    public BooksService(BooksRepository booksRepository, DateConvert dateConvert) {
        this.booksRepository = booksRepository;
        this.dateConvert = dateConvert;
    }

    public ResponseEntity<Books> createBooksService(BooksRequest booksRequest) {
        Books books = new Books();
        books.setTitle(booksRequest.getTitle());
        books.setAuthor(booksRequest.getAuthor());
        books.setPublishedDate(dateConvert.dateConvertThaiBuddhistDateToGregorianDate(booksRequest.getPublishedDate()));
        this.booksRepository.save(books);

        return new ResponseEntity<>(books, HttpStatus.CREATED);
    }

    public List<Books> getByAuthorService(String author) {
        return this.booksRepository.findByAuthor(author);
    }
}
