package com.example.scrabble.converter;

import com.example.scrabble.entity.Player;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class LeaderBoardConverter extends JsonConverter{
    public LeaderBoardConverter() {
        super(new TypeReference<List<Player>>() {});
    }
}
