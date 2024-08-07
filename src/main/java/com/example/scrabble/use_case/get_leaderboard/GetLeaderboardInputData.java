package com.example.scrabble.use_case.get_leaderboard;

import java.util.List;

public class GetLeaderboardInputData {
//    private List<Integer> players;
    private int gameId;

    public GetLeaderboardInputData() {}

    public GetLeaderboardInputData(int gameId, List<Integer> players) {
        this.gameId = gameId;
//        this.players = players;
    }

    public GetLeaderboardInputData(int gameId) {
        this.gameId = gameId;
    }

//    public List<Integer> getPlayers() {
//        return players;
//    }

    public int getGameId() {
        return gameId;
    }
}
