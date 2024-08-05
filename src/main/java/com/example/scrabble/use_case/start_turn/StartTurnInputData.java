package com.example.scrabble.use_case.start_turn;
import java.util.List;




public class StartTurnInputData {
  private static int gameId;

  public StartTurnInputData() {
    // Empty constructor for automatic parsing
  }

  public StartTurnInputData(int gameId) {
    StartTurnInputData.gameId = gameId;
  }

  public static int getGameId() {
    return gameId;
  }



}
