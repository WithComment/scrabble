package com.example.scrabble.use_case.place_letter;

/**
 * Represents the input data required to place a letter on the board.
 * Contains the game ID, coordinates, and the letter to place.
 */
public class PlaceLetterInputData {
  private int gameId;
  private int x;
  private int y;
  private char letter;

  /**
   * Default constructor for PlaceLetterInputData.
   */
  public PlaceLetterInputData() {}

  /**
   * Constructs a PlaceLetterInputData instance with the specified game ID, coordinates, and letter.
   *
   * @param gameId the ID of the game
   * @param x the x-coordinate of the letter to place
   * @param y the y-coordinate of the letter to place
   * @param letter the letter to place on the board
   */
  public PlaceLetterInputData(
    int gameId,
    int x,
    int y,
    char letter
  ) {
    this.gameId = gameId;
    this.x = x;
    this.y = y;
    this.letter = letter;
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
   * Returns the x-coordinate of the letter to place.
   *
   * @return the x-coordinate of the letter to place
   */
  public int getX() {
    return x;
  }

  /**
   * Returns the y-coordinate of the letter to place.
   *
   * @return the y-coordinate of the letter to place
   */
  public int getY() {
    return y;
  }

  /**
   * Returns the letter to place on the board.
   *
   * @return the letter to place on the board
   */
  public char getLetter() {
    return letter;
  }
}