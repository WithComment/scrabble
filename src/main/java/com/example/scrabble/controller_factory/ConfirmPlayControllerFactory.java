package main.java.com.example.scrabble.controller_factory;

import interface_adapter.GameViewModel;
import interface_adapter.confirm_play.ConfirmPlayController;
import interface_adapter.confirm_play.ConfirmPlayPresenter;
import use_case.confirm_play.ConfirmPlayInputBoundary;
import use_case.confirm_play.ConfirmPlayInteractor;
import use_case.confirm_play.ConfirmPlayOutputBoundary;

/**
 * Factory class for creating instances of ConfirmPlayController.
 * This class provides a static method to create a ConfirmPlayController
 * with the necessary dependencies.
 */
public class ConfirmPlayControllerFactory {
  private ConfirmPlayControllerFactory() {
    // This class should not be instantiated
  }

  /**
   * Creates a ConfirmPlayController with the specified GameViewModel.
   * @param viewModel The GameViewModel to be used by the presenter.
   * @return A new instance of ConfirmPlayController.
   */
  public static ConfirmPlayController create(GameViewModel viewModel) {
    ConfirmPlayOutputBoundary presenter = new ConfirmPlayPresenter(viewModel);
    ConfirmPlayInputBoundary interactor = new ConfirmPlayInteractor(presenter);
    return new ConfirmPlayController(interactor);
  }
}
