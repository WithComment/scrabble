package entity;

/**
 * Represents a playable letter.
 */
public class Letter {
  private final char letter;
  private final int points;

  /**
   * Constructs a new Letter with the specified character and points.
   * @param letter The character representing the letter.
   * @param points The points the letter is worth.
   */
  public Letter(
    char letter,
    int points
  ) {
    this.letter = letter;
    this.points = points;
  }

  /**
   * Gets the character of the letter.
   * @return The character of the letter.
   */
  public char getLetter() {
    return letter;
  }

  /**
   * Gets the points the letter is worth.
   * @return The points the letter is worth.
   */
  public int getPoints() {
    return points;
  }

  /**
   * Checks if this letter is equal to another object.
   * Two Letter objects are considered equal if they have the same character.
   * @param o The object to compare with.
   * @return True if the objects are equal, false otherwise.
   */
  @Override
  public boolean equals(Object o) {
    return (o instanceof Letter && ((Letter) o).getLetter() == getLetter());
  }

  /**
   * Returns a string representation of the letter.
   * @return A string representation of the letter.
   */
  @Override
  public String toString() {
    return Character.toString(getLetter());
  }

  /**
   * Creates and returns a copy of this letter.
   * @return A clone of this letter.
   */
  @Override
  public Letter clone() {
    return new Letter(this.getLetter(), this.getPoints());
  }
}
