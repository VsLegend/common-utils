package com.utils.time;

import sun.util.calendar.ZoneInfoFile;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.time.zone.ZoneRules;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author Wang Junwei
 * @Date 2023/2/28 16:52
 * @Description 时间相关
 */
public class TimeUsage {

    /**
     * UTC时区
     */
    private static void timeZoneUTC() {
        // 世界标准时：
        // UTC（Coordinated Universal Time，世界标准时，世界协调时）是最主要的世界时间标准。UTC基于国际原子时，并通过不规则的加入闰秒来抵消地球自转变慢的影响。
        // GMT（Greenwich Mean Time，格林尼治时间），由于地球每天的自转是有些不规则的，而且正在缓慢减速，因此格林尼治平时基于天文观测本身的缺陷，目前已经被原子钟报时的协调世界时（UTC）所取代。
        // UT(Universal Time，世界时)，是一种以格林威治子夜起算的平太阳时。世界时是以地球自转为基准得到的时间尺度，其精度受到地球自转不均匀变化和极移的影响。


        // 基础模块，基本上jdk8的时间都会于它打交道
        // Instant.now()代表UTC当前的时间
        Instant instant = Instant.now();
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("America/New_York"));
        ZonedDateTime.ofInstant(instant, ZoneId.of("America/New_York"));

        OffsetDateTime offsetDateTime = instant.atOffset(ZoneOffset.ofHours(5));
        OffsetDateTime.ofInstant(instant, ZoneId.of("+5"));

        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        LocalDateTime.now();
        System.out.println("UTC时区当前时间：" + instant);
        System.out.println("指定时区的时间：" + zonedDateTime);
        System.out.println("指定偏移量的时间：" + offsetDateTime);
        System.out.println("程序执行的本地地区时间：" + localDateTime);
    }
    /**
     * 地区时
     */
    private static void timeZoneRegion() {

        // 地区时：
        // 由于每个国家所在的位置、时区不同，由此产生了各地的地方时区，世界标准时以伦敦为测量点，地方时区切换大都以当地为测量点。
        // 中国占据东五区、东六区、东七区、东八区、东九区5个时区，为了统一时间，1949年中华人民共和国成立后，中国大陆全境统一划为东八区（UTC+8），同时以北京时间作为全国唯一的标准时间，称为北京时间。
        // 北京时间，又名中国标准时间（China Standard Time，CST，UTC+8），是中国大陆的标准时间，比世界协调时快八小时（即UTC+8）。
        Instant instant = Instant.now();
        System.out.println("UTC：" + instant);
        System.out.println("纽约 UTC-5：" + instant.atZone(ZoneId.of("America/New_York")));
        System.out.println("东京 UTC+9：" + instant.atZone(ZoneId.of("Asia/Tokyo")));
        System.out.println("上海 UTC+8：" + instant.atZone(ZoneId.of("Asia/Shanghai")));


    }

    /**
     * 时区
     */
    private static void timeZone() {
        // 时区：
        // jdk8使用ZoneId作为时区类，在此之前使用TimeZone。因此ZoneId转TimeZone时可能存在兼容性问题。
        // 时区ID，Z代表UTC，ZoneId仅支持世界标准时的表达方式
        ZoneId zone = ZoneId.of("Z");
        ZoneId zoneUtc = ZoneId.of("UTC");
        ZoneId zoneGmt = ZoneId.of("GMT");
        ZoneId zoneUt = ZoneId.of("UT");
        ZoneId ctt = ZoneId.of("Asia/Shanghai");
//        System.out.println(ZonedDateTime.now(zone));
//        System.out.println(ZonedDateTime.now(zoneUtc));
//        System.out.println(ZonedDateTime.now(zoneGmt));
//        System.out.println(ZonedDateTime.now(zoneUt));
//        System.out.println(ZonedDateTime.now(ctt));

        // ZoneId可用的时区ID
        Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();
//        availableZoneIds.forEach(System.out::println);

        // 可用的时区ID简称对照表
        Map<String, String> shortIds = ZoneId.SHORT_IDS;
        // 注意：ZoneId的简称已经在TimeZone中废弃.因此在TimeZone使用一些简称时，转ZoneId可能会出现时区错乱



        // TimeZone
        // 也可以通过地区时的简称来获取时区ID，上海时间（"CTT", "Asia/Shanghai"）
//        ZoneId ctt = ZoneId.of("CTT", ZoneId.SHORT_IDS);
        TimeZone timeZoneUtc = TimeZone.getTimeZone("UTC");
        TimeZone timeZoneCtt = TimeZone.getTimeZone("Asia/Shanghai");
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(timeZoneUtc);
        System.out.println(dateFormat.format(date));
        dateFormat.setTimeZone(timeZoneCtt);
        System.out.println(dateFormat.format(date));


        String[] offsetTimeZones = TimeZone.getAvailableIDs(8 * 60 * 60 * 1000);
        // 时区概念 Time Zone，通过时区ID确定一个时区。此外还可以根据getAvailableIDs获取可以表示的时区ID，包括世界标准时和各地的地区时。
        TimeZone timeZone = TimeZone.getTimeZone(ctt);
        ZoneId toZoneId = timeZone.toZoneId();
        // TimeZone可用的时区ID
        String[] availableIds = TimeZone.getAvailableIDs();
//        Arrays.stream(availableIds).forEach(System.out::println);
        // 具体的
        String[] zoneIds = ZoneInfoFile.getZoneIds();
        Map<String, String> aliasMap = ZoneInfoFile.getAliasMap();
        // CTT -> Asia/Shanghai
        // CST -> Central Standard Time (North America)  America/Chicago
//        TimeZone timeZoneCtt = TimeZone.getTimeZone("CTT");
    }


    public static void main(String[] args) {
        offset();
    }

    /**
     * 时区偏移量
     */
    private static void offset() {

        // 时区偏移量：
        // 时区偏移量，相对于世界标准时来说，每相隔一个时区即相差一小时，往东经一个区，时间快1h，偏移量+1，往西经-1，自然有了偏移了小时数，也有分秒。支持的偏移量范围（+18:00 to -18:00）
        // 因此东八区可以由上面的几种世界标准时表示为：UTC+8（+8）、GMT+8、UT+8，其表达的含义大致相当，区别在于每种方式表示的时间精度不同。
        int i = (2 * 60 + 30) * 60 * 1000;
        // 1.
        ZoneId offsetZoneUtc = ZoneId.of("Asia/Shanghai");
        // 2.
        ZoneOffset zoneOffset = ZoneOffset.of("+02:30");
        // 3.
        ZoneOffset zoneOffsetNum = ZoneOffset.ofHoursMinutes(5, 35);
        ZoneId offsetZoneNum = ZoneId.ofOffset("UTC", zoneOffsetNum);

        Instant instant = Instant.now();
        System.out.println("时区：" + instant.atZone(offsetZoneUtc));
        System.out.println("偏移量：" + instant.atOffset(zoneOffset));
        System.out.println("偏移量生成的时区：" + instant.atZone(offsetZoneNum));


        TimeZone timeZoneZ = TimeZone.getTimeZone("GMT+8");
        TimeZone timeZone8 = TimeZone.getTimeZone("UTC+8");
        TimeZone timeZoneNum = TimeZone.getTimeZone("+8");
        Calendar instanceZ8 = Calendar.getInstance(timeZoneZ);
        Calendar instance8 = Calendar.getInstance(timeZone8);


        Date time1 = instanceZ8.getTime();
        Date time2 = instance8.getTime();
    }

    /**
     * 时间单位
     */
    private static void timeUnit() {
        // 时间单位 TimeUnit/1.8前 ChronoUnit/1.8
        ChronoUnit years = ChronoUnit.YEARS;
        // 代表某个时间单位的持续时间
        Duration days = Duration.ofDays(1);
    }

    public static void calc() {
        // 生成时间 以及对比时区不同所产生的时间差距
        LocalDateTime now = LocalDateTime.now(ZoneId.of("+8"));
        LocalDateTime nowOfUtc = LocalDateTime.now(ZoneId.of("UTC+8"));
//        System.out.println(now);
//        System.out.println(nowOfUtc);

        // 时间

        // 判断时间先后
//        System.out.println(now.isBefore(nowOfUtc));
//        System.out.println(now.isAfter(nowOfUtc));


        // 获取天时分秒差，计算天时分秒差计算规则相同，都是通过简单的进制得出的。
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, Calendar.AUGUST, 1, 9, 45, 0);
        Date startDate = calendar.getTime();
        calendar.set(2023, Calendar.APRIL, 1, 1, 45, 0);
        Date endDate = calendar.getTime();

        // 1. 手工计算
        long end = endDate.getTime();
        long start = startDate.getTime();
        long different = end - start;
        long differentDays = different / 1000 / 60 / 60 / 24;
