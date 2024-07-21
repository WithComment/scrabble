package interface_adapter;

import entity.Board;
import entity.LeaderboardEntry;
import entity.Letter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameOverViewModel extends ViewModel{
    private final PropertyChangeSupport support;
    private Board board;
    private List<LeaderboardEntry> leaderboard;
    private ArrayList<LeaderboardEntry> winners;
    private final HashMap<String, Object> propertiesChanged;

    public GameOverViewModel(Board board, List<LeaderboardEntry> leaderboard) {
        super("Game Over");
        this.support = new PropertyChangeSupport(this);
        this.board = board;
        this.leaderboard = leaderboard;
        this.winners = new ArrayList<>();
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

    public void setWinners(ArrayList<LeaderboardEntry> winners) {
        this.winners = winners;
        propertiesChanged.put("winners", winners);
    }

    public Board getBoard() {
        return board;
    }

    public ArrayList<LeaderboardEntry> getWinners() {
        return winners;
    }

    public List<LeaderboardEntry> getLeaderboard() {
        return leaderboard;
    }
}
