package com.manage.books.unit_tests;

import com.manage.books.validate.PublishedYearValidator;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PublishedYearValidatorTest {
    private final PublishedYearValidator publishedYearValidator = new PublishedYearValidator();

    @Test
    public void PublishedYearValidatorSuccessTest(){
        ResponseEntity<?> actual = this.publishedYearValidator.validatePublishedYear(LocalDate.now(),"GREGORIAN");
        assertNull(actual);
    }

    @Test
    public void PublishedYearValidatorGregorianNeedMoreThan1000YearTest(){
        ResponseEntity<?> actual = this.publishedYearValidator.validatePublishedYear(LocalDate.now().minusYears(2000),"GREGORIAN");
        assertEquals(HttpStatus.BAD_REQUEST,actual.getStatusCode());
    }

    @Test
    public void PublishedYearValidatorBuddhistNeedMoreThan1543YearTest(){
        ResponseEntity<?> actual = this.publishedYearValidator.validatePublishedYear(LocalDate.now().minusYears(2000),"GREGORIAN");
        assertEquals(HttpStatus.BAD_REQUEST,actual.getStatusCode());
    }

    @Test
    public void PublishedYearValidatorCalendarTypeIsBlankTest(){
        ResponseEntity<?> actual = this.publishedYearValidator.validatePublishedYear(LocalDate.now().minusYears(2000),"");
        assertEquals(HttpStatus.BAD_REQUEST,actual.getStatusCode());
    }

    @Test
    public void PublishedYearValidatorRequireCalendarTypeTest(){
        ResponseEntity<?> actual = this.publishedYearValidator.validatePublishedYear(LocalDate.now().minusYears(2000),"aa");
        assertEquals(HttpStatus.BAD_REQUEST,actual.getStatusCode());
    }

    @Test
    public void PublishedYearValidatorAfterCurrentDateTest(){
        ResponseEntity<?> actual = this.publishedYearValidator.validatePublishedYear(LocalDate.now().plusYears(1),"GREGORIAN");
        assertEquals(HttpStatus.BAD_REQUEST,actual.getStatusCode());
    }
}
