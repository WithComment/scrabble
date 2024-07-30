package com.example.scrabble.use_case.end_game;

public class EndGameInputData {
    private final int gameId;

    public EndGameInputData(int gameId) {
        this.gameId = gameId;
    }

    public int getGameId() {
        return gameId;
    }

}
