package com.example.scrabble.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a Player's turn.
 */
public class Play {
  private final Player player;
  private final List<Move> moves;
  private List<String> words;

  /**
   * Constructs a new Play for the specified player.
   * @param player The player making the play.
   */
  @JsonCreator
  public Play(@JsonProperty("player") Player player) {
    this.player = player;
    this.moves = new LinkedList<>();
  }

  /**
   * Adds a move to the play.
   * @param move The move to add.
   */
  public void addMove(Move move) {
    moves.add(move);
  }

  /**
   * Undoes the last move made in the play.
   * @return The move that was undone.
   */
  public Move undoMove() {
    return moves.removeLast();
  }

  /**
   * Removes a move from the play based on its coordinates.
   * @param x The x-coordinate of the move to remove.
   * @param y The y-coordinate of the move to remove.
   * @return The move that was removed, or null if no move was found at the specified coordinates.
   */
  public Move removeMove(int x, int y) {
    for (int i = 0; i < moves.size(); i++) {
      if (moves.get(i).getX() == x && moves.get(i).getY() == y) {
        return moves.remove(i);
      }
    }
    return null;
  }

  /**
   * Gets the player making the play.
   * @return The player making the play.
   */
  public Player getPlayer() {
    return player;
  }

    /**
     * Gets the list of moves made in the play.
     * @return A LinkedList of moves made in the play.
     */
  public List<Move> getMoves() {
    return moves;
  }

  /**
   * Sets the list of words formed in the play.
   * @param words The list of words formed in the play.
   */
  public void setWords(List<String> words) {
    this.words = words;
  }

  /**
   * Gets the list of words formed in the play.
   * @return A list of words formed in the play.
   */
  public List<String> getWords() {
    return words;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj){return true;}

    if (obj == null || getClass() != obj.getClass()){return false;}

    Play other = (Play) obj;

    return  (player.equals(other.player)) &&
            (moves.equals(other.moves)) &&
            (words.equals(other.words));
  }
}
