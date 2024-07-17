package view;

import entity.Board;
import entity.Letter;
import entity.Tile;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class View extends JPanel implements MouseListener, ActionListener {
    private JFrame window;
    private Board board;

    public View(Board board) {
        window = new JFrame("Scrabble");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(1000, 1000);
        window.setVisible(true);
        window.addMouseListener(this);
        this.board = board;
        JPanel boardPanel = new JPanel(new GridLayout(15, 15));
        int height = board.getHeight();
        int width = board.getWidth();
        for (int y=0; y < height; y++){
            for (int x=0; x < width; x++){
                JLabel tileLabel = new JLabel("", SwingConstants.CENTER);
                Tile tile = board.getCell(x, y);
                int wordMult = tile.getWordMult();
                int letterMult = tile.getLetterMult();
                Letter letter = tile.getLetter();
                if (letter != null){
                    tileLabel.setText(String.valueOf(letter.getLetter()));
                } else{
                    tileLabel.setText("_");
                }
                tileLabel.setBorder(new LineBorder(Color.black));
                if (wordMult == 2){
                    tileLabel.setBackground(Color.ORANGE);
                } else if (wordMult == 3){
                    tileLabel.setBackground(Color.RED);
                }
                if (letterMult == 2){
                    tileLabel.setBackground(Color.CYAN);
                } else if (letterMult == 3){
                    tileLabel.setBackground(Color.BLUE);
                }
                tileLabel.setOpaque(true);
                boardPanel.add(tileLabel);
            }
        }
        window.add(boardPanel);
        boardPanel.updateUI();
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
}
