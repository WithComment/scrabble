package com.example.scrabble.use_case.confirm_play;

public class ConfirmPlayInputData {
  private final int gameId;

  public ConfirmPlayInputData(int gameId) {
    this.gameId = gameId;
  }

  public int getGameId() {
    return gameId;
  }
}
