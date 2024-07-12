package use_case.place_letter;

import entity.Letter;

public class PlaceLetterInputData {
  private final int x;
  private final int y;
  private final Letter letter;

  public PlaceLetterInputData(
    int x,
    int y,
    Letter letter
  ) {
    this.x = x;
    this.y = y;
    this.letter = letter;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public Letter getLetter() {
    return letter;
  }
}
