package fr.univtln.maxremvi.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * Created by remi on 15/10/2017.
 */
public class TimeUtil {
    private static String format(String pattern, Date time) {
        return new SimpleDateFormat(pattern).format(time);
    }

    public static String timeToSqlFormat(Date time) {
        return format("yyyy-MM-dd HH:mm:ss", time);
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
}
