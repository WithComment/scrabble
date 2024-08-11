package com.example.scrabble.use_case.contest;

/**
 * Represents the input data required for a contest.
 * Contains the game ID, player ID, and a boolean indicating if it is a contest.
 */
public class ContestInputData {
    private int gameId;
    private int playerId;
    private boolean isContest;

    /**
     * Default constructor for ContestInputData.
     */
    public ContestInputData() {}

    /**
     * Constructs a ContestInputData instance with the specified game ID, player ID, and contest status.
     *
     * @param gameId the ID of the game
     * @param playerId the ID of the player
     * @param isContest true if it is a contest, false otherwise
     */
    public ContestInputData(int gameId, int playerId, boolean isContest) {
        this.gameId = gameId;
        this.playerId = playerId;
        this.isContest = isContest;
    }

    /**
     * Returns the game ID.
     *
     * @return the game ID
     */
    public int getGameId() {
        return gameId;
    }

    /**
     * Returns the player ID.
     *
     * @return the player ID
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Returns whether it is a contest.
     *
     * @return true if it is a contest, false otherwise
     */
    public boolean getIsContest() {
        return isContest;
    }
}