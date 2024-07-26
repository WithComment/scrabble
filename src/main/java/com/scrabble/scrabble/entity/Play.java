package entity;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a Player's turn.
 * @param player The player who made the moves.
 * @param moves The moves made in the play.
 * @param words The words formed in the play.
 */
public class Play {
  private final Player player;
  private final List<Move> moves;
  private List<String> words;

  public Play(Player player) {
    this.player = player;
    this.moves = new LinkedList<Move>();
  }

  public void addMove(Move move) {
    moves.add(move);
  }

  public Move undoMove() {
    return moves.removeLast();
  }

  public Move removeMove(int x, int y) {
    for (int i = 0; i < moves.size(); i++) {
      if (moves.get(i).getX() == x && moves.get(i).getY() == y) {
        return moves.remove(i);
      }
    }
    return null;
  }

  public Player getPlayer() {
    return player;
  }

  public List<Move> getMoves() {
    return moves;
  }

  public void setWords(List<String> words) {
    this.words = words;
  }

  public List<String> getWords() {
    return words;
  }
}
