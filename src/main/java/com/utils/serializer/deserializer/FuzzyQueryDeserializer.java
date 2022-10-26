package com.utils.serializer.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.util.ObjectUtils;

import java.io.IOException;

/**
 * @Author Wang Junwei
 * @Date 2022/8/25 15:31
 * @Description 模糊查询转义
 */
public class FuzzyQueryDeserializer extends StdDeserializer<String> {

    /**
     * 需要转义的字符串
     */
    String[] fbsArr = {"\\", ""};

    FuzzyQueryDeserializer() {
        this(null);
    }


    FuzzyQueryDeserializer(Class<String> deserializer) {
        super(deserializer);
    }

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String keyword = p.getValueAsString();
        if (ObjectUtils.isEmpty(keyword)) {
            return null;
        }
        return keyword.trim()
                .replaceAll("\\s", "")
                .replace("\\", "\\\\\\\\")
                .replace("_", "\\_")
                .replace("'", "\\'")
                .replace("%", "\\%")
                .replace("*", "\\*");
    }
}
