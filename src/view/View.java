package view;

import entity.Tile;
import input_manager.InputManager;
import view.panels.BoardPanel;
import view.panels.HandPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;


public class View extends JPanel implements MouseListener, ActionListener {
    private JFrame window;
    private BoardPanel boardPanel;
    private HandPanel handPanel;

    public View(InputManager inputManager) {
        window = new JFrame("Scrabble");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(1000, 1000);
        window.setVisible(true);
        window.addMouseListener(this);
        boardPanel = new BoardPanel(inputManager);
        handPanel = new HandPanel(inputManager);
        ArrayList<String> tempHand = new ArrayList<>();
        tempHand.add("A");
        tempHand.add("B");
        handPanel.setHand(tempHand);
        JPanel view = new JPanel();
        view.setLayout(new FlowLayout());
        view.add(boardPanel);
        view.add(handPanel);
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
}
