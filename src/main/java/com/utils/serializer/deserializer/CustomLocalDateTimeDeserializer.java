package com.utils.serializer.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author Wang Junwei
 * @Date 2022/10/19 13:35
 * @Description LocalDateTime反序列化
 */
public class CustomLocalDateTimeDeserializer extends StdDeserializer<LocalDateTime> {


    private final DateTimeFormatter FORMATTER1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final DateTimeFormatter FORMATTER2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public CustomLocalDateTimeDeserializer() {
        this(LocalDateTime.class);
    }


    CustomLocalDateTimeDeserializer(Class<LocalDateTime> deserializer) {
        super(deserializer);
    }

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String text = jsonParser.getText().trim();
        if (ObjectUtils.isEmpty(text)) {
            return null;
        }
        LocalDateTime localDateTime = null;
        try {
            localDateTime = LocalDateTime.parse(text, FORMATTER1);
        } catch (Exception e) {
            try {
                LocalDate parse = LocalDate.parse(text, FORMATTER2);
                LocalTime time = LocalTime.of(0, 0, 0);
                localDateTime = LocalDateTime.of(parse, time);
            } catch (Exception ignore) {
            }
        }
        return localDateTime;
    }
}
