package fr.univtln.maxremvi.utils;

import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;

public class TimeManager {
    /***
     * Converts the pattern of a given date to the given pattern and returns it
     *
     * @param pattern The pattern to use
     * @param time The Date to convert
     * @return The formatted date
     */
    public static String format(String pattern, Date time) {
        return new SimpleDateFormat(pattern).format(time);
    }

    /***
     * Transforms a given Date into a Timestamp one (useful for database storage)
     *
     * @param date The date to tranform
     * @return The Timestamp value of the given Date
     */
    public static java.sql.Timestamp timeToSqlFormat(Date date) {
        return (date != null) ? new java.sql.Timestamp(date.getTime()) : null;
    }

    /***
     * Extracts the day/month/year value of a given Date and return it
     *
     * @param time The Date to extract values
     * @return The extracted value
     */
    public static String extractDateString(Date time) {
        return format("dd/MM/yyyy", time);
    }

    /***
     * Extracts the hour:minute value of a given Date and return it
     *
     * @param time The Date to extract values
     * @return The extracted value
     */
    public static String extractHourMinutesString(Date time) {
        return format("HH:mm", time);
    }

    /***
     * Transform a given LocalDateTime into a Date one
     * @param localDate The LocalDateTime to transform
     * @return The transformed LocalDateTime
     */
    public static Date localDateToDate(LocalDateTime localDate) {
        return Date.from(localDate.toInstant(OffsetDateTime.now().getOffset()));
    }
}
