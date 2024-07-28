package com.example.scrabble.use_case.place_letter;

public class PlaceLetterInputData {
  private final int gameId;
  private final int x;
  private final int y;
  private final char letter;

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

  public int getGameId() {
    return gameId;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public Character getLetter() {
    return letter;
  }
}
