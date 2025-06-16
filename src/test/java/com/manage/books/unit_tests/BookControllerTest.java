package com.manage.books.unit_tests;

import com.manage.books.controller.BooksController;
import com.manage.books.entity.Books;
import com.manage.books.models.BooksRequest;
import com.manage.books.service.BooksService;
import com.manage.books.validate.PublishedYearValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookControllerTest {
    @Mock
    private BooksService booksService;
    @Mock
    private PublishedYearValidator publishedYearValidator;
    @InjectMocks
    private BooksController booksController;

    @Test
    public void TestCreateBookControllerSuccess() {
        LocalDate date = LocalDate.now();
        BooksRequest booksRequest = new BooksRequest();
        booksRequest.setTitle("TestTitle");
        booksRequest.setAuthor("TestAuthor");
        booksRequest.setPublishedDate(date);
        booksRequest.setCalendarType("GREGORIAN");

        Books books = new Books();
        books.setTitle("TestTitle");
        books.setAuthor("TestAuthor");
        books.setPublishedDate(date);

        when(publishedYearValidator.validatePublishedYear(
                booksRequest.getPublishedDate(),
                booksRequest.getCalendarType()
        )).thenReturn(null);
        when(booksService.createBooksService(booksRequest)).thenReturn(new ResponseEntity<Books>(books, HttpStatus.CREATED));
        ResponseEntity<?> actual = booksController.createBook(booksRequest);

        assertEquals(HttpStatus.CREATED, actual.getStatusCode());
    }

    @Test
    public void TestGetBookControllerSuccess() {
        LocalDate date = LocalDate.now();
        List<Books> booksList = new ArrayList<>();
        Books book = new Books();
        book.setTitle("TestTitle");
        book.setAuthor("TestAuthor");
        book.setPublishedDate(date);
        booksList.add(book);

        when(booksService.getByAuthorService("TestAuthor")).thenReturn(booksList);
        List<Books> actual = booksController.getBookByAuthor("TestAuthor");

        assertEquals(booksList, actual);
    }
}
