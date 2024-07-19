package view.panels;

import entity.Board;
import entity.Letter;
import entity.Tile;
import view.buttons.BoardButton;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardPanel extends JPanel {
    private int width;
    private int height;
    private Board board;
    BoardButton[][] buttons;

    public BoardPanel() {
        setLayout(new GridLayout(15, 15));
        Board emptyBoard = new Board();
        setPreferredSize(new Dimension(800, 800));
        this.width = emptyBoard.getWidth();
        this.height = emptyBoard.getHeight();
        buttons = new BoardButton[this.width][this.height];
        for (int y=0; y < height; y++){
            for (int x=0; x < width; x++){
                BoardButton tileButton = new BoardButton(new int[]{x, y});
                Tile tile = emptyBoard.getCell(x, y);
                setButtonToMatchTile(tileButton, tile);
                tileButton.addActionListener(
                        new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                System.out.println(((BoardButton) e.getSource()).getCoords()[0]); //TODO: add onClick function that alerts InputManager when board elt pressed
                            }
                        }
                );
                this.add(tileButton);
                int[] coords = new int[]{x, y};
                buttons[y][x] = tileButton;
            }
        }
    }

    public void setBoardToState(Board board){
        for (int y=0; y < board.getHeight(); y++){
            for (int x=0; x < board.getWidth(); x++){
                Tile tile = board.getCell(x, y);
                int[] coords = new int[]{x, y};
                Letter letter = tile.getLetter();
                BoardButton tileButton =  buttons[coords[0]][coords[1]];
                setButtonToMatchTile(tileButton, tile);
            }
        }
    }

    private void setButtonToMatchTile(JButton tileButton, Tile tile){
        Letter letter = tile.getLetter();
        int wordMult = tile.getWordMult();
        int letterMult = tile.getLetterMult();
        if (letter != null){
            tileButton.setText(String.valueOf(letter.getLetter()));
        } else{
            tileButton.setText("_");
        }
        tileButton.setBorder(new LineBorder(Color.black));
        if (wordMult == 2){
            tileButton.setBackground(Color.ORANGE);
        } else if (wordMult == 3){
            tileButton.setBackground(Color.RED);
        }
        if (letterMult == 2){
            tileButton.setBackground(Color.CYAN);
        } else if (letterMult == 3){
            tileButton.setBackground(Color.BLUE);
        }
        tileButton.setOpaque(true);
    }

    public void setGridSpotToTile(int[] coords, Tile tile){
        JButton tileButton = buttons[coords[0]][coords[1]];
        System.out.println("Adding tile");
        setButtonToMatchTile(tileButton, tile);
    }
}
