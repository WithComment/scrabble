package com.example.scrabble.interface_adapter.get_leaderboard;

import com.example.scrabble.use_case.get_leaderboard.*;
import com.example.scrabble.entity.Player;

import java.util.List;

public class GetLeaderboardController {

    final GetLeaderboardInputBoundary getLeaderboardUseCaseInteractor;

    public GetLeaderboardController(GetLeaderboardInputBoundary getLeaderboardUseCaseInteractor) {
        this.getLeaderboardUseCaseInteractor = getLeaderboardUseCaseInteractor;
    }

    public void execute(List<Integer> players) {
        GetLeaderboardInputData getLeaderboardInputData = new GetLeaderboardInputData(players);
        getLeaderboardUseCaseInteractor.execute(getLeaderboardInputData);
    }
}
