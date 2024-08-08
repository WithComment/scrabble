package com.example.scrabble.use_case.create_game;

import com.example.scrabble.entity.Player;

import java.util.List;

/**
 * Represents the output data for creating a game.
 * Contains the list of players and the game ID.
 */
public class CreateGameOutputData {
    private final List<Player> players;
    private final int gameId;

    /**
     * Constructs a CreateGameOutputData instance with the specified players and game ID.
     *
     * @param players the list of players
     * @param gameId the ID of the game
     */
    public CreateGameOutputData(List<Player> players, int gameId) {
        this.players = players;
        this.gameId = gameId;
    }

    /**
     * Returns the game ID.
     *
     * @return the game ID
     */
    public Integer getGameId() {
        return gameId;
    }

    /**
     * Returns the list of players.
     *
     * @return the list of players
     */
    public List<Player> getPlayers() {
        return players;
    }
}