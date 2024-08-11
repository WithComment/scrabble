package com.example.scrabble.use_case.get_leaderboard;

import java.util.List;

/**
 * Represents the input data required to get the leaderboard.
 * Contains the game ID and optionally a list of player IDs.
 */
public class GetLeaderboardInputData {
    //    private List<Integer> players;
    private int gameId;

    /**
     * Default constructor for GetLeaderboardInputData.
     */
    public GetLeaderboardInputData() {}

    /**
     * Constructs a GetLeaderboardInputData instance with the specified game ID and list of player IDs.
     *
     * @param gameId the ID of the game
     * @param players the list of player IDs
     */
    public GetLeaderboardInputData(int gameId, List<Integer> players) {
        this.gameId = gameId;
        //        this.players = players;
    }

    /**
     * Constructs a GetLeaderboardInputData instance with the specified game ID.
     *
     * @param gameId the ID of the game
     */
    public GetLeaderboardInputData(int gameId) {
        this.gameId = gameId;
    }

    /**
     * Returns the game ID.
     *
     * @return the game ID
     */
    public int getGameId() {
        return gameId;
    }
}