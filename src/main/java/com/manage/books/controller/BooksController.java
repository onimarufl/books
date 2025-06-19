package com.manage.books.controller;

import com.manage.books.entity.Books;
import com.manage.books.models.BooksRequest;
import com.manage.books.service.BooksService;
import com.manage.books.utils.DateConvert;
import com.manage.books.validate.PublishedYearValidator;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/books")
public class BooksController {

    private final BooksService booksService;
    private final PublishedYearValidator publishedYearValidator;

    public BooksController(BooksService booksService, PublishedYearValidator publishedYearValidator, DateConvert dateConvert) {
        this.booksService = booksService;
        this.publishedYearValidator = publishedYearValidator;
    }

    @PostMapping
    public ResponseEntity<?> createBook(@Valid @RequestBody BooksRequest booksRequest) {
        ResponseEntity<?> response;
        if (booksRequest.getPublishedDate() != null) {
            response = publishedYearValidator.validatePublishedYear(booksRequest.getPublishedDate());
            if (response == null) {
                response = this.booksService.createBooksService(booksRequest);
            }
        } else {
            response = this.booksService.createBooksService(booksRequest);
        }
        return response;
    }

    @GetMapping
    public List<Books> getBookByAuthor(@RequestParam String author) {
        return this.booksService.getByAuthorService(author);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
