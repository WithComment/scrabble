package com.example.scrabble.use_case.end_game;

/**
 * Represents the input data required to end a game.
 * Contains the game ID.
 */
public class EndGameInputData {
    private int gameId;

    /**
     * Default constructor for EndGameInputData.
     */
    public EndGameInputData() {}

    /**
     * Constructs an EndGameInputData instance with the specified game ID.
     *
     * @param gameId the ID of the game to end
     */
    public EndGameInputData(int gameId) {
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