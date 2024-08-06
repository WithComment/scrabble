package com.example.scrabble.use_case.end_turn;


public class EndTurnOutputData {
    private int gameId;

    public EndTurnOutputData() {}

    public EndTurnOutputData(int gameId) {
        this.gameId = gameId;
    }

    public int getGameId() {
      return gameId;
    }
}
