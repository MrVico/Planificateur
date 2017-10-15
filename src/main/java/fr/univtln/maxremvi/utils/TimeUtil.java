package fr.univtln.maxremvi.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 * Created by remi on 15/10/2017.
 */
public class TimeUtil {
    public static String timeToSqlFormat(Date time) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
    }

    public static Date localDateToDate(LocalDate localDate) {
        return java.sql.Date.valueOf(localDate);
    }
}
