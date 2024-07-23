package interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entity.Board;
import entity.Play;

public class GameViewModel extends ViewModel {
      
  private final PropertyChangeSupport support;
  private Board board; //TODO
  private List<Integer> leaderboard;
  private List<Integer> leaderboardScores;
  private List<Character> hand;
  private char selectedLetter;
  private int currentPlayer;
  private Play currentPlay; //TODO
  private String errorMessage;
  private final HashMap<String, Object> propertiesChanged;

  public GameViewModel(Board board, List<Integer> leaderboard) {
    super("game");
    this.support = new PropertyChangeSupport(this);
    this.board = board;
    this.leaderboard = leaderboard;
    this.hand = null;
    this.selectedLetter = '\0';
    this.currentPlayer = -1;
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

  public void setLeaderboard(List<Integer> leaderboard, List<Integer> scores) {
    System.out.println("leaderboard size: " + Integer.toString(leaderboard.size()));
    this.leaderboard = leaderboard;
    this.leaderboardScores = scores;
    ArrayList<Object> args = new ArrayList<>();
    args.add(leaderboard);
    args.add(scores);
    propertiesChanged.put("leaderboard", args);
  }

  public void setHand(List<Character> hand) {
    this.hand = hand;
    propertiesChanged.put("hand", hand);
  }

  public void setSelectedLetter(char letter) {
    this.selectedLetter = letter;
    propertiesChanged.put("selectedLetter", letter);
  }

  public void setPlayer(int player) {
    this.currentPlayer = player;
    propertiesChanged.put("player", player);
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

  public List<Integer> getLeaderboard() {
    return leaderboard;
  }

  public List<Integer> getScores() {
    return leaderboardScores;
  }

  public List<Character> getHand() {
    return hand;
  }

  public char getSelectedLetter() {
    return selectedLetter;
  }

  public int getPlayer() {
    return currentPlayer;
  }

  public String getErrorMessage() {
    return errorMessage;
  }
}
