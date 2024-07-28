package main.java.com.example.scrabble.controller_factory;

import entity.Game;
import interface_adapter.get_leaderboard.GetLeaderboardController;
import interface_adapter.get_leaderboard.GetLeaderboardPresenter;
import interface_adapter.GameViewModel;
import use_case.get_leaderboard.GetLeaderboardInteractor;
import use_case.get_leaderboard.GetLeaderboardOutputBoundary;

/**
 * Factory class for creating instances of GetLeaderboardController.
 * This class provides a static method to create a GetLeaderboardController
 * with the necessary dependencies.
 */
public class GetLeaderboardControllerFactory {
  
  private GetLeaderboardControllerFactory() {
    // This class should not be instantiated
  }

  /**
   * Creates a GetLeaderboardController with the specified GameViewModel and Game.
   * @param viewModel The GameViewModel to be used by the presenter.
   * @param game The Game entity to be used by the interactor.
   * @return A new instance of GetLeaderboardController.
   */
  public static GetLeaderboardController create(GameViewModel viewModel, Game game) {
    GetLeaderboardOutputBoundary presenter = new GetLeaderboardPresenter(viewModel);
    GetLeaderboardInteractor interactor = new GetLeaderboardInteractor(presenter, game);
    return new GetLeaderboardController(interactor);
  }
}
