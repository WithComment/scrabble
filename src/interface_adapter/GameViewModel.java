package interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.List;

import entity.Board;
import entity.Letter;
import entity.Player;

public class GameViewModel extends ViewModel {
      
  private final PropertyChangeSupport support;
  private Board board;
  private List<Player> leaderboard;
  private List<Letter> hand;
  private Player currentPlayer;
  private String errorMessage;
  private final HashMap<String, Object> propertiesChanged;

  public GameViewModel(Board board, List<Player> leaderboard, List<Letter> hand) {
    super("game");
    this.support = new PropertyChangeSupport(this);
    this.board = board;
    this.leaderboard = leaderboard;
    this.hand = hand;
    this.currentPlayer = null;
    this.errorMessage = "";
    this.propertiesChanged = new HashMap<>();
  }
  
  @Override
  public void firePropertyChanged() {
    for (String key : propertiesChanged.keySet()) {
      System.out.println(key + " set");
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

  public void setLeaderboard(List<Player> leaderboard) {
    this.leaderboard = leaderboard;
    propertiesChanged.put("leaderboard", leaderboard);
  }

  public void setHand(List<Letter> hand) {
    this.hand = hand;
    propertiesChanged.put("hand", hand);
  }

  public void setPlayer(Player player) {
    this.currentPlayer = player;
    propertiesChanged.put("player", player);
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
    propertiesChanged.put("errorMessage", errorMessage);
  }

  public Board getBoard() {
    return board;
  }

  public List<Player> getLeaderboard() {
    return leaderboard;
  }

  public List<Letter> getHand() {
    return hand;
  }

  public Player getPlayer() {
    return currentPlayer;
  }

  public String getErrorMessage() {
    return errorMessage;
  }
}
