package use_case.confirm_play;

import java.util.List;

import entity.Board;
import entity.Player;

public class ConfirmPlayOutputData {
  private final Board board;
  private final List<Player> leaderboard;

  public ConfirmPlayOutputData(Board board, List<Player> leaderboard) {
    this.board = board;
    this.leaderboard = leaderboard;
  }

  public Board getBoard() {
    return board;
  }

  public List<Player> getLeaderboard() {
    return leaderboard;
  }
}
