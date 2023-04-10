package com.simona.housing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.lang.reflect.Type;

public class JsonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();


    public static <T> T json2obj(String jsonStr, Type targetType) {
        mapper.findAndRegisterModules();
        try {
            JavaType javaType = TypeFactory.defaultInstance().constructType(targetType);
            return mapper.readValue(jsonStr, javaType);
        } catch (IOException e) {
            throw new IllegalArgumentException("An error occurred while converting JSON to an object:"  + jsonStr, e);
        }
    }
}
