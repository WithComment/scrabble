package entity;

public class Board {
    private Tile[][] board;
    private int height;
    private int width;

    public Board(int height, int width) {
        this.height = height;
        this.width = width;
        this.board = new Tile[height][width];
    }

    public int getHeight() {
        return this.height;
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
