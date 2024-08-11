package com.example.scrabble.use_case.remove_letter;

/**
 * Represents the input data required to remove a letter from the board.
 * Contains the game ID and the coordinates of the letter to remove.
 */
public class RemoveLetterInputData {
    private int gameId;
    private int x;
    private int y;

    /**
     * Default constructor for RemoveLetterInputData.
     */
    public RemoveLetterInputData() {}

    /**
     * Constructs a RemoveLetterInputData instance with the specified game ID and coordinates.
     *
     * @param gameId the ID of the game
     * @param x the x-coordinate of the letter to remove
     * @param y the y-coordinate of the letter to remove
     */
    public RemoveLetterInputData(int gameId, int x, int y) {
        this.gameId = gameId;
        this.x = x;
        this.y = y;
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
     * Returns the x-coordinate of the letter to remove.
     *
     * @return the x-coordinate of the letter to remove
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of the letter to remove.
     *
     * @return the y-coordinate of the letter to remove
     */
    public int getY() {
        return y;
    }
}