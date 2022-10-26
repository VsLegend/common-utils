package com.utils.serializer.deserializer;

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
 * @Description 将查询时间的结束时间设置为：当天的最后一刻
 */
public class EndDateResetDeserializer extends StdDeserializer<Date> {

    private static final SimpleDateFormat FORMATTER1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final SimpleDateFormat FORMATTER2 = new SimpleDateFormat("yyyy-MM-dd");

    EndDateResetDeserializer() {
        this(null);
    }


    EndDateResetDeserializer(Class<Date> deserializer) {
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
        if (null == date) {
            return null;
        }
        return new Date(date.getTime() + 1000 * 60 * 60 * 24L - 1L);
    }
}
