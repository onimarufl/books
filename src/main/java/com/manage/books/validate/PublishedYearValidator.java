package com.manage.books.validate;

import com.manage.books.utils.Constants;
import com.manage.books.utils.DateConvert;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class PublishedYearValidator {

    private final DateConvert dateConvert;

    public PublishedYearValidator(DateConvert dateConvert) {
        this.dateConvert = dateConvert;
    }


    public ResponseEntity<Map<String, String>> validatePublishedYear(LocalDate publishedDate) {
        Map<String, String> error = new HashMap<>();

        if (publishedDate.getYear() <= Constants.THAI_BUDDHIST_DATE_AFTER_VALUE) {
            String fieldName = "publishedDate";
            String errorMessage = "publishedYear need more than "
                    + Constants.THAI_BUDDHIST_DATE_AFTER_VALUE
                    + " year";
            error.put(fieldName, errorMessage);
        } else if (dateConvert.dateConvertThaiBuddhistDateToGregorianDate(publishedDate).isAfter(LocalDate.now())) {
            String fieldName = "publishedDate";
            String errorMessage = "publishedYear after currentDate";
            error.put(fieldName, errorMessage);
        }

        if (!error.isEmpty()) {
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        return null;
    }

}
