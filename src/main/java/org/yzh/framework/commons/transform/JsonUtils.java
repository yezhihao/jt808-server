package org.yzh.framework.commons.transform;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;

public class JsonUtils {

    public static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(Include.NON_NULL);
    }

    public static <T> void register(Class<T> type, JsonDeserializer<? extends T> deserializer) {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(type, deserializer);
        mapper.registerModule(module);
    }

    public static String toJson_(Object value) throws JsonProcessingException {
        return mapper.writeValueAsString(value);
    }

    public static String toJson(Object value) {
        try {
            return mapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T toObj_(Class<? extends T> clazz, String json) throws IOException {
        return mapper.readValue(json, clazz);
    }

    public static <T> T toObj(Class<? extends T> clazz, String json) {
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T toObj_(TypeReference<T> type, String json) throws IOException {
        return mapper.readValue(json, type);
    }

    public static <T> T toObj(TypeReference<T> type, String json) {
        try {
            return mapper.readValue(json, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}