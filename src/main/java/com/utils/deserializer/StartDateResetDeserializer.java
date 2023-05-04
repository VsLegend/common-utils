package com.utils.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author Wang Junwei
 * @Date 2022/8/25 15:31
 * @Description 查询的开始时间兼容格式
 */
public class StartDateResetDeserializer extends StdDeserializer<Date> {

    private static final SimpleDateFormat FORMATTER1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final SimpleDateFormat FORMATTER2 = new SimpleDateFormat("yyyy-MM-dd");

    StartDateResetDeserializer() {
        this(null);
    }


    StartDateResetDeserializer(Class<Date> deserializer) {
        super(deserializer);
    }

    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String text = p.getText();
        if (ObjectUtils.isEmpty(text)) {
            return null;
        }
        Date date = null;
        try {
            date = FORMATTER1.parse(text);
        } catch (ParseException ignore) {
            try {
                date = FORMATTER2.parse(text);
            } catch (ParseException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("Illegal date format");
            }
        }
        return date;
    }
}
