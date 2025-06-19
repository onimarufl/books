package com.manage.books.utils;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.chrono.ThaiBuddhistDate;

@Service
public class DateConvert {

    public LocalDate dateConvertThaiBuddhistDateToGregorianDate(LocalDate thaiBuddhistDate) {
        ThaiBuddhistDate parsedBuddhistDate = ThaiBuddhistDate.of(
                thaiBuddhistDate.getYear(),
                thaiBuddhistDate.getMonthValue(),
                thaiBuddhistDate.getDayOfMonth()
        );

        return LocalDate.from(parsedBuddhistDate);
    }
    
}
