package com.example.scrabble.use_case.contest;

public class ContestInputData {
    private int gameId;
    private int playerId;
    private boolean isContest;

    public ContestInputData() {}

    public ContestInputData(int gameId, int playerId, boolean isContest) {
        this.gameId = gameId;
        this.playerId = playerId;
        this.isContest = isContest;
    }

    public int getGameId() {
        return gameId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public boolean getIsContest() {
        return isContest;
    }
}
