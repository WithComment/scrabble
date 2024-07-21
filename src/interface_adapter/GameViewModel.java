package interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.List;

import entity.Board;
import entity.LeaderboardEntry;
import entity.Letter;

public class GameViewModel extends ViewModel {
      
  private final PropertyChangeSupport support;
  private Board board;
  private List<LeaderboardEntry> leaderboard;
  private List<Letter> hand;
  private String errorMessage;
  private final HashMap<String, Object> propertiesChanged;

  public GameViewModel(Board board, List<LeaderboardEntry> leaderboard, List<Letter> hand) {
    super("game");
    this.support = new PropertyChangeSupport(this);
    this.board = board;
    this.leaderboard = leaderboard;
    this.hand = hand;
    this.errorMessage = "";
    this.propertiesChanged = new HashMap<>();
  }
  
  @Override
  public void firePropertyChanged() {
    for (String key : propertiesChanged.keySet()) {
      support.firePropertyChange(key, null, propertiesChanged.get(key));
    }
    propertiesChanged.clear();
  }

  @Override
  public void addPropertyChangeListener(PropertyChangeListener listener) {
    support.addPropertyChangeListener(listener);
  }

  public void setBoard(Board board) {
    this.board = board;
    propertiesChanged.put("board", board);
  }

  public void setLeaderboard(List<LeaderboardEntry> leaderboard) {
    this.leaderboard = leaderboard;
    propertiesChanged.put("leaderboard", leaderboard);
  }

  public void setHand(List<Letter> hand) {
    this.hand = hand;
    propertiesChanged.put("hand", hand);
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
    propertiesChanged.put("errorMessage", errorMessage);
  }

  public Board getBoard() {
    return board;
  }

  public List<LeaderboardEntry> getLeaderboard() {
    return leaderboard;
  }

  public List<Letter> getHand() {
    return hand;
  }

  public String getErrorMessage() {
    return errorMessage;
  }
}
