package com.example.scrabble.use_case.get_winner;

import use_case.get_leaderboard.GetLeaderboardOutputData;

public interface GetWinnerOutputBoundary {
    void prepareView(GetWinnerOutputData outputData);
}
