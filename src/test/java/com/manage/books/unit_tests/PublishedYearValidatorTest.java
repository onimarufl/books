package com.manage.books.unit_tests;

import com.manage.books.utils.DateConvert;
import com.manage.books.validate.PublishedYearValidator;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PublishedYearValidatorTest {

    private final DateConvert dateConvert = new DateConvert();
    private final PublishedYearValidator publishedYearValidator = new PublishedYearValidator(dateConvert);

    @Test
    public void PublishedYearValidatorSuccessTest() {
        ResponseEntity<?> actual = this.publishedYearValidator.validatePublishedYear(LocalDate.now());
        assertNull(actual);
    }

    @Test
    public void PublishedYearValidatorNeedMoreThan1000YearTest(){
        ResponseEntity<?> actual = this.publishedYearValidator.validatePublishedYear(LocalDate.of(900,1,1));
        assertNotNull(actual);
    }

    @Test
    public void PublishedYearValidatorAfterCurrentDateTest(){
        ResponseEntity<?> actual = this.publishedYearValidator.validatePublishedYear(LocalDate.now().plusYears(600));
        assertNotNull(actual);
    }
}