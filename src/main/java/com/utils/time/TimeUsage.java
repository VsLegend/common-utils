package com.utils.time;

import org.springframework.cglib.core.Local;
import sun.util.calendar.ZoneInfo;
import sun.util.calendar.ZoneInfoFile;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.chrono.Chronology;
import java.time.temporal.*;
import java.time.zone.ZoneRules;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

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
        ZoneId zoneId = ZoneId.systemDefault(); // 本质是调用TimeZone.getDefault().toZoneId()
        TimeZone timeZone = TimeZone.getDefault();
        // 可以设置全局的默认时区
        TimeZone.setDefault(timeZone);


        // jdk8使用ZoneId作为时区类，在此之前使用TimeZone。因此ZoneId转TimeZone时可能存在兼容性问题。
        // 时区ID，Z代表UTC，ZoneId仅支持世界标准时的表达方式
        ZoneId zone = ZoneId.of("Z");
        ZoneId zoneUtc = ZoneId.of("UTC");
        ZoneId zoneGmt = ZoneId.of("GMT");
        ZoneId zoneUt = ZoneId.of("UT");
        ZoneId ctt = ZoneId.of("Asia/Shanghai");
        String[] availableIDs = ZoneInfo.getAvailableIDs(8 * 60 * 60 * 1000);
        System.out.println(ZonedDateTime.now(zone));
        System.out.println(ZonedDateTime.now(zoneUtc));
        System.out.println(ZonedDateTime.now(zoneGmt));
        System.out.println(ZonedDateTime.now(zoneUt));
        System.out.println(ZonedDateTime.now(ctt));

        Instant parse = Instant.parse("1986-06-01T00:00:00.000Z");
        LocalDateTime localDateTime = LocalDateTime.ofInstant(parse, ctt);
        ZoneOffset offset = ctt.getRules().getOffset(parse);

        // ZoneId可用的时区ID
        Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();
//        availableZoneIds.forEach(System.out::println);

        // 可用的时区ID简称对照表
        Map<String, String> shortIds = ZoneId.SHORT_IDS;
        // 注意：ZoneId的简称已经在TimeZone中废弃.因此在TimeZone使用一些简称时，转ZoneId可能会出现时区错乱


        // TimeZone
        // 也可以通过地区时的简称来获取时区ID，上海时间（"CTT", "Asia/Shanghai"）
