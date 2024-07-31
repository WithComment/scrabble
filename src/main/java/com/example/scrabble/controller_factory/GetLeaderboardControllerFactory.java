package com.example.scrabble.controller_factory;

import com.example.scrabble.entity.Game;
import com.example.scrabble.interface_adapter.get_leaderboard.GetLeaderboardController;
import com.example.scrabble.interface_adapter.get_leaderboard.GetLeaderboardPresenter;
import com.example.scrabble.interface_adapter.GameViewModel;
import com.example.scrabble.use_case.get_leaderboard.GetLeaderboardInteractor;
import com.example.scrabble.use_case.get_leaderboard.GetLeaderboardOutputBoundary;

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
