package com.lecture10.Assignment01.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static LocalDate parseData(String dateStr){
        try{
            return LocalDate.parse(dateStr, formatter);
        } catch (DateTimeParseException e){
            return null;
        }
    }

    public static String formatDate(LocalDate date){
        return date.format(formatter);
    }
}