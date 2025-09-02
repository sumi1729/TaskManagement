package com.Taskmanagement.database;

import androidx.room.TypeConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Converters {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @TypeConverter
    public static LocalDateTime stringToLocalDateTime(String value) {
        return value == null ? null : LocalDateTime.parse(value, formatter);
    }

    @TypeConverter
    public static String localDateTimeToString(LocalDateTime dateTime) {
        return dateTime == null ? null : dateTime.format(formatter);
    }

    private static final DateTimeFormatter formatter4date = DateTimeFormatter.ISO_LOCAL_DATE;

    @TypeConverter
    public static LocalDate stringToLocalDate(String value) {
        return value == null ? null : LocalDate.parse(value, formatter4date);
    }

    @TypeConverter
    public static String localDateToString(LocalDate dateTime) {
        return dateTime == null ? null : dateTime.format(formatter4date);
    }

    private static final DateTimeFormatter formatter4time = DateTimeFormatter.ISO_LOCAL_TIME;

    @TypeConverter
    public static LocalTime stringToLocalTime(String value) {
        return value == null ? null : LocalTime.parse(value, formatter4time);
    }

    @TypeConverter
    public static String localTimeToString(LocalTime time) {
        return time == null ? null : time.format(formatter4time);
    }
}
