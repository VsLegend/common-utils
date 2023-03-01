package com.utils.time;

/**
 * @Author Wang Junwei
 * @Date 2023/2/28 15:01
 * @Description
 */
public class TimeConstants {

    public static final String TIME_ZONE = "+8";
    public static final String TIME_ZONE_UTC = "UTC+8";
    public static final String TIME_ZONE_GMT = "GMT+8";
    public static final String TIME_ZONE_UT = "UT+8";

    /**
     * 除了国际通用外，每个国家也存在各自的时区概念
     * 如我们中国就公用一个时区概念（中国实际横跨多个时区，为了统一时间，便使用了改时区概念）：CCT +8，中国北京时间
     */
    public static final String TIME_ZONE_CCT = "CCT+8";
}
