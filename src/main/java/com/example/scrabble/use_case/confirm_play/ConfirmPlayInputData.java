package com.example.scrabble.use_case.confirm_play;

public class ConfirmPlayInputData {
  private int gameId;

  public ConfirmPlayInputData() {}

  public ConfirmPlayInputData(int gameId) {
    this.gameId = gameId;
  }

  public int getGameId() {
    return gameId;
  }
}
