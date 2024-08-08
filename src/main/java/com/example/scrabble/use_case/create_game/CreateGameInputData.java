package com.example.scrabble.use_case.create_game;

import java.util.List;

/**
 * Represents the input data required to create a game.
 * Contains a list of player names.
 */
public class CreateGameInputData {
  private List<String> playerNames;

  /**
   * Default constructor for CreateGameInputData.
   */
  public CreateGameInputData() {}

  /**
   * Constructs a CreateGameInputData instance with the specified player names.
   *
   * @param playerNames the list of player names
   */
  public CreateGameInputData(List<String> playerNames) {
    this.playerNames = playerNames;
  }

  /**
   * Returns the list of player names.
   *
   * @return the list of player names
   */
  public List<String> getPlayerNames() {
    return playerNames;
  }
}