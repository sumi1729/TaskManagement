package com.Taskmanagement.database;

import static com.Taskmanagement.util.CommonUtility.DATE_TIME_FORMATTER_ISO_LOCAL_DATE;
import static com.Taskmanagement.util.CommonUtility.DATE_TIME_FORMATTER_ISO_LOCAL_DATE_TIME;
import static com.Taskmanagement.util.CommonUtility.DATE_TIME_FORMATTER_ISO_LOCAL_TIME;

import androidx.room.TypeConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Converters {

    @TypeConverter
    public static LocalDateTime stringToLocalDateTime(String value) {
        return value == null ? null : LocalDateTime.parse(value, DATE_TIME_FORMATTER_ISO_LOCAL_DATE_TIME);
    }

    @TypeConverter
    public static String localDateTimeToString(LocalDateTime dateTime) {
        return dateTime == null ? null : dateTime.format(DATE_TIME_FORMATTER_ISO_LOCAL_DATE_TIME);
    }

    @TypeConverter
    public static LocalDate stringToLocalDate(String value) {
        return value == null ? null : LocalDate.parse(value, DATE_TIME_FORMATTER_ISO_LOCAL_DATE);
    }

    @TypeConverter
    public static String localDateToString(LocalDate dateTime) {
        return dateTime == null ? null : dateTime.format(DATE_TIME_FORMATTER_ISO_LOCAL_DATE);
    }

    @TypeConverter
    public static LocalTime stringToLocalTime(String value) {
        return value == null ? null : LocalTime.parse(value, DATE_TIME_FORMATTER_ISO_LOCAL_TIME);
    }

    @TypeConverter
    public static String localTimeToString(LocalTime time) {
        return time == null ? null : time.format(DATE_TIME_FORMATTER_ISO_LOCAL_TIME);
    }
}
