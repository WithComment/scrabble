package view.panels;

import javax.swing.*;

import entity.Player;

import java.awt.GridLayout;
import java.util.List;
public class LeaderboardPanel extends JPanel {
    public LeaderboardPanel(List<Player> players){
        update(players);
    }

    public void update(List<Player> players) {
        this.removeAll();
        this.setLayout(new GridLayout(4, 2, 10, 10));
        for (Player player : players) {
            this.add(new JLabel("Player " + Integer.toString(player.getId())));
            this.add(new JLabel(Integer.toString(player.getScore())));
        }
    }
}
