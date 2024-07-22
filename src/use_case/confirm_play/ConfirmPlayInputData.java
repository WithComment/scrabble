package use_case.confirm_play;

import entity.Board;
import entity.Play;

public class ConfirmPlayInputData {
  private final Play play;
  private final Board board;

  public ConfirmPlayInputData(Play play, Board board) {
    this.play = play;
    this.board = board;
  }

  public Play getPlay() {
    return play;
  }

  public Board getBoard() {
    return board;
  }
}
