package entity;

public class Board {
    private Tile[][] board;
    private int height;
    private int width;

    private static Tile[][] getBlankBoard() {
        Tile[][] board = new Tile[15][15];
        addToBoardSymmetrically(0, 0, 3, 0, board);
        addToBoardSymmetrically(0, 7, 3, 0, board);

        addToBoardSymmetrically(1, 1, 2, 0, board);
        addToBoardSymmetrically(2, 2, 2, 0, board);
        addToBoardSymmetrically(3, 3, 2, 0, board);
        addToBoardSymmetrically(4, 4, 2, 0, board);

        addToBoardSymmetrically(0, 3, 0, 2, board);
        addToBoardSymmetrically(2, 6, 0, 2, board);
        addToBoardSymmetrically(3, 7, 0, 2, board);
        addToBoardSymmetrically(6, 6, 0, 2, board);

        addToBoardSymmetrically(2, 5, 0, 3, board);
        addToBoardSymmetrically(5, 5, 0, 3, board);

        board[7][7] = new Tile(2, 0, null);
        return board;
    }

    private static void addToBoardSymmetrically(int x, int y, int wordMult, int letterMult, Tile[][] board) {
        board[x][y] = new Tile(wordMult, letterMult, null);
        board[x][15 - y] = new Tile(wordMult, letterMult, null);
        board[15 - x][y] = new Tile(wordMult, letterMult, null);
        board[15 - x][15 - y] = new Tile(wordMult, letterMult, null);
        board[y][x] = new Tile(wordMult, letterMult, null);
        board[y][15 - x] = new Tile(wordMult, letterMult, null);
        board[15 - y][x] = new Tile(wordMult, letterMult, null);
        board[15 - y][15 - x] = new Tile(wordMult, letterMult, null);
    }

    public Board() {
        height = width = 15;
        board = getBlankBoard();
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
