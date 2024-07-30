package com.example.scrabble.use_case.end_turn;
import java.util.List;




public class GetEndTurnInputData {
    private int gameId;
    private boolean isContestSucceed;
    private boolean isContest;
    private List<List<Integer>> wordsToBeConfirmed;

    public GetEndTurnInputData() {
        // Empty constructor for automatic parsing
    }

    public GetEndTurnInputData(int gameId, boolean isContestSucceed, boolean isContest, List<List<Integer>> wordsToBeConfirmed) {
        this.gameId = gameId;
        this.isContestSucceed = isContestSucceed;
        this.isContest = isContest;
        this.wordsToBeConfirmed = wordsToBeConfirmed;
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

    public List<List<Integer>> getWordsToBeConfirmed() {
        return wordsToBeConfirmed;
    }

}
