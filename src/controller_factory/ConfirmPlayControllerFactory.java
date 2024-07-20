package controller_factory;

import entity.Board;
import entity.Play;
import interface_adapter.GameViewModel;
import interface_adapter.place_letter.ConfirmPlayController;
import use_case.confirm_play.ConfirmPlayOutputBoundary;

public class ConfirmPlayControllerFactory {
  
  private ConfirmPlayControllerFactory() {
    // This class should not be instantiated
  }

  public static ConfirmPlayController create(GameViewModel viewModel, Board board, Play play) {
    ConfirmPlayOutputBoundary presenter = new ConfirmPlayPresenter(viewModel);
  }
}
