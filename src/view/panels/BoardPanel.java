package view.panels;

import entity.Board;
import entity.Letter;
import entity.Tile;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {
    private int width;
    private int height;
    private Board board;
    public BoardPanel(LayoutManager layout, Board board) {
        super(layout);
        this.board = board;
        this.width = board.getWidth();
        this.height = board.getHeight();
        System.out.println("Constructed");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int y=0; y < height; y++){
            for (int x=0; x < width; x++){
                Tile tile = board.getCell(x, y);
                int wordMult = tile.getWordMult();
                int letterMult = tile.getLetterMult();
                g.setColor(Color.WHITE);
                if (wordMult == 2){
                    g.setColor(Color.ORANGE);
                } else if (wordMult == 3){
                    g.setColor(Color.RED);
                }
                if (letterMult == 2){
                    g.setColor(Color.CYAN);
                } else if (letterMult == 3){
                    g.setColor(Color.BLUE);
                }
                g.fillRect(x * 50, y * 50, 50,50);
                g.setColor(Color.BLACK);
                g.drawRect(x * 50, y * 50, 50,50);
                Letter letter = tile.getLetter();
                if (letter != null){
                    JButton letterLabel = new JButton(letter.toString());
                    this.add(letterLabel);
                }
            }
        }
    }
}
