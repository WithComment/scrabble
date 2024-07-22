package view.panels;

import javax.swing.*;

import entity.Player;

import java.awt.GridLayout;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class LeaderboardPanel extends JPanel {
    private Map<String, JLabel> leaderboardLabels = new HashMap<String, JLabel>();

    public LeaderboardPanel(List<Player> players){
        this.setLayout(new GridLayout(4, 2, 10, 10));
        for (Player player : players) {
            addPlayer(Integer.toString(player.getId()));
        }
    }
    public void addPlayer(String player){
        leaderboardLabels.put(player, new JLabel("0"));
        JLabel playerLabel = new JLabel(player);
        JLabel scoreLabel = leaderboardLabels.get(player);
        this.add(playerLabel);
        this.add(scoreLabel);
    }
    public void updateLeaderboard(List<Player> players){
        for (Player player : players) {
            leaderboardLabels.get(Integer.toString(player.getId())).setText(Integer.toString(player.getScore()));
        }
    }
}
