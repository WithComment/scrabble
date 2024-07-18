package entity;

/**
 * Represents a move in a Player's turn.
 * @param x The x-coordinate of the move.
 * @param y The y-coordinate of the move.
 * @param letter The letter placed on the board.
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