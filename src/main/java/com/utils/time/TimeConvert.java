package com.utils.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.TimeZone;

/**
 * @Author Wang Junwei
 * @Date 2023/4/25 11:43
 * @Description 时间转换
 */
public class TimeConvert {

    public static void main(String[] args) throws ParseException {
        long d1 = ChronoUnit.DAYS.between(LocalDateTime.now(), ZonedDateTime.now());
        long d2 = ChronoUnit.DAYS.between(OffsetDateTime.now(), ZonedDateTime.now());
        long d3 = ChronoUnit.DAYS.between(ZonedDateTime.now(), OffsetDateTime.now());
        long d4 = ChronoUnit.DAYS.between(ZonedDateTime.now(), LocalDateTime.now());

        zonedDateTime();
    }

    public static void date2Instant() {
        Date date = new Date();
        // 1. Date兼容了Instant，可以直接转
        Instant instant = date.toInstant();
        // 2. 通过时间戳初始化
        Instant.ofEpochMilli(date.getTime());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // SimpleDateFormat中使用当前系统的默认时区输出时间，所以需要把时区转为UTC时区才能保证两者区时一致
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        System.out.println(simpleDateFormat.format(date));
        System.out.println(instant);
    }

    public static void instant2Date() {
        Instant instant = Instant.now();
        // 1. 构造方法转换
        Date date = Date.from(instant);
        // 2. 通过时间戳初始化
        new Date(instant.toEpochMilli());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        System.out.println(simpleDateFormat.format(date));
        System.out.println(instant);
    }

    public static void date2Time() {
        Date date = new Date();
        Instant instant = date.toInstant();
        // 1. 通过添加时区、偏移量信息直接转换
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("UTC"));
        OffsetDateTime offsetDateTime = instant.atOffset(ZoneOffset.UTC);

        // 2. 通过构造方法
        ZonedDateTime.ofInstant(instant, ZoneId.of("UTC"));
        OffsetDateTime.ofInstant(instant, ZoneOffset.UTC);
        LocalDateTime.ofInstant(instant, ZoneOffset.UTC);

        // 3. 通过时间戳转换
        long timestamp = date.getTime();
        // 这里就是Date转Instant，Instant转LocalDateTime的源代码实现
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(Math.floorDiv(timestamp, 1000), (int) Math.floorMod(timestamp, 1000), ZoneOffset.UTC);
        // 然后通过LocalDateTime转其它类
        LocalDate localDate = localDateTime.toLocalDate();
        LocalTime localTime = localDateTime.toLocalTime();
        ZonedDateTime zonedDateTime1 = localDateTime.atZone(ZoneId.of("UTC"));
        OffsetDateTime offsetDateTime1 = localDateTime.atOffset(ZoneOffset.UTC);

        // 4. 字符串格式化
        String pattern = "yyyy-MM-dd HH:mm:ss X";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String format = simpleDateFormat.format(date);
        LocalDateTime.parse(format, dateTimeFormatter);
        OffsetDateTime.parse(format, dateTimeFormatter);
        ZonedDateTime.parse(format, dateTimeFormatter);
    }

    public static void time2Date() throws ParseException {
        LocalDateTime localDateTime = LocalDateTime.now();
        OffsetDateTime offsetDateTime = OffsetDateTime.now();
        ZonedDateTime zonedDateTime = ZonedDateTime.now();

        // 1. 转Instant，再转Date
        Instant instant = localDateTime.toInstant(ZoneOffset.ofHours(8));
        offsetDateTime.toInstant();
        zonedDateTime.toInstant();
        Date date = Date.from(instant);

        // 2. 时间戳转Date，这种转换会损失纳秒精度
        long epochSecond = localDateTime.toEpochSecond(ZoneOffset.ofHours(8)) * 1000L;
        offsetDateTime.toEpochSecond();
        zonedDateTime.toEpochSecond();
        Date date1 = new Date(epochSecond);

        // 3. 字符串格式化
        String local = "yyyy-MM-dd HH:mm:ss";
        String offset = "yyyy-MM-dd HH:mm:ss X";
        String zone = "yyyy-MM-dd HH:mm:ss z";

        System.out.println(new SimpleDateFormat(local).parse(DateTimeFormatter.ofPattern(local).format(localDateTime)));
        System.out.println(new SimpleDateFormat(offset).parse(DateTimeFormatter.ofPattern(offset).format(offsetDateTime)));
        System.out.println(new SimpleDateFormat(zone).parse(DateTimeFormatter.ofPattern(zone).format(zonedDateTime)));
    }

    public static void instant() {
        Instant instant = Instant.now();
        // 实例对象转换
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("UTC"));
        OffsetDateTime offsetDateTime = instant.atOffset(ZoneOffset.UTC);

        // 构造方法转换
        ZonedDateTime.ofInstant(instant, ZoneId.of("UTC"));
        OffsetDateTime.ofInstant(instant, ZoneOffset.UTC);
        LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
    }

    public static void localDateTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        ZoneOffset zoneOffset = ZoneOffset.ofHours(8);
        // 通过实例对象转换
        Instant instant = localDateTime.toInstant(zoneOffset);
        LocalDate localDate = localDateTime.toLocalDate();
        LocalTime localTime = localDateTime.toLocalTime();
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneOffset);
        OffsetDateTime offsetDateTime = localDateTime.atOffset(zoneOffset);
        // 通过构造方法转换
        Instant.ofEpochSecond(localDateTime.toEpochSecond(zoneOffset));
        LocalDate.from(localDateTime);
        LocalTime.from(localDateTime);
        OffsetDateTime.of(localDateTime, zoneOffset);
        ZonedDateTime.of(localDateTime, zoneOffset);
    }

    public static void offsetDateTime() {
        OffsetDateTime offsetDateTime = OffsetDateTime.now();
        // 通过实例对象转换
        Instant instant = offsetDateTime.toInstant();
        OffsetTime offsetTime = offsetDateTime.toOffsetTime();
        LocalDate localDate = offsetDateTime.toLocalDate();
        LocalTime localTime = offsetDateTime.toLocalTime();
        LocalDateTime localDateTime = offsetDateTime.toLocalDateTime();
        ZonedDateTime zonedDateTime = offsetDateTime.toZonedDateTime();
        // 通过构造方法转换
        Instant.from(offsetDateTime);
        OffsetTime.from(offsetDateTime);
        LocalDate.from(offsetDateTime);
        LocalTime.from(offsetDateTime);
        LocalDateTime.from(offsetDateTime);
        ZonedDateTime.from(offsetDateTime);
    }

    public static void zonedDateTime() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        // 通过实例对象转换
        Instant instant = zonedDateTime.toInstant();
        LocalDate localDate = zonedDateTime.toLocalDate();
        LocalTime localTime = zonedDateTime.toLocalTime();
        LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();
        OffsetDateTime offsetDateTime = zonedDateTime.toOffsetDateTime();
        // 通过构造方法转换
        Instant.from(zonedDateTime);
        OffsetTime.from(zonedDateTime);
        OffsetDateTime.from(zonedDateTime);
        LocalDate.from(zonedDateTime);
        LocalTime.from(zonedDateTime);
        LocalDateTime.from(zonedDateTime);
    }

}
