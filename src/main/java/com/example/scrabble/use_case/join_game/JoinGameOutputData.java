package com.example.scrabble.use_case.join_game;

/**
 * Represents the output data for joining a game.
 * Contains the player's name.
 */
public class JoinGameOutputData {
    private final String playerName;

    /**
     * Constructs a JoinGameOutputData instance with the specified player name.
     *
     * @param playerName the name of the player
     */
    public JoinGameOutputData(String playerName){
        this.playerName = playerName;
    }

    /**
     * Returns the player's name.
     *
     * @return the player's name
     */
    public String getPlayerName() {
        return playerName;
    }
}