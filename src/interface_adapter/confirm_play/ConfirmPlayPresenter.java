package interface_adapter.confirm_play;

import interface_adapter.GameViewModel;
import use_case.confirm_play.ConfirmPlayOutputBoundary;
import use_case.confirm_play.ConfirmPlayOutputData;

public class ConfirmPlayPresenter implements ConfirmPlayOutputBoundary {
    private GameViewModel gameViewModel;

    public ConfirmPlayPresenter(GameViewModel gameViewModel) {
        this.gameViewModel = gameViewModel;
    }

    public GameViewModel getGameViewModel() {
        return gameViewModel;
    }

    @Override
    public void prepareSuccessView(ConfirmPlayOutputData data) {
        gameViewModel.setBoard(data.getBoard());
        gameViewModel.setLeaderboard(data.getLeaderboard());
        gameViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        gameViewModel.setErrorMessage(error);
        gameViewModel.firePropertyChanged();
    }
}