//        ZoneId ctt = ZoneId.of("CTT", ZoneId.SHORT_IDS);
        Date date = new Date();
        TimeZone utc = TimeZone.getTimeZone("UTC+8");
        TimeZone gmt = TimeZone.getTimeZone("GMT+8");
        TimeZone ut = TimeZone.getTimeZone("UT+8");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(utc);
        System.out.println(sdf.format(date));
        sdf.setTimeZone(gmt);
        System.out.println(sdf.format(date));
        sdf.setTimeZone(ut);
        System.out.println(sdf.format(date));


        String[] offsetTimeZones = TimeZone.getAvailableIDs(8 * 60 * 60 * 1000);
        // 时区概念 Time Zone，通过时区ID确定一个时区。此外还可以根据getAvailableIDs获取可以表示的时区ID，包括世界标准时和各地的地区时。
        TimeZone tz = TimeZone.getTimeZone(ctt);
        ZoneId toZoneId = tz.toZoneId();
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

    /**
     * 时区偏移量
     */
    private static void offset() {

        // 时区偏移量：
        // 时区偏移量，相对于世界标准时来说，每相隔一个时区即相差一小时，往东经一个区，时间快1h，偏移量+1，往西经-1，自然有了偏移了小时数，也有分秒。支持的偏移量范围（+18:00 to -18:00）
        // 因此东八区可以由上面的几种世界标准时表示为：UTC+8（+8）、GMT+8、UT+8，其表达的含义大致相当，区别在于每种方式表示的时间精度不同。
        Instant instant = Instant.now();
        // 1.
        ZoneId offsetZoneUtc = ZoneId.of("Asia/Shanghai");
        ZoneId offsetZoneNum = ZoneId.ofOffset("UTC", ZoneOffset.ofHours(1)); // 仅支持GMT、UTC、UT三种前缀
        System.out.println("时区的偏移量计算：" + instant.atZone(offsetZoneUtc));
        System.out.println("指定时区偏移量计算：" + instant.atZone(offsetZoneNum));

        // 2.
        // 比如我们当前时区为东八区，要计算本地时间+2:30偏移量的当地时间
        ZoneId zoneId = ZoneId.of("Asia/Shanghai");
        ZoneOffset zoneOffset = ZoneOffset.of("+02:30"); // ZoneOffset.ofHoursMinutes(2, 30);
        System.out.println("世界时时间：" + instant);
        System.out.println("当前地区时间：" + instant.atZone(zoneId).toLocalDateTime());
        System.out.println("偏移量的本地时间计算：" + instant.atOffset(zoneOffset).toLocalDateTime());

        TimeZone timeZone = TimeZone.getTimeZone("GMT+12:24");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(timeZone);
        System.out.println(dateFormat.format(new Date()));
    }

    private static void chrUnit() {
        // 时间单位 TimeUnit/1.8前 ChronoUnit/1.8
        Instant instant = Instant.now();
        ChronoUnit hours = ChronoUnit.HOURS;
        instant.atZone(ZoneId.of("+8"));
        System.out.println("不同时间类的相差时间：" + hours.between(instant.atOffset(ZoneOffset.ofHours(-5)), instant.atOffset(ZoneOffset.ofHours(8))));
        System.out.println("不同时间类的相差时间：" + hours.between(instant.atZone(ZoneId.of("-5")), instant.atZone(ZoneId.of("+8"))));
        System.out.println("不同时间类的相差时间：" + hours.between(LocalDateTime.ofInstant(instant, ZoneId.of("-5")), LocalDateTime.ofInstant(instant, ZoneId.of("+8"))));

        ChronoUnit weeks = ChronoUnit.WEEKS;
        System.out.println("相差周数：" + weeks.between(LocalDate.parse("2023-04-07"), LocalDate.parse("2023-04-14")));
        System.out.println("相差周数：" + weeks.between(LocalDate.parse("2023-04-08"), LocalDate.parse("2023-04-14")));

        LocalDate localDate = LocalDate.parse("2023-03-21");
        System.out.println(localDate.isSupported(ChronoUnit.DAYS) && localDate.isSupported(ChronoUnit.MONTHS) && localDate.isSupported(ChronoUnit.YEARS));
        System.out.println(localDate.plus(10, ChronoUnit.DAYS));
        System.out.println(localDate.minus(1, ChronoUnit.MONTHS));
        System.out.println(localDate.until(localDate.minus(10, ChronoUnit.YEARS), ChronoUnit.YEARS));

    }


    private static void chronoField() {
        System.out.println(ChronoField.AMPM_OF_DAY.getFrom(LocalDateTime.now()));

        LocalDateTime now = LocalDateTime.now(); // 2023-04-21T18:34:57.537
        System.out.println(now.isSupported(ChronoField.AMPM_OF_DAY) && now.isSupported(ChronoField.DAY_OF_MONTH));
        // 0代表上午 1代表下午
        System.out.println(now.get(ChronoField.AMPM_OF_DAY)); // 1
        System.out.println(now.getLong(ChronoField.MILLI_OF_SECOND)); // 537
        System.out.println(now.range(ChronoField.DAY_OF_MONTH)); // 1-30

        System.out.println(now.get(ChronoField.SECOND_OF_MINUTE));
        System.out.println(now.get(ChronoField.MINUTE_OF_HOUR));
        System.out.println(now.get(ChronoField.HOUR_OF_DAY));

        System.out.println(now.get(ChronoField.MONTH_OF_YEAR));
        System.out.println(now.get(ChronoField.DAY_OF_MONTH));
        System.out.println(now.get(ChronoField.DAY_OF_WEEK));
    }

    /**
     * 时间单位
     */
    private static void duration() {
        // 代表某个时间的具体长度
        Instant now = Instant.now();
        Duration days = Duration.ofDays(1);
        System.out.printf("时间加减：%s，增加后：%s，减少后：%s \n", now, days.addTo(now), days.subtractFrom(now));
        System.out.printf("时间长度：%s，增加后：%s，倍数计算：%s \n", days, days.plus(Duration.ofHours(6)), days.multipliedBy(10));

        Duration duration = ChronoUnit.HOURS.getDuration();
        System.out.println(days.toString());
        System.out.println(duration.toString());
        System.out.println(days.get(ChronoUnit.SECONDS));

        Duration plus = Duration.ofDays(10).plus(Duration.ofHours(10)).plus(Duration.ofMinutes(10));
        System.out.println(plus);
        System.out.println(now.plus(plus));

        // 时间段
        ChronoField hourOfDay = ChronoField.HOUR_OF_DAY;

    }

    public static void main(String[] args) {
        Clock clock = Clock.systemDefaultZone();
        Clock tick = Clock.tick(clock, Duration.ofHours(1).plusMinutes(30));
        System.out.println(LocalDateTime.ofInstant(clock.instant(), ZoneId.systemDefault()));
        System.out.println(LocalDateTime.ofInstant(tick.instant(), ZoneId.systemDefault()));
    }

    /**
     * 时钟类（动态时间）
     */
    private static void clock() {
        // 本地系统时钟 SystemClock
        Clock defaultClock = Clock.systemDefaultZone();

        // 静态时钟 FixedClock
        Clock fixedClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

        // 偏移量时钟 OffsetClock
        Clock offset = Clock.offset(defaultClock, Duration.ofDays(1));

        // 闹钟，根据当前时间计算Duration后的未来时钟 TickClock
        Clock tick = Clock.tick(defaultClock, Duration.ofMinutes(10));
        System.out.println(LocalDateTime.ofInstant(defaultClock.instant(), ZoneId.systemDefault()));
        System.out.println(LocalDateTime.ofInstant(tick.instant(), ZoneId.systemDefault()));
    }

    private static void print(Clock clock) {
        System.out.println(clock.toString());
        LockSupport.parkNanos(1);
        System.out.println(clock.instant());
        LockSupport.parkNanos(1);
        System.out.println(clock.instant());
    }


    /**
     * Instant（时间实例，静态时间）
     */
    private static void instant() {
        long timeMillis = System.currentTimeMillis();
        Instant instant = Instant.now();
        Instant now = Instant.now(Clock.systemUTC());
        Instant ofEpochMilli = Instant.ofEpochMilli(timeMillis);
        Instant parse = Instant.parse("2023-04-14T08:22:28.987Z");

        // 时间转换操作
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.of("America/New_York"));
        OffsetDateTime offsetDateTime = OffsetDateTime.ofInstant(instant, ZoneId.of("+5"));
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        // 时间加减操作
        Instant minus = instant.minus(1, ChronoUnit.DAYS);
        instant.minus(Duration.ofDays(18));
        Instant plus = instant.plus(1, ChronoUnit.DAYS);
        Instant adjustInto = ChronoField.INSTANT_SECONDS.adjustInto(instant, instant.getEpochSecond() + 60 * 60 * 24);

        // 对比（时间是用秒和毫秒存储，谁的值小谁就越早，反之越晚）
        boolean after = instant.isAfter(minus); // true
        boolean before = instant.isBefore(plus); // true

        // 查询是否支持某个时间单位、时间区域，不支持的话会在使用时间单位、时间区域的方法中抛出异常
        boolean supported = instant.isSupported(ChronoUnit.FOREVER); // false
        boolean supported1 = instant.isSupported(ChronoField.DAY_OF_MONTH); // false

        // 查询指定的时间对象
        Integer nano = Instant.now().query(temporal -> temporal.get(ChronoField.NANO_OF_SECOND));
        Integer milli = Instant.now().query(temporal -> temporal.get(ChronoField.MILLI_OF_SECOND));
        ZoneId query = ZonedDateTime.now(ZoneId.of("Asia/Shanghai")).query(TemporalQueries.zoneId());
        LocalDate localDate = ZonedDateTime.now(ZoneId.of("Asia/Shanghai")).query(TemporalQueries.localDate());

        Integer year = Year.now().query(temporal -> temporal.get(ChronoField.YEAR));
    }

    public static void localDateTime() {
        // 实例
        LocalTime localTime = LocalTime.parse("13:45:30.123456789");
        LocalDate localDate = LocalDate.parse("2023-04-19");
        LocalDateTime localDateTime = LocalDateTime.parse("2023-04-19T13:45:30.123456789");

        // 转换
        LocalDateTime of = LocalDateTime.of(localDate, localTime);
        localDate.atTime(localTime);
        localTime.atDate(localDate);
        of.toLocalDate();
        of.toLocalTime();
        // 时间调整
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime adjustInto = (LocalDateTime) LocalTime.MAX.adjustInto(now);
        LocalDateTime adjustInto1 = (LocalDateTime) Year.of(2025).adjustInto(now);
        Temporal temporal = ZoneOffset.UTC.adjustInto(now);

        int year = now.getYear();
        int monthValue = now.getMonthValue();
        Month month = now.getMonth();
        DayOfWeek dayOfWeek = now.getDayOfWeek();
        int dayOfMonth = now.getDayOfMonth();
        LocalDateTime withLocal = now.withYear(2022).withMonth(12).withDayOfMonth(23).withHour(10).withMinute(55).withSecond(12).withNano(123456);
        LocalDateTime plusAndMinus = now.plusYears(1).plusMonths(12).plusWeeks(1).minusDays(40).minusHours(1).minusMinutes(1).plusSeconds(120).plusNanos(10);
    }

    public static void zonedDateTime() {
        ZoneId from = ZoneId.from(OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.ofHoursMinutes(8, 12)));
        // 创建实例
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        ZonedDateTime.now(ZoneId.systemDefault());
        ZonedDateTime.from(OffsetDateTime.now());
        ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
        ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault());
        ZonedDateTime.ofStrict(LocalDateTime.now(), ZoneOffset.ofHours(8), ZoneId.of("Asia/Shanghai")); // 偏移量和时区不匹配会抛错
        ZonedDateTime.parse("2023-04-14T08:22:28.987+08:00");

    }

    public static void offsetDateTime() {
        OffsetDateTime offsetDateTime = OffsetDateTime.now();
        OffsetTime now = OffsetTime.now();
        OffsetTime offsetTime = offsetDateTime.toOffsetTime();

        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Shanghai"));
        ZoneOffset zoneOffset = ZoneOffset.ofHours(8);

        OffsetDateTime of = OffsetDateTime.of(localDateTime, zoneOffset);

        // 调整偏移量
        System.out.println(of);
        System.out.println(of.withOffsetSameLocal(ZoneOffset.ofHours(-5)));

        // 调整偏移量时同时调整该偏移量的时间
        System.out.println(of.withOffsetSameInstant(ZoneOffset.ofHours(8)));
        System.out.println(of.withOffsetSameInstant(ZoneOffset.ofHours(-5)));
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
