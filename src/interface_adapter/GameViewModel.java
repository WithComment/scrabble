package interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.List;

import entity.Board;
import entity.Letter;
import entity.Play;
import entity.Player;

public class GameViewModel extends ViewModel {
      
  private final PropertyChangeSupport support;
  private Board board;
  private List<Player> leaderboard;
  private List<Letter> hand;
  private Letter selectedLetter;
  private Player currentPlayer;
  private Play currentPlay;
  private String errorMessage;
  private final HashMap<String, Object> propertiesChanged;

  public GameViewModel(Board board, List<Player> leaderboard) {
    super("game");
    this.support = new PropertyChangeSupport(this);
    this.board = board;
    this.leaderboard = leaderboard;
    this.hand = null;
    this.selectedLetter = null;
    this.currentPlayer = null;
    this.currentPlay = null;
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

  public void setLeaderboard(List<Player> leaderboard) {
    System.out.println("leaderboard size: " + Integer.toString(leaderboard.size()));
    this.leaderboard = leaderboard;
    propertiesChanged.put("leaderboard", leaderboard);
  }

  public void setHand(List<Letter> hand) {
    this.hand = hand;
    propertiesChanged.put("hand", hand);
  }

  public void setSelectedLetter(Letter letter) {
    this.selectedLetter = letter;
    propertiesChanged.put("selectedLetter", letter);
  }

  public void setPlayer(Player player) {
    this.currentPlayer = player;
    propertiesChanged.put("player", player);
    this.hand = player.getInventory();
    propertiesChanged.put("hand", hand);
  }

  public void setPlay(Play play) {
    this.currentPlay = play;
  }

  public Play getPlay() {
    return currentPlay;
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

  public Letter getSelectedLetter() {
    return selectedLetter;
  }

  public Player getPlayer() {
    return currentPlayer;
  }

  public String getErrorMessage() {
    return errorMessage;
  }
}
