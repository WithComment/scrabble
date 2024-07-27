package use_case.start_turn;

import entity.TurnManager;

public class StartTurnInputData {
  private final TurnManager turnManager;

  public StartTurnInputData(TurnManager turnManager) {
    this.turnManager = turnManager;
  }

  public TurnManager getTurnManager() {
    return turnManager;
  }
}
