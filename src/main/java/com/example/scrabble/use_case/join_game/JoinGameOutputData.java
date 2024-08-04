package com.example.scrabble.use_case.join_game;

import com.example.scrabble.entity.Game;
import com.example.scrabble.data_access.GameDataAccess;

public class JoinGameOutputData {
    private final String playerName;

    public JoinGameOutputData(String playerName){
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }
}