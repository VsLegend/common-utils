package com.utils;

import org.springframework.util.ObjectUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author Wang Junwei
 * @Date 2023/2/20 9:53
 * @Description 身份证解析器
 */
public class IdCardParser {

    /**
     * 身份证出生年月
     */
    private static final String ID_CARD_BIRTH_DAY = "yyyyMMdd";

    /**
     * 身份证位数
     */
    private static final Integer ID_CARD_15 = 15;
    private static final Integer ID_CARD_18 = 18;

    /**
     * 性别代码（0：未知性别；1：男；2：女；5：女变男；6：男变女；9：未定义的性别）
     */
    private static final Integer ID_CARD_MALE = 1;
    private static final Integer ID_CARD_FEMALE = 2;
    private static final Integer ID_CARD_UNKNOWN = 0;


    /**
     * 获取身份证性别
     * @param idCard
     * @return
     */
    public static Integer getSex(String idCard) {
        if (ObjectUtils.isEmpty(idCard)) {
            return null;
        }
        Integer genderNum = null;
        int length = idCard.length();
        //15位身份证号
        if (length == ID_CARD_15) {
            genderNum = Integer.parseInt(idCard.substring(14, 15));
        } else if (length == ID_CARD_18) {
            //18位身份证号
            genderNum = Integer.parseInt(idCard.substring(16).substring(0, 1));
        }
        if (null == genderNum) {
            return ID_CARD_UNKNOWN;
        }
        return genderNum % 2 == 0 ? ID_CARD_FEMALE : ID_CARD_MALE;
    }


    /**
     * 获取出生日期  yyyy年MM月dd日
     *
     * @param idCard
     * @return
     */
    public static Date getBirthDay(String idCard) {
        String birth = split(idCard);
        if (!ObjectUtils.isEmpty(birth)) {
            Date parse;
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(ID_CARD_BIRTH_DAY);
                parse = sdf.parse(birth);
                return parse;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 分割
     *
     * @param idCard
     * @return
     */
    private static String split(String idCard) {
        if (!ObjectUtils.isEmpty(idCard)) {
            int length = idCard.length();
            if (length == ID_CARD_15) {
                return "19" + idCard.substring(6, 12);
            } else if (length == ID_CARD_18) {
                return idCard.substring(6, 14);
            }
        }
        return null;
    }
}
