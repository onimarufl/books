package com.manage.books.validate;

import com.manage.books.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class PublishedYearValidator {
    public ResponseEntity<Map<String, String>> validatePublishedYear(LocalDate publishedDate, String calendarType) {
        Map<String, String> error = new HashMap<>();
        LocalDate publishedDateConvertToGregorian;
        if (calendarType.equals(Constants.BUDDHIST_CALENDAR_TYPE)) {
            publishedDateConvertToGregorian = publishedDate.minusYears(Constants.BUDDHIST_YEAR_VALUE);
        } else {
            publishedDateConvertToGregorian = publishedDate;
        }

        if (calendarType.isBlank()) {
            String fieldName = "calendarType";
            String errorMessage = "calendarType is blank! calendarType required value "
                    + Constants.GREGORIAN_CALENDAR_TYPE +
                    " or "
                    + Constants.BUDDHIST_CALENDAR_TYPE;
            error.put(fieldName, errorMessage);
        } else if (!calendarType.equals(Constants.GREGORIAN_CALENDAR_TYPE) && !calendarType.equals(Constants.BUDDHIST_CALENDAR_TYPE)) {
            String fieldName = "calendarType";
            String errorMessage = "calendarType required value "
                    + Constants.GREGORIAN_CALENDAR_TYPE +
                    " or "
                    + Constants.BUDDHIST_CALENDAR_TYPE;
            error.put(fieldName, errorMessage);
        }

        if (calendarType.equals(Constants.BUDDHIST_CALENDAR_TYPE) && publishedDate.getYear() <= (Constants.BUDDHIST_YEAR_VALUE + Constants.GREGORIAN_AFTER_VALUE)) {
            String fieldName = "publishedDate";
            String errorMessage = "publishedYear " + Constants.BUDDHIST_CALENDAR_TYPE
                    + " need more than "
                    + (Constants.BUDDHIST_YEAR_VALUE + Constants.GREGORIAN_AFTER_VALUE)
                    + " year";
            error.put(fieldName, errorMessage);
        } else if (publishedDate.getYear() <= Constants.GREGORIAN_AFTER_VALUE) {
            String fieldName = "publishedDate";
            String errorMessage = "publishedYear " + Constants.GREGORIAN_CALENDAR_TYPE
                    + " need more than "
                    + Constants.GREGORIAN_AFTER_VALUE
                    + " year";
            error.put(fieldName, errorMessage);
        } else if (publishedDateConvertToGregorian.isAfter(LocalDate.now())) {
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
