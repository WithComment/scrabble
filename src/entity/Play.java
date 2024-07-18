package entity;

import java.util.LinkedList;

/**
 * Represents a Player's turn.
 * @param player The player who made the moves.
 * @param moves The moves made in the play.
 * @param score The score of the play.
 */
public class Play {
  private final Player player;
  private final LinkedList<Move> moves;

  public Play(Player player) {
    this.player = player;
    this.moves = new LinkedList<Move>();
  }

  public void addMove(Move move) {
    moves.add(move);
  }

  public Move undoMove() {
    return moves.pop();
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

  public LinkedList<Move> getMoves() {
    return moves;
  }
}
