package view;

import entity.Board;
import entity.Game;
import entity.Letter;
import entity.Player;
import entity.Tile;
import input_manager.InputManager;
import interface_adapter.GameViewModel;
import view.listeners.ConfirmPlayListener;
import view.panels.BoardPanel;
import view.panels.HandPanel;
import view.panels.LeaderboardPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;


public class View extends JPanel implements MouseListener, ActionListener, PropertyChangeListener {
    private JFrame window;
    private BoardPanel boardPanel;
    private HandPanel handPanel;
    private GameViewModel gameViewModel;
    private LeaderboardPanel leaderBoardPanel;

    public View(InputManager inputManager, GameViewModel gameViewModel) {
        this.gameViewModel = gameViewModel;
        gameViewModel.addPropertyChangeListener(this);
        window = new JFrame("Scrabble");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(1000, 1000);
        window.setVisible(true);
        window.addMouseListener(this);
        boardPanel = new BoardPanel(inputManager);
        handPanel = new HandPanel(inputManager);
        leaderBoardPanel = new LeaderboardPanel(gameViewModel.getLeaderboard());
//        leaderBoardPanel.updateLeaderboard(whatever their score is, as an int);

        JButton confirmPlayButton = new JButton("Confirm Play");
        
        confirmPlayButton.addMouseListener(new ConfirmPlayListener(inputManager));
        ArrayList<String> tempHand = new ArrayList<>();
        for (Letter l : gameViewModel.getHand()) {
            tempHand.add(String.valueOf(l.getLetter()));
        }
        handPanel.setHand(gameViewModel.getHand());

        JPanel view = new JPanel();
        view.setLayout(new FlowLayout());
        view.add(boardPanel);
        view.add(handPanel);
        view.add(confirmPlayButton);
        view.add(leaderBoardPanel);
        window.add(view);
        boardPanel.updateUI();
        handPanel.updateUI();
    }

    public void actionPerformed(ActionEvent e) {

    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void setTile(int[] coords, Tile tile) {
        boardPanel.setGridSpotToTile(coords, tile);
    }

    private void updateBoard(Board newBoard) {
        boardPanel.setBoardToState(newBoard);
        boardPanel.updateUI();
    }

    private void updateHand(List<Letter> newHand) {
        handPanel.setHand(newHand);
        handPanel.updateUI();
    }

    private void updateLeaderboard(List<Player> newLeaderboard) {
        leaderBoardPanel.update(newLeaderboard);
        leaderBoardPanel.updateUI();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Object newValue = evt.getNewValue();
        if (evt.getPropertyName().equals("board") && newValue instanceof Board) {
            updateBoard((Board) newValue);
        } else if (evt.getPropertyName().equals("hand") && newValue instanceof List) {
            updateHand((List<Letter>) evt.getNewValue());
        } else if (evt.getPropertyName().equals("player") && newValue instanceof Player) {
            // TODO: update current player
        } else if (evt.getPropertyName().equals("leaderboard") && newValue instanceof List) {
            // TODO: update leaderboard
            updateLeaderboard((List<Player>) newValue);
        }
    }
}
