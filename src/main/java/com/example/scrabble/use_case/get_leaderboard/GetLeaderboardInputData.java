package com.example.scrabble.use_case.get_leaderboard;

import java.util.List;

public class GetLeaderboardInputData {
    final List<Integer> players;

    public GetLeaderboardInputData(List<Integer> players) {
        this.players = players;
    }

    public List<Integer> getPlayers() {
        return players;
    }
}
