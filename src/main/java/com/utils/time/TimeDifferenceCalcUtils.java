package com.utils.time;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

/**
 * @Author Wang Junwei
 * @Date 2023/2/21 17:58
 * @Description 时差计算
 */
public class TimeDifferenceCalcUtils {

    /**
     * 如果是月龄小月1（30天），则年龄为新生儿，若1月≤年龄＜2岁（24月），需要精确到月龄，≥2岁，则为年
     *
     * @param dateOfBirth
     */
    public static void calcAgeData(LocalDateTime dateOfBirth, LocalDateTime dateOfCalc) {
        if (null == dateOfBirth || null == dateOfCalc || dateOfCalc.isBefore(dateOfBirth)) {
            return;
        }
        try {
            // 判断日差和月差
            long dayInterval = ChronoUnit.DAYS.between(dateOfBirth, dateOfCalc);
            long monthInterval = ChronoUnit.MONTHS.between(dateOfBirth, dateOfCalc);
            long yearInterval = ChronoUnit.YEARS.between(dateOfBirth, dateOfCalc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Date转LocalDateTime
     *
     * @param date
     * @return
     */
    private static LocalDateTime toLocalDateTime(Date date) {
        return toLocalDateTime(date, ZoneId.of(TimeConstants.TIME_ZONE));
    }

    private static LocalDateTime toLocalDateTime(Date date, ZoneId zoneId) {
        Objects.requireNonNull(date, "date");
        Objects.requireNonNull(date, "zoneId");
        Instant instant = date.toInstant();
        return instant.atZone(zoneId).toLocalDateTime();
    }

}