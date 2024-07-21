package view;

import entity.Board;
import entity.Game;
import entity.Letter;
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
        leaderBoardPanel = new LeaderboardPanel();
        leaderBoardPanel.addPlayer("Player Name 1"); // Replace with players actual name or ID as a string NOT as a Player object
        leaderBoardPanel.addPlayer("Player Name 2");
        leaderBoardPanel.addPlayer("Player Name 3");
        leaderBoardPanel.addPlayer("Player Name 4");
//        leaderBoardPanel.updateLeaderboard(whatever their score is, as an int);

        JButton confirmPlayButton = new JButton("Confirm Play");
        confirmPlayButton.addMouseListener(new ConfirmPlayListener(inputManager));
        ArrayList<String> tempHand = new ArrayList<>();
        System.out.println(inputManager.getGame().getTurnManager().GetCurrentPlayer().getId());
        for (Letter l : inputManager.getGame().getTurnManager().GetCurrentPlayer().getInventory()) {
            tempHand.add(String.valueOf(l.getLetter()));
        }
        handPanel.setHand(inputManager.getGame().getTurnManager().GetCurrentPlayer().getInventory());

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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("board")) {
            updateBoard((Board) evt.getNewValue());
        } else if (evt.getPropertyName().equals("hand")) {
            updateHand((List<Letter>) evt.getNewValue());
        }
    }
}
