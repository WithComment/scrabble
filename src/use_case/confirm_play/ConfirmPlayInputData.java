package use_case.confirm_play;

import entity.Board;
import entity.Play;

public class ConfirmPlayInputData {
  private final Play play;
  private final Board board;
  private final boolean isFirstPlay;

  public ConfirmPlayInputData(Play play, Board board, boolean isFirstPlay) {
    this.play = play;
    this.board = board;
    this.isFirstPlay = isFirstPlay;
  }

  public Play getPlay() {
    return play;
  }

  public Board getBoard() {
    return board;
  }

  public boolean isFirstPlay() {
    return isFirstPlay;
  }
}
