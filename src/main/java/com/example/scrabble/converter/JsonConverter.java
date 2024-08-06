package com.example.scrabble.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;

public class JsonConverter<T> implements AttributeConverter<T, String>{
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final TypeReference<T> typeReference;

    public JsonConverter(TypeReference<T> typeReference) {
        this.typeReference = typeReference;
    }

    @Override
    public String convertToDatabaseColumn(T attribute) {
        if (attribute == null)
            return "";
        try {
            return objectMapper.writeValueAsString(attribute);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public T convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty())
            return null;
        try {
            return objectMapper.readValue(dbData, typeReference);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
