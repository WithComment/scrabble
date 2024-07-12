package entity;

import java.util.LinkedList;

public class Play {
  private final Player player;
  private final LinkedList<Move> moves;
  private int score;

  public Play(Player player) {
    this.player = player;
    this.moves = new LinkedList<Move>();
    this.score = 0;
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

  public int getScore() {
    return score;
  }
}
