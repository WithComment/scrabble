package controller_factory;

import interface_adapter.GameViewModel;
import interface_adapter.start_turn.StartTurnController;
import interface_adapter.start_turn.StartTurnPresenter;
import use_case.start_turn.StartTurnInteractor;

/**
 * Factory class for creating instances of StartTurnController.
 * This class provides a static method to create a StartTurnController
 * with the necessary dependencies.
 */
public class StartTurnControllerFactory {
  private StartTurnControllerFactory() {
    // should not be instantiated
  }

  /**
   * Creates a StartTurnController with the specified GameViewModel.
   * @param gameViewModel The GameViewModel to be used by the presenter.
   * @return A new instance of StartTurnController.
   */
  public static StartTurnController create(GameViewModel gameViewModel) {
    return new StartTurnController(new StartTurnInteractor(new StartTurnPresenter(gameViewModel)));
  }
}
