package controller_factory;

import interface_adapter.get_leaderboard.GetLeaderboardController;
import interface_adapter.get_leaderboard.GetLeaderboardPresenter;
import interface_adapter.GameViewModel;
import use_case.get_leaderboard.GetLeaderboardInteractor;
import use_case.get_leaderboard.GetLeaderboardOutputBoundary;

public class GetLeaderboardControllerFactory {
  
  private GetLeaderboardControllerFactory() {
    // This class should not be instantiated
  }

  public static GetLeaderboardController create(GameViewModel viewModel) {
    GetLeaderboardOutputBoundary presenter = new GetLeaderboardPresenter(viewModel);
    GetLeaderboardInteractor interactor = new GetLeaderboardInteractor(presenter);
    return new GetLeaderboardController(interactor);
  }
}
