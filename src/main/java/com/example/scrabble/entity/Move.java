package com.example.scrabble.entity;

/**
 * Represents a move in a Player's turn.
 */
public class Move {
  private int x;
  private int y;
  private Letter letter;

  public Move() {}

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

}