package com.example.scrabble.converter;

import com.example.scrabble.entity.LetterBag;
import com.fasterxml.jackson.core.type.TypeReference;

public class LetterBagConverter extends JsonConverter {
    public LetterBagConverter() {
        super(new TypeReference<LetterBag>() {});
    }
}
