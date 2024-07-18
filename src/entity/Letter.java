package entity;

/**
 * Represents a playable letter.
 * @param letter The letter.
 * @param points The points the letter is worth.
 */
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

  /**
   * Two Letter are equal if they have the same letter.
   */
  @Override
  public boolean equals(Object o) {
    return (o instanceof Letter && ((Letter) o).getLetter() == getLetter());
  }

  @Override
  public String toString() {
    return Character.toString(getLetter());
  }

  @Override
  public Letter clone() {
    return new Letter(this.getLetter(), this.getPoints());
  }
}
