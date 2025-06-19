package com.manage.books.unit_tests;

import com.manage.books.entity.Books;
import com.manage.books.models.BooksRequest;
import com.manage.books.repository.BooksRepository;
import com.manage.books.service.BooksService;
import com.manage.books.utils.DateConvert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.chrono.ThaiBuddhistDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {
    @Mock
    private BooksRepository booksRepository;

    @Mock
    private DateConvert dateConvert;

    @InjectMocks
    private BooksService booksService;

    @Test
    public void TestCreateBooksServiceSuccess() {
        ThaiBuddhistDate parsedBuddhistDate = ThaiBuddhistDate.of(
                2555,
                1,
                2
        );

        LocalDate date = LocalDate.from(parsedBuddhistDate);
        BooksRequest booksRequest = new BooksRequest();
        booksRequest.setTitle("TestTitle");
        booksRequest.setAuthor("TestAuthor");
        booksRequest.setPublishedDate(date);

        ResponseEntity<?> expect = new ResponseEntity<>(booksRequest, HttpStatus.CREATED);
        ResponseEntity<?> Actual = booksService.createBooksService(booksRequest);

        assertEquals(expect.getStatusCode(), Actual.getStatusCode());
    }

    @Test
    public void TestCreateBooksServiceBuddhistCalendarTypeSuccess() {
        LocalDate date = LocalDate.now();
        BooksRequest booksRequest = new BooksRequest();
        booksRequest.setTitle("TestTitle");
        booksRequest.setAuthor("TestAuthor");
        booksRequest.setPublishedDate(date);

        ResponseEntity<?> expect = new ResponseEntity<>(booksRequest, HttpStatus.CREATED);
        ResponseEntity<?> Actual = booksService.createBooksService(booksRequest);

        assertEquals(expect.getStatusCode(), Actual.getStatusCode());
    }

    @Test
    public void TestGetByAuthorServiceSuccess() {
        LocalDate date = LocalDate.now();
        List<Books> booksList = new ArrayList<>();
        Books book = new Books();
        book.setTitle("TestTitle");
        book.setAuthor("TestAuthor");
        book.setPublishedDate(date);
        booksList.add(book);

        when(booksRepository.findByAuthor("TestAuthor")).thenReturn(booksList);
        List<Books> actual = booksService.getByAuthorService("TestAuthor");

        assertEquals(booksList, actual);
    }

}
