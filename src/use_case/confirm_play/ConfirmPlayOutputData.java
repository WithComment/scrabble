package use_case.confirm_play;

import entity.Board;
import entity.Player;

public class ConfirmPlayOutputData {
  private final Board board;
  private final Player player;


  public ConfirmPlayOutputData(Board board, Player player) {
    this.board = board;
    this.player = player;
  }

  public Board getBoard() {
    return board;
  }


  public Player getPlayer() {
    return player;
  }
}
