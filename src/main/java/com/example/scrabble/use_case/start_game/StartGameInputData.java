package com.example.scrabble.use_case.start_game;

/**
 * Represents the input data required to start a game.
 * Contains the game ID.
 */
public class StartGameInputData {
  private int gameId;

  /**
   * Default constructor for StartGameInputData.
   */
  public StartGameInputData() {}

  /**
   * Constructs a StartGameInputData instance with the specified game ID.
   *
   * @param gameId the ID of the game to start
   */
  public StartGameInputData(int gameId) {
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