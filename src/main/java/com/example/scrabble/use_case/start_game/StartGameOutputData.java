package com.example.scrabble.use_case.start_game;

public class StartGameOutputData {

  private final boolean isSuccessful;

  public StartGameOutputData(boolean isSuccessful) { this.isSuccessful = isSuccessful; }

  public boolean isSuccessful() {
    return isSuccessful;
  }
}
