package com.example.scrabble.use_case.contest;

public class ContestInputData {
    private int gameId;
    private int playerId;

    public ContestInputData() {}

    public ContestInputData(int gameId, int playerId) {
        this.gameId = gameId;
        this.playerId = playerId;
    }

    public int getGameId() {
        return gameId;
    }

    public int getPlayerId() {
        return playerId;
    }
}
