package com.example.scrabble.use_case.end_turn;
import java.util.List;




public class GetEndTurnInputData {
    private int gameId;
    private List<List<Integer>> wordsToBeConfirmed;

    public GetEndTurnInputData() {
        // Empty constructor for automatic parsing
    }

    public GetEndTurnInputData(int gameId, List<List<Integer>> wordsToBeConfirmed) {
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
