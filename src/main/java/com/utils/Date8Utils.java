package com.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.Objects;

/**
 * @Author Wang Junwei
 * @Date 2022/11/1 10:24
 * @Description 时间工具
 */
public class Date8Utils {

    /**
     * Date转LocalDateTime
     * @param date
     * @return
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return toLocalDateTime(date, ZoneId.of("+8"));
    }

    public static LocalDateTime toLocalDateTime(Date date, ZoneId zoneId) {
        Objects.requireNonNull(date, "date");
        Objects.requireNonNull(date, "zoneId");
        Instant instant = date.toInstant();
        return instant.atZone(zoneId).toLocalDateTime();
//        TimeZone.getTimeZone("CTT").toZoneId();
//        return LocalDateTime.ofInstant(instant, zoneId);
    }

    /**
     * 计算时间和当前时间的间隔
     * @param localDateTime
     * @return
     */
    public static long getYearFromNow(LocalDateTime localDateTime) {
        return getLongFromNow(localDateTime, ChronoUnit.YEARS);
    }

    public static long getLongFromNow(LocalDateTime localDateTime, TemporalUnit temporalUnit) {
        Objects.requireNonNull(localDateTime, "localDateTime");
        Objects.requireNonNull(temporalUnit, "temporalUnit");
        return localDateTime.until(LocalDateTime.now(), temporalUnit);
    }

}
