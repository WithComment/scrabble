package com.example.scrabble.use_case.end_turn;

/**
 * Represents the input data required to end a turn.
 * Contains the game ID.
 */
public class EndTurnInputData {
    private int gameId;

    /**
     * Default constructor for EndTurnInputData.
     * Empty constructor for automatic parsing.
     */
    public EndTurnInputData() {
        // Empty constructor for automatic parsing
    }

    /**
     * Constructs an EndTurnInputData instance with the specified game ID.
     *
     * @param gameId the ID of the game
     */
    public EndTurnInputData(int gameId) {
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