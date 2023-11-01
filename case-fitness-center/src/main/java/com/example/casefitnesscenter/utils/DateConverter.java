package com.example.casefitnesscenter.utils;



import com.example.casefitnesscenter.common.exception.AppRuntimeException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;

import static com.example.casefitnesscenter.constant.AppFitnessConstant.messageFormat;


public final class DateConverter {
    public static final String TIMEZONE = "Asia/Jakarta";
    public static final String DATETIME_FORMAT = "dd-MM-yyyy HH:mm:ss z";
    public static final String DATE_FORMAT = "dd-MM-yyyy";
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String MONTH_YEARS_FORMAT = "MM/yyyy";
    public static final ZoneId zoneId;
    public static final String TIMESTAMP_NOW = DateConverter.formatDate(DateConverter.DATETIME_FORMAT, new Date(System.currentTimeMillis()).toInstant());

    static {
        zoneId = ZoneId.of(TIMEZONE);
    }

    private DateConverter() {
    }

    public static LocalDateTime toLocalDateTime(Date dateToConvert) {
        return LocalDateTime.ofInstant(
                dateToConvert.toInstant(),
                zoneId);
    }

    public static String formatDate(String format, TemporalAccessor temporalAccessor) {
        return DateTimeFormatter
                .ofPattern(format)
                .withZone(ZoneId.of(TIMEZONE))
                .format(temporalAccessor);
    }

    public static Date today() {
        var cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 0);
        return cal.getTime();
    }

    public static Date parseToDate(String date, String formatDate) {
        try {
            Date deliveryDate = null;
            var dateFormat = new SimpleDateFormat(formatDate);
            var parse = dateFormat.parse(date);
            long time = parse.getTime();
            if (time != 0) {
                deliveryDate = new Date(time);
            }

            return deliveryDate;
        } catch (ParseException e) {
            String message = messageFormat("Could not parse date time format {}", date);
            throw new AppRuntimeException(message);
        }
    }

    public static Date toDate(LocalDate localDate) {
        Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }
}
