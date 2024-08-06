package com.example.scrabble.use_case.join_game;

import com.example.scrabble.entity.Game;
import com.example.scrabble.data_access.GameDataAccess;

public class JoinGameInputData {
    private String playerName;
    private int gameId;

    public JoinGameInputData() {
        // Empty constructor for automatic parsing
    }

    public JoinGameInputData(String playerName, int gameId){
        this.playerName = playerName;
        this.gameId = gameId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getGameId() {
        return gameId;
    }
}



