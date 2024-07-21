package view.panels;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class LeaderboardPanel extends JPanel {
    private Map<String, JLabel> leaderboardLabels = new HashMap<String, JLabel>();

    public LeaderboardPanel(){
        this.setLayout(new GridLayout(4, 2, 10, 10));
    }
    public void addPlayer(String player){
        leaderboardLabels.put(player, new JLabel("0"));
        JLabel playerLabel = new JLabel(player);
        JLabel scoreLabel = leaderboardLabels.get(player);
        this.add(playerLabel);
        this.add(scoreLabel);
    }
    public void updateLeaderboard(String player, int score){
        leaderboardLabels.get(player).setText(Integer.toString(score));
    }
}
