package com.example.scrabble.converter;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class NumContestFailedConverter extends JsonConverter{
    public NumContestFailedConverter() {
        super(new TypeReference<List<Integer>>() {});
    }
}
