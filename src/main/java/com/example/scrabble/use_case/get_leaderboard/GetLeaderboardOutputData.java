package com.example.scrabble.use_case.get_leaderboard;

import com.example.scrabble.entity.Player;
import java.util.List;

/**
 * Represents the output data for getting the leaderboard.
 * Contains the list of players in the leaderboard.
 */
public class GetLeaderboardOutputData {
    private final List<Player> leaderboard;

    /**
     * Constructs a GetLeaderboardOutputData instance with the specified leaderboard.
     *
     * @param leaderboard the list of players in the leaderboard
     */
    public GetLeaderboardOutputData(List<Player> leaderboard) {
        this.leaderboard = leaderboard;
    }

    /**
     * Returns the list of players in the leaderboard.
     *
     * @return the list of players in the leaderboard
     */
    public List<Player> getLeaderboard() {
        return leaderboard;
    }
}