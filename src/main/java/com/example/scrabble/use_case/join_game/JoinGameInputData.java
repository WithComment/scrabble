package com.example.scrabble.use_case.join_game;

import com.example.scrabble.entity.Game;
import com.example.scrabble.data_access.GameDataAccess;

public class JoinGameInputData {
    private final String playerName;
    private final int gameId;

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



