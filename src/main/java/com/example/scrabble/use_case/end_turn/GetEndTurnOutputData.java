package com.example.scrabble.use_case.end_turn;


public class GetEndTurnOutputData {
    private int gameId;
    boolean isContestSucceed;
    boolean isContest;

    public GetEndTurnOutputData(int gameId, boolean isContestSucceed, boolean isContest) {
        this.gameId = gameId;
        this.isContestSucceed = isContestSucceed;
        this.isContest = isContest;

    }


}
