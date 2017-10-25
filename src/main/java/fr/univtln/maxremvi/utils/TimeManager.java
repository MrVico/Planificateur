package fr.univtln.maxremvi.utils;

import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;

/**
 * Created by remi on 15/10/2017.
 */
public class TimeManager {
    private static String format(String pattern, Date time) {
        return new SimpleDateFormat(pattern).format(time);
    }

    public static java.sql.Timestamp timeToSqlFormat(Date date) {
        return (date != null) ? new java.sql.Timestamp(date.getTime()) : null;
    }

    public static String extractDateString(Date time) {
        return format("dd/MM/yyyy", time);
    }

    public static String extractHourMinutesString(Date time) {
        return format("HH:mm", time);
    }

    public static Date localDateToDate(LocalDateTime localDate) {
        return Date.from(localDate.toInstant(OffsetDateTime.now().getOffset()));
    }

    public static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}
