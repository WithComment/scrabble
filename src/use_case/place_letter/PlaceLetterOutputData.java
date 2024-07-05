package use_case.place_letter;

import entity.Board;
import entity.Play;
import entity.Player;

public class PlaceLetterOutputData {
  private final Board board;
  private final Player player;
  private final Play play;

  public PlaceLetterOutputData(Board board, Player player, Play play) {
    this.board = board;
    this.player = player;
    this.play = play;
  }
}
