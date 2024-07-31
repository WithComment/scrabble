package com.example.scrabble.use_case.start_turn;

import com.example.scrabble.entity.TurnManager;

public class StartTurnInputData {
  private int gameId;

  public StartTurnInputData() {}

  public StartTurnInputData(int gameId) {
    this.gameId = gameId;
  }

  public int getGameId() {
    return gameId;
  }
}
