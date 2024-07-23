package interface_adapter.get_leaderboard;

import java.util.LinkedList;
import java.util.List;

import entity.LeaderboardEntry;
import interface_adapter.GameViewModel;
import entity.Player;
import use_case.get_leaderboard.*;

public class GetLeaderboardPresenter implements GetLeaderboardOutputBoundary {

    private GameViewModel viewModel;

    public GetLeaderboardPresenter(GameViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareView(GetLeaderboardOutputData outputData) {
        List<Integer> players = new LinkedList<>();
        List<Integer> scores = new LinkedList<>();
        for (LeaderboardEntry entry : outputData.getLeaderboard()) {
            players.add(entry.getPlayer().getId());
            scores.add(entry.getScore());
        }
        viewModel.setLeaderboard(players, scores);
        viewModel.firePropertyChanged();
    }
}