package com.example.scrabble.use_case.end_turn;

/**
 * Represents the output data for ending a turn.
 * Contains the game ID.
 */
public class EndTurnOutputData {
    private int gameId;

    /**
     * Default constructor for EndTurnOutputData.
     */
    public EndTurnOutputData() {}

    /**
     * Constructs an EndTurnOutputData instance with the specified game ID.
     *
     * @param gameId the ID of the game
     */
    public EndTurnOutputData(int gameId) {
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