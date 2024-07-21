package interface_adapter.get_leaderboard;

import interface_adapter.GameViewModel;
import use_case.get_leaderboard.*;

public class GetLeaderboardPresenter implements GetLeaderboardOutputBoundary {

    private GameViewModel viewModel;

    public GetLeaderboardPresenter(GameViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareView(GetLeaderboardOutputData outputData) {
        viewModel.setLeaderboard(outputData.getLeaderboard());
        viewModel.firePropertyChanged();
    }
}