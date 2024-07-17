package view;

import entity.Tile;
import view.panels.BoardPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class View extends JPanel implements MouseListener, ActionListener {
    private JFrame window;
    private BoardPanel boardPanel;

    public View() {
        window = new JFrame("Scrabble");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(1000, 1000);
        window.setVisible(true);
        window.addMouseListener(this);
        boardPanel = new BoardPanel(new GridLayout(15, 15));
        window.add(boardPanel);
        boardPanel.updateUI();
    }

    public void actionPerformed(ActionEvent e) {

    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseClicked(MouseEvent e) {
        Point location = e.getLocationOnScreen();
        int x = location.x;
        int y = location.y;
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