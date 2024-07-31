package com.example.scrabble.use_case.end_turn;


public class GetEndTurnOutputData {
    private int gameId;
    private boolean isContestSucceed;
    private boolean isContest;

    public GetEndTurnOutputData() {}

    public GetEndTurnOutputData(int gameId, boolean isContestSucceed, boolean isContest) {
        this.gameId = gameId;
        this.isContestSucceed = isContestSucceed;
        this.isContest = isContest;
    }

    public int getGameId() {
      return gameId;
    }

    public boolean isContestSucceed() {
      return isContestSucceed;
    }

    public boolean isContest() {
      return isContest;
    }
}
