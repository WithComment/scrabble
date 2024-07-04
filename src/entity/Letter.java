package entity;

public class Letter {
  private final char letter;
  private final int points;

  public Letter(
    char letter,
    int points
  ) {
    this.letter = letter;
    this.points = points;
  }

  public char getLetter() {
    return letter;
  }

  public int getPoints() {
    return points;
  }
}
