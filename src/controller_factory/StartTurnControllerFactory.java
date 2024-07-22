package controller_factory;

import interface_adapter.GameViewModel;
import interface_adapter.start_turn.StartTurnController;
import interface_adapter.start_turn.StartTurnPresenter;
import use_case.start_turn.StartTurnInteractor;

public class StartTurnControllerFactory {
  private StartTurnControllerFactory() {
    // should not be instantiated
  }

  public static StartTurnController create(GameViewModel gameViewModel) {
    return new StartTurnController(new StartTurnInteractor(new StartTurnPresenter(gameViewModel)));
  }
}
