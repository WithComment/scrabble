package use_case.confirm_play;

import entity.Board;

public class ConfirmPlayOutputData {
  private final Board board;

  public ConfirmPlayOutputData(Board board) {
    this.board = board;
  }

  public Board getBoard() {
    return board;
  }
}
