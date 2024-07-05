package entity;

public class Board {
    private Tile[][] board;
    private int length;
    private int width;

    public Board(int length, int width) {
        this.length = length;
        this.width = width;
        this.board = new Tile[length][width];
    }

    public int getLength() {
        return this.length;
    }

    public int getWidth() {
        return this.width;
    }

    public void setCell(int x, int y, Tile tile) {
        this.board[x][y] = tile;
    }

    public Tile getCell(int x, int y) {
        return this.board[x][y];
    }
}
