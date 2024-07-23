package view.panels;

import javax.swing.*;

import java.awt.GridLayout;
import java.util.List;
public class LeaderboardPanel extends JPanel {
    public LeaderboardPanel(List<Integer> players, List<Integer> scores){
        update(players, scores);
    }

    public void update(List<Integer> players, List<Integer> scores) {
        this.removeAll();
        this.setLayout(new GridLayout(4, 2, 10, 10));
        for (int i = 0; i < players.size(); i++){
            this.add(new JLabel("Player " + Integer.toString(players.get(i))));
            this.add(new JLabel(Integer.toString(scores.get(i))));
        }
    }
}
