package app;

import entity.Board;
import entity.Letter;
import entity.Tile;
import view.View;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        board.setCell(0,0, new Tile(1, 2, new Letter('A', 3)));
        View view = new View(board);
    }
}
