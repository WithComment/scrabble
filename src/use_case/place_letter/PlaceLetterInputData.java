package use_case.place_letter;

import entity.Letter;
import entity.Play;
import entity.Player;
import entity.Tile;

public class PlaceLetterInputData {
  private final int x;
  private final int y;
  private final Letter letter;
  private final Tile tile;
  private final Player player;
  private final Play play;

  public PlaceLetterInputData(int x, int y, Letter letter, Tile tile, Player player) {
    this.x = x;
    this.y = y;
    this.letter = letter;
    this.tile = tile;
    this.player = player;
    this.play = player.getCurrentPlay();
  }

  public PlaceLetterInputData(
    int x,
    int y,
    Letter letter,
    Tile tile, 
    Play play, 
    Player player
  ) {
    this.x = x;
    this.y = y;
    this.letter = letter;
    this.tile = tile;
    this.player = player;
    this.play = play;
  }
}
