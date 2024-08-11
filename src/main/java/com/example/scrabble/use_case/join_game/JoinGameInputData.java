package com.example.scrabble.use_case.join_game;

/**
 * Represents the input data required to join a game.
 * Contains the player's name and the game ID.
 */
public class JoinGameInputData {
    private String playerName;
    private int gameId;

    /**
     * Default constructor for JoinGameInputData.
     */
    public JoinGameInputData() {}

    /**
     * Constructs a JoinGameInputData instance with the specified player name and game ID.
     *
     * @param playerName the name of the player
     * @param gameId the ID of the game
     */
    public JoinGameInputData(String playerName, int gameId){
        this.playerName = playerName;
        this.gameId = gameId;
    }

    /**
     * Returns the player's name.
     *
     * @return the player's name
     */
    public String getPlayerName() {
        return playerName;
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