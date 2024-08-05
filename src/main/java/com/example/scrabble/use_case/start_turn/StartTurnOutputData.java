package com.example.scrabble.use_case.start_turn;


public class StartTurnOutputData {
    private int gameId;

    public StartTurnOutputData() {}

    public StartTurnOutputData(int gameId) {
        this.gameId = gameId;
    }

    public int getGameId() {
        return gameId;
    }
}