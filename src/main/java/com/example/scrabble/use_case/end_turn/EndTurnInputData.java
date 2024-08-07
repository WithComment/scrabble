package com.example.scrabble.use_case.end_turn;

public class EndTurnInputData {
    private int gameId;

    public EndTurnInputData() {
        // Empty constructor for automatic parsing
    }

    public EndTurnInputData(int gameId) {
        this.gameId = gameId;
    }

    public int getGameId() {
        return gameId;
    }

}
