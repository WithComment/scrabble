package com.example.scrabble.use_case.join_game;

public class JoinGameInputData {
    private String playerName;
    private int gameId;

    public JoinGameInputData() {}

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



