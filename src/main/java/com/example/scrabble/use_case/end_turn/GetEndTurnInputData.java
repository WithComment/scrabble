package com.example.scrabble.use_case.end_turn;
import java.util.List;




public class GetEndTurnInputData {
    private int gameId;

    public GetEndTurnInputData() {
        // Empty constructor for automatic parsing
    }

    public GetEndTurnInputData(int gameId) {
        this.gameId = gameId;
    }

    public int getGameId() {
        return gameId;
    }

    

}
