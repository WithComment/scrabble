package controller_factory;

import interface_adapter.GameViewModel;
import interface_adapter.confirm_play.ConfirmPlayController;
import interface_adapter.confirm_play.ConfirmPlayPresenter;
import use_case.confirm_play.ConfirmPlayInputBoundary;
import use_case.confirm_play.ConfirmPlayInteractor;
import use_case.confirm_play.ConfirmPlayOutputBoundary;

public class ConfirmPlayControllerFactory {
  private ConfirmPlayControllerFactory() {
    // This class should not be instantiated
  }

  public static ConfirmPlayController create(GameViewModel viewModel) {
    ConfirmPlayOutputBoundary presenter = new ConfirmPlayPresenter(viewModel);
    ConfirmPlayInputBoundary interactor = new ConfirmPlayInteractor(presenter);
    return new ConfirmPlayController(interactor);
  }
}
