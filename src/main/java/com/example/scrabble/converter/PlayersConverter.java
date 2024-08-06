package com.example.scrabble.converter;

import com.example.scrabble.entity.Player;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class PlayersConverter extends JsonConverter{
    public PlayersConverter() {
        super(new TypeReference<List<Player>>() {});
    }
}
