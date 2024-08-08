package com.example.scrabble.use_case.start_game;

/**
 * Represents the output data for starting a game.
 * Contains information about whether the game start was successful.
 */
public class StartGameOutputData {

  private final boolean isSuccessful;

  /**
   * Constructs a StartGameOutputData instance with the specified success status.
   *
   * @param isSuccessful a boolean indicating whether the game start was successful
   */
  public StartGameOutputData(boolean isSuccessful) {
    this.isSuccessful = isSuccessful;
  }

  /**
   * Returns whether the game start was successful.
   *
   * @return true if the game start was successful, false otherwise
   */
  public boolean isSuccessful() {
    return isSuccessful;
  }
}