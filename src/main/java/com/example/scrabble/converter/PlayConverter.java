package com.example.scrabble.converter;

import com.example.scrabble.entity.Play;
import com.fasterxml.jackson.core.type.TypeReference;

public class PlayConverter extends JsonConverter{
    public PlayConverter() {
        super(new TypeReference<Play>() {});
    }
}
