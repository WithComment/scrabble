package main.java.com.example.scrabble.interface_adapter.get_leaderboard;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import entity.Player;
import interface_adapter.GameViewModel;
import use_case.get_leaderboard.*;

public class GetLeaderboardPresenter implements GetLeaderboardOutputBoundary {

    private GameViewModel viewModel;

    public GetLeaderboardPresenter(GameViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareView(GetLeaderboardOutputData outputData) {
        List<Player> leaderboard = outputData.getLeaderboard();
        List<Integer> players = new ArrayList<>();
        List<Integer> scores = new LinkedList<>();
        for (Player entry : leaderboard) {
            players.add(entry.getId());
            scores.add(entry.getScore());
        }
        viewModel.setLeaderboard(players, scores);
        viewModel.firePropertyChanged();
    }
}