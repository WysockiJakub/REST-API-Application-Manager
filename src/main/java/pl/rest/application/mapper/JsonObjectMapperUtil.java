package pl.rest.application.mapper;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import java.io.IOException;

public class JsonObjectMapperUtil {
    private static final ObjectMapper INSTANCE = initJsonMapper();

    private static ObjectMapper initJsonMapper() {
        return (new ObjectMapper()).registerModule(new Jdk8Module()).registerModule(new JavaTimeModule()).registerModule(new JaxbAnnotationModule()).enable(SerializationFeature.INDENT_OUTPUT).enable(SerializationFeature.FAIL_ON_EMPTY_BEANS).enable(SerializationFeature.WRITE_ENUMS_USING_INDEX).enable(SerializationFeature.WRITE_DATES_WITH_ZONE_ID).disable(DeserializationFeature.READ_ENUMS_USING_TO_STRING).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE).disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).setSerializationInclusion(Include.NON_NULL).configure(Feature.ESCAPE_NON_ASCII, true).copy();
    }

    public static ObjectMapper jsonMapper() {
        return INSTANCE;
    }

    public static <T> T fromJson(String content, Class<T> clazz) {
        try {
            return INSTANCE.readValue(content, clazz);
        } catch (IOException var3) {
            throw new JsonObjectMapperUtil.PPKJsonException(var3);
        }
    }

    public static String asJson(Object obj) {
        try {
            return INSTANCE.writeValueAsString(obj);
        } catch (JsonProcessingException var2) {
            throw new JsonObjectMapperUtil.PPKJsonException(var2);
        }
    }

    private JsonObjectMapperUtil() {
    }

    public static class PPKJsonException extends RuntimeException {
        public PPKJsonException(Exception e) {
            super(e);
        }
    }
}