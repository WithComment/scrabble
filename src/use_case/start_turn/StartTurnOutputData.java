package use_case.start_turn;

import entity.Play;
import entity.Player;

public class StartTurnOutputData {
  private final Player player;
  private final Play play;

  public StartTurnOutputData(Player player, Play play) {
    this.player = player;
    this.play = play;
  }

  public Player getPlayer() {
    return player;
  }

  public Play getPlay() {
    return play;
  }
}
