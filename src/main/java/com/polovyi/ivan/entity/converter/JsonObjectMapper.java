package com.polovyi.ivan.entity.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class JsonObjectMapper {

    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @SneakyThrows
    public static String toString(Object object) {
        return objectMapper.writeValueAsString(object);
    }

    @SneakyThrows
    public static <T> T toObject(String json, Class<T> clazz) {

        return objectMapper.readValue(json, clazz);
    }
}
