package com.manage.books.unit_tests;

import com.manage.books.models.BooksRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookRequestValidateTest {

    private Validator validator;

    @BeforeEach
    void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void TestValidBookRequest() {
        LocalDate date = LocalDate.now();
        BooksRequest booksRequest = new BooksRequest();
        booksRequest.setTitle("TestTitle");
        booksRequest.setAuthor("TestAuthor");
        booksRequest.setPublishedDate(date);

        Set<ConstraintViolation<BooksRequest>> violations = validator.validate(booksRequest);
        assertTrue(violations.isEmpty(), "Valid user should have no violations.");
    }

    @Test
    public void TestInvalidBookRequestTitle() {
        LocalDate date = LocalDate.now();
        BooksRequest booksRequest = new BooksRequest();
        booksRequest.setTitle("");
        booksRequest.setAuthor("TestAuthor");
        booksRequest.setPublishedDate(date);

        Set<ConstraintViolation<BooksRequest>> violations = validator.validate(booksRequest);
        assertFalse(violations.isEmpty(), "Title is required");
    }

    @Test
    public void TestInvalidBookRequestAuthor() {
        LocalDate date = LocalDate.now();
        BooksRequest booksRequest = new BooksRequest();
        booksRequest.setTitle("TestTitle");
        booksRequest.setAuthor("");
        booksRequest.setPublishedDate(date);

        Set<ConstraintViolation<BooksRequest>> violations = validator.validate(booksRequest);
        assertFalse(violations.isEmpty(), "Author is required");
    }

}
