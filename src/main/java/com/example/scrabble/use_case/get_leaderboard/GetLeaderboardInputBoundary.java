package com.example.scrabble.use_case.get_leaderboard;

import com.example.scrabble.entity.Game;

public interface GetLeaderboardInputBoundary {
    Game execute(GetLeaderboardInputData data);
}
