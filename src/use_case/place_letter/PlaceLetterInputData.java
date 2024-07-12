package use_case.place_letter;

import entity.Board;
import entity.Letter;
import entity.Play;

public class PlaceLetterInputData {
  private final int x;
  private final int y;
  private final Letter letter;
  private final Play play;
  private final Board board;

  public PlaceLetterInputData(
    int x,
    int y,
    Letter letter,
    Play play, 
    Board board
  ) {
    this.x = x;
    this.y = y;
    this.letter = letter;
    this.play = play;
    this.board = board;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public Letter getLetter() {
    return letter;
  }

  public Play getPlay() {
    return play;
  }

  public Board getBoard() {
    return board;
  }
}
