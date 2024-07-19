package view.panels;

import entity.Board;
import entity.Letter;
import entity.Tile;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class BoardPanel extends JPanel {
    private int width;
    private int height;
    private Board board;
    JLabel[][] labels;

    public BoardPanel() {
        setLayout(new GridLayout(15, 15));
        Board emptyBoard = new Board();
        setPreferredSize(new Dimension(800, 800));
        this.width = emptyBoard.getWidth();
        this.height = emptyBoard.getHeight();
        labels = new JLabel[this.width][this.height];
        for (int y=0; y < height; y++){
            for (int x=0; x < width; x++){
                JLabel tileLabel = new JLabel("", SwingConstants.CENTER);
                Tile tile = emptyBoard.getCell(x, y);
                setLabelToMatchTile(tileLabel, tile);
                this.add(tileLabel);
                int[] coords = new int[]{x, y};
                labels[y][x] = tileLabel;
            }
        }
    }

    public void setBoardToState(Board board){
        for (int y=0; y < board.getHeight(); y++){
            for (int x=0; x < board.getWidth(); x++){
                Tile tile = board.getCell(x, y);
                int[] coords = new int[]{x, y};
                Letter letter = tile.getLetter();
                JLabel tileLabel =  labels[coords[0]][coords[1]];
                setLabelToMatchTile(tileLabel, tile);
            }
        }
    }

    private void setLabelToMatchTile(JLabel tileLabel, Tile tile){
        Letter letter = tile.getLetter();
        int wordMult = tile.getWordMult();
        int letterMult = tile.getLetterMult();
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
    }

    public void setGridSpotToTile(int[] coords, Tile tile){
        JLabel tileLabel = labels[coords[0]][coords[1]];
        System.out.println("Adding tile");
        setLabelToMatchTile(tileLabel, tile);
    }
}
