package com.utils.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.utils.common.enums.SerializerEnums;

import java.io.IOException;

/**
 * @Author Wong Junwei
 * @Date 2022/8/1
 * @Description 手机号序列化时脱敏
 */
public class PhoneSerializer extends StdSerializer<String> {

    private static final SerializerEnums SERIALIZER =  SerializerEnums.PHONE;

    public PhoneSerializer() {
        this(null);
    }


    public PhoneSerializer(Class<String> t) {
        super(t);
    }

    @Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(SERIALIZER.replaceAll(s));
    }
}
