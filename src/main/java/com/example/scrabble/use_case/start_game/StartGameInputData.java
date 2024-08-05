package com.example.scrabble.use_case.start_game;

public class StartGameInputData {
  private int gameId;
  public StartGameInputData() {}
  public StartGameInputData(int gameId) { this.gameId = gameId; }

  public int getGameId() {
    return gameId;
  }
}
