package com.example.scrabble.converter;

import com.example.scrabble.entity.Move;
import com.example.scrabble.entity.Play;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class JsonConverter<T> implements AttributeConverter<T, String>{
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final TypeReference<T> typeReference;

    private static Logger logger = LoggerFactory.getLogger(JsonConverter.class);

    public JsonConverter(TypeReference<T> typeReference) {
        this.typeReference = typeReference;
    }

    @Override
    public String convertToDatabaseColumn(T attribute) {
        if (attribute == null)
            return "";
        try {
            if (typeReference.getType().equals(Play.class)) {
                Play play = (Play) attribute;
                List<Move> moves = play.getMoves();
                logger.info("[");
                for (Move move: moves) {
                    logger.info(move + ",");
                }
                logger.info("]");
            }
            logger.info("Converting "+ typeReference.getType() + "to database column: " + objectMapper.writeValueAsString(attribute));
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
            logger.info("Converting database column to "+ typeReference.getType() + ": " + dbData);
            return objectMapper.readValue(dbData, typeReference);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