//        System.out.println("相差天数（手工计算）：" + differentDays);
        // 2. TimeUnit封装方法（1.5）
        long differentDays1 = TimeUnit.DAYS.convert(different, TimeUnit.MILLISECONDS);
//        System.out.println("相差天数（TimeUnit封装方法，1.8前）：" + differentDays1);

        // 这种方式并不支持年月时差的计算


        // 3. ChronoUnit封装方法（1.8）
        LocalDateTime startTime = LocalDateTime.of(2019, 7, 1, 9, 45, 0);
        LocalDateTime endTime = LocalDateTime.of(2023, 3, 1, 1, 45, 0);
        long differentDays2 = ChronoUnit.DAYS.between(startTime, endTime);
//        System.out.println("相差天数（ChronoUnit封装方法，1.8）：" + differentDays2);
        // 4. Duration封装方法（1.8）
        long differentDays3 = Duration.between(startTime, endTime).toDays();
//        System.out.println("相差天数（Duration封装方法，1.8）：" + differentDays3);

        long differentYears2 = ChronoUnit.YEARS.between(startTime, endTime);
//        System.out.println("相差年数（ChronoUnit封装方法，1.8）：" + differentYears2);

        //


        // 偏移量计算
        OffsetDateTime offsetDateTime = OffsetDateTime.now();
    }
}
