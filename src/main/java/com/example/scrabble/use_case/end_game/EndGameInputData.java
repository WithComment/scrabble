package com.example.scrabble.use_case.end_game;

public class EndGameInputData {
    private int gameId;

    public EndGameInputData() {}

    public EndGameInputData(int gameId) {
        this.gameId = gameId;
    }

    public int getGameId() {
        return gameId;
    }

}
