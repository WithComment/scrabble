package com.example.scrabble.use_case.place_letter;

public class PlaceLetterInputData {
  private int gameId;
  private int x;
  private int y;
  private char letter;

  public PlaceLetterInputData() {}

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

  public char getLetter() {
    return letter;
  }
}
