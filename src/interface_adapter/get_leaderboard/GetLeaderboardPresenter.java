package interface_adapter.get_leaderboard;

import java.util.LinkedList;

import entity.LeaderboardEntry;
import interface_adapter.GameViewModel;
import use_case.get_leaderboard.*;

public class GetLeaderboardPresenter implements GetLeaderboardOutputBoundary {

    private GameViewModel viewModel;

    public GetLeaderboardPresenter(GameViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareView(GetLeaderboardOutputData outputData) {
        viewModel.setLeaderboard(new LinkedList<>() {{
            for (LeaderboardEntry entry : outputData.getLeaderboard()) {
                add(entry.getPlayer());
            }
        }});
        viewModel.firePropertyChanged();
    }
}