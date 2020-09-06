package com.studentdating.studentdating;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Locale;

@Component
public class DateTimeFormatter implements Formatter<LocalDate> {
    @Override
    public LocalDate parse(String text, Locale locale) {
        return LocalDate.parse(text, java.time.format.DateTimeFormatter.ISO_LOCAL_DATE);
    }

    @Override
    public String print(LocalDate object, Locale locale) {
        return object.format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
