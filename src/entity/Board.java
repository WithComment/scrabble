package entity;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<List<Tile>> board;
    private int length;
    private int width;

    public Board(int length, int width) {
        this.length = length;
        this.width = width;
        this.board = new ArrayList<List<Tile>>();
        for (int i = 0; i < length; i++) {
            List<Tile> row = new ArrayList<Tile>();
            for (int j = 0; j < length; j++) {
                row.add(new Tile());
            }
            this.board.add(row);
        }
    }

    public int getLength() {
        return this.length;
    }

    public int getWidth() {
        return this.width;
    }

    public void setCell(int x, int y, Tile tile) {
        this.board.get(x).set(y, tile);
    }

    public Tile getCell(int x, int y) {
        return this.board.get(x).get(y);
    }
}
