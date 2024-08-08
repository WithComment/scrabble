package com.example.scrabble.use_case.confirm_play;

/**
 * Represents the input data required to confirm a play.
 * Contains the game ID.
 */
public class ConfirmPlayInputData {
  private int gameId;

  /**
   * Default constructor for ConfirmPlayInputData.
   */
  public ConfirmPlayInputData() {}

  /**
   * Constructs a ConfirmPlayInputData instance with the specified game ID.
   *
   * @param gameId the ID of the game
   */
  public ConfirmPlayInputData(int gameId) {
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