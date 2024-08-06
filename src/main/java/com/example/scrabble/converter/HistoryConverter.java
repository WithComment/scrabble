package com.example.scrabble.converter;

import com.example.scrabble.entity.Play;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class HistoryConverter extends JsonConverter{
    public HistoryConverter() {
        super(new TypeReference<List<Play>>() {});
    }
}
