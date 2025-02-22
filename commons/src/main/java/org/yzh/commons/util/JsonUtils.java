package org.yzh.commons.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.yzh.commons.spring.BaseConfig;

import java.io.IOException;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public class JsonUtils {

    public static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModules(BaseConfig.objectMapperModules());
        BaseConfig.configureObjectMapper(objectMapper);
    }

    public static void setObjectMapper(ObjectMapper mapper) {
        objectMapper = mapper;
    }

    @SneakyThrows
    public static String toJson(Object value) {
        return objectMapper.writeValueAsString(value);
    }

    public static <T> T toObj(String json, Class<? extends T> clazz) {
        if (json != null && (json.startsWith("{") || json.startsWith("[")))
            try {
                return objectMapper.readValue(json, clazz);
            } catch (IOException ignored) {
            }
        return null;
    }

    public static <T> T toObj(String json, TypeReference<T> type) {
        if (json != null && (json.startsWith("{") || json.startsWith("[")))
            try {
                return objectMapper.readValue(json, type);
            } catch (IOException ignored) {
            }
        return null;
    }
}