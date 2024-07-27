package interface_adapter.start_turn;

import entity.TurnManager;
import use_case.start_turn.StartTurnInputBoundary;
import use_case.start_turn.StartTurnInputData;

public class StartTurnController {
  
  private StartTurnInputBoundary interactor;
  
  public StartTurnController(StartTurnInputBoundary interactor) {
    this.interactor = interactor;
  }

  public void execute(TurnManager turnManager) {
    interactor.execute(new StartTurnInputData(turnManager));
  }
}
