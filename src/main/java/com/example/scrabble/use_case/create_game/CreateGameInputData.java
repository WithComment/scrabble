package com.example.scrabble.use_case.create_game;

import java.util.List;

public class CreateGameInputData {
  private List<String> playerNames;

  public CreateGameInputData() {}

  public CreateGameInputData(List<String> playerNames) {
    this.playerNames = playerNames;
  }

  public List<String> getPlayerNames() {
    return playerNames;
  }
}
