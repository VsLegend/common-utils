package com.utils.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.utils.common.enums.SerializerEnums;
import org.springframework.util.ObjectUtils;

import java.io.IOException;

/**
 * @Author Wong Junwei
 * @Date 2022/8/1
 * @Description 身份证序列化时脱敏
 */
public class IdCardSerializer extends StdSerializer<String> {

    private static final SerializerEnums SERIALIZER15 =  SerializerEnums.ID_CARD_15;

    private static final SerializerEnums SERIALIZER18 =  SerializerEnums.ID_CARD_18;

    public IdCardSerializer() {
        this(null);
    }


    public IdCardSerializer(Class<String> t) {
        super(t);
    }

    @Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (ObjectUtils.isEmpty(s)) {
            jsonGenerator.writeString(s);
            return;
        }
        if (s.length() == 15) {
            jsonGenerator.writeString(SERIALIZER15.replaceAll(s));
        } else if (s.length() == 18) {
            jsonGenerator.writeString(SERIALIZER18.replaceAll(s));
        } else {
            jsonGenerator.writeString(s);
        }
    }
}
