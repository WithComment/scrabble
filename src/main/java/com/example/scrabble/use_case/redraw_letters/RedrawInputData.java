package com.example.scrabble.use_case.redraw_letters;

import java.util.List;

/**
 * Represents the input data required to redraw letters.
 * Contains the game ID and the list of characters to redraw.
 */
public class RedrawInputData {
    private int gameId;
    private List<String> characters;

    /**
     * Default constructor for RedrawInputData.
     */
    public RedrawInputData() {}

    /**
     * Constructs a RedrawInputData instance with the specified game ID and characters.
     *
     * @param gameId the ID of the game
     * @param letters the list of characters to redraw
     */
    public RedrawInputData(int gameId, List<String> letters) {
        this.gameId = gameId;
        this.characters = letters;
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
     * Returns the list of characters to redraw.
     *
     * @return the list of characters to redraw
     */
    public List<String> getCharacters() {
        return characters;
    }
}