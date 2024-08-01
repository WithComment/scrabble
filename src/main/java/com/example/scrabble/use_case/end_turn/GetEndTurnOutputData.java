package com.example.scrabble.use_case.end_turn;


public class GetEndTurnOutputData {
    private int gameId;

    public GetEndTurnOutputData() {}

    public GetEndTurnOutputData(int gameId) {
        this.gameId = gameId;
    }

    public int getGameId() {
      return gameId;
    }
}
