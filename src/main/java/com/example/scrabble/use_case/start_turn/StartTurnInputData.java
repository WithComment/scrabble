package com.example.scrabble.use_case.start_turn;
import java.util.List;




public class StartTurnInputData {
  private int gameId;

  public StartTurnInputData() {
    // Empty constructor for automatic parsing
  }

  public StartTurnInputData(int gameId) {
    this.gameId = gameId;
  }

  public int getGameId() {
    return gameId;
  }



}
