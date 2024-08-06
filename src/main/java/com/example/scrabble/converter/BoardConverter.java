package com.example.scrabble.converter;

import com.example.scrabble.entity.Board;
import com.fasterxml.jackson.core.type.TypeReference;

public class BoardConverter extends JsonConverter{
    public BoardConverter() {
        super(new TypeReference<Board>() {});
    }
}
