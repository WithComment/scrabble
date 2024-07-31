package com.example.scrabble.use_case.get_leaderboard;

import java.util.List;

public class GetLeaderboardInputData {
    final List<Integer> players;
    private final int gameId;

    public GetLeaderboardInputData(int gameId, List<Integer> players) {
        this.gameId = gameId;
        this.players = players;
    }

    public List<Integer> getPlayers() {
        return players;
    }

    public int getGameId() {
        return gameId;
    }
}
