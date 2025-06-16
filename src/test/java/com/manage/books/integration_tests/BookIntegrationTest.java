package com.manage.books.integration_tests;

import com.manage.books.controller.BooksController;
import com.manage.books.entity.Books;
import com.manage.books.models.BooksRequest;
import com.manage.books.service.BooksService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
public class BookIntegrationTest {

    @Autowired
    private BooksController booksController;

    @Autowired
    private BooksService booksService;

    @Test
    public void createBookIntegrationTest() {
        BooksRequest booksRequest = new BooksRequest();
        booksRequest.setTitle("TestTitle");
        booksRequest.setAuthor("TestAuthor");
        booksRequest.setPublishedDate(LocalDate.now());
        booksRequest.setCalendarType("GREGORIAN");

        ResponseEntity<?> actual = booksController.createBook(booksRequest);
        assertEquals(HttpStatus.CREATED, actual.getStatusCode());

    }

    @Test
    public void GetBookIntegrationTest() {
        BooksRequest booksRequest = new BooksRequest();
        booksRequest.setTitle("TestTitleGet");
        booksRequest.setAuthor("TestAuthorGet");
        booksRequest.setPublishedDate(LocalDate.now());
        booksRequest.setCalendarType("GREGORIAN");

        this.booksService.createBooksService(booksRequest);

        List<Books> actual = booksController.getBookByAuthor(booksRequest.getAuthor());
        assertNotNull(actual);

    }
}
