package com.example.scrabble.entity;

/**
 * Represents a move in a Player's turn.
 */
public class Move {
  private final int x;
  private final int y;
  private final Letter letter;

  /**
   * Constructs a new Move with the specified coordinates and letter.
   * @param x The x-coordinate of the move.
   * @param y The y-coordinate of the move.
   * @param letter The letter placed on the board.
   */
  public Move(int x, int y, Letter letter) {
    this.x = x;
    this.y = y;
    this.letter = letter;
  }

  /**
   * Gets the x-coordinate of the move.
   * @return The x-coordinate of the move.
   */
  public int getX() {
    return x;
  }

  /**
   * Gets the y-coordinate of the move.
   * @return The y-coordinate of the move.
   */
  public int getY() {
    return y;
  }

  /**
   * Gets the letter placed on the board.
   * @return The letter placed on the board.
   */
  public Letter getLetter() {
    return letter;
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClass() != other.getClass()) {
      return false;
    }
    Move move = (Move) other;
    return x == move.x && y == move.y && letter.equals(move.letter);
  }
}