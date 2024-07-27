package use_case.place_letter;

import entity.Board;
import entity.Play;

public class PlaceLetterInputData {
  private final int x;
  private final int y;
  private final Character letter;
  private final Board board;
  private final Play play;

  public PlaceLetterInputData(
    int x,
    int y,
    Character letter,
    Board board,
    Play play
  ) {
    this.x = x;
    this.y = y;
    this.letter = letter;
    this.board = board;
    this.play = play;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public Character getLetter() {
    return letter;
  }

  public Play getPlay() {
    return play;
  }

  public Board getBoard() {
    return board;
  }
}
