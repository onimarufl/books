package com.manage.books.service;

import com.manage.books.entity.Books;
import com.manage.books.models.BooksRequest;
import com.manage.books.repository.BooksRepository;
import com.manage.books.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BooksService {
    private static final String BUDDHIST_VALUE = "BUDDHIST_YEAR";

    @Autowired
    private BooksRepository booksRepository;

    public ResponseEntity<Books> createBooksService(BooksRequest booksRequest) {
        Books books = new Books();
        books.setTitle(booksRequest.getTitle());
        books.setAuthor(booksRequest.getAuthor());
        books.setPublishedDate(
                booksRequest.getCalendarType()
                        .equals(BUDDHIST_VALUE)
                        ? booksRequest.getPublishedDate().minusYears(Constants.BUDDHIST_YEAR_VALUE) : booksRequest.getPublishedDate());
        this.booksRepository.save(books);

        return new ResponseEntity<>(books, HttpStatus.CREATED);
    }

    public List<Books> getByAuthorService(String author) {
        return this.booksRepository.findByAuthor(author);
    }
}
