package entity;

/**
 * This dataclass is used to record where a player has put a single letter in their turn.
 * It is used only within the play entity which
 */
public class Move {
  private final int x;
  private final int y;
  private final Letter letter;

  public Move(int x, int y, Letter letter) {
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