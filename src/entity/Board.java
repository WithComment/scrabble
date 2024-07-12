package entity;

public class Board {
    private final Tile[][] board;
    private final int height;
    private final int width;

    public Board() {
        height = 15;
        width = 15;
        board = getBlankBoard();
    }

    private static Tile[][] getBlankBoard() {
        Tile[][] board = new Tile[15][15];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                board[i][j] = new Tile(0, 0, null);
            }
        }
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
        board[x][14 - y] = new Tile(wordMult, letterMult, null);
        board[14 - x][y] = new Tile(wordMult, letterMult, null);
        board[14 - x][14 - y] = new Tile(wordMult, letterMult, null);
        board[y][x] = new Tile(wordMult, letterMult, null);
        board[y][14 - x] = new Tile(wordMult, letterMult, null);
        board[14 - y][x] = new Tile(wordMult, letterMult, null);
        board[14 - y][14 - x] = new Tile(wordMult, letterMult, null);
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
