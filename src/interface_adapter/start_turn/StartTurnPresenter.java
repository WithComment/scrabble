package interface_adapter.start_turn;

import interface_adapter.GameViewModel;
import use_case.start_turn.StartTurnOutputBoundary;
import use_case.start_turn.StartTurnOutputData;

public class StartTurnPresenter implements StartTurnOutputBoundary {
  private GameViewModel gameViewModel;

  public StartTurnPresenter(GameViewModel gameViewModel) {
    this.gameViewModel = gameViewModel;
  }

  @Override
  public void prepareView(StartTurnOutputData data) {
    gameViewModel.setPlayer(data.getPlayer());
    gameViewModel.setPlay(data.getPlay());
    gameViewModel.firePropertyChanged();
  }
}
