package com.example.scrabble.use_case.end_turn;
import java.util.List;

public class EndTurnInputData {
    private int gameId;
    private List<List<Integer>> wordsToBeConfirmed;

    public EndTurnInputData() {
        // Empty constructor for automatic parsing
    }

    public EndTurnInputData(int gameId, List<List<Integer>> wordsToBeConfirmed) {
        this.gameId = gameId;
        this.wordsToBeConfirmed = wordsToBeConfirmed;
    }

    public int getGameId() {
        return gameId;
    }


    public List<List<Integer>> getWordsToBeConfirmed() {
        return wordsToBeConfirmed;
    }

}
