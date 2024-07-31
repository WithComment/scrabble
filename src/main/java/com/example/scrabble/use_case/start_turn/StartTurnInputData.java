package com.example.scrabble.use_case.start_turn;

import com.example.scrabble.entity.TurnManager;

public class StartTurnInputData {
  private final TurnManager turnManager;

  public StartTurnInputData(TurnManager turnManager) {
    this.turnManager = turnManager;
  }

  public TurnManager getTurnManager() {
    return turnManager;
  }
}
