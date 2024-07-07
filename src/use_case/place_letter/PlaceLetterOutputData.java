package use_case.place_letter;

import entity.Board;

public class PlaceLetterOutputData {
  private final Board board;

  public PlaceLetterOutputData(Board board) {
    this.board = board;
  }

  public Board getBoard() {
    return board;
  }
}
