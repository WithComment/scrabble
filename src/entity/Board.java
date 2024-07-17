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
                board[i][j] = new Tile(1, 1, null);
            }
        }
        addToBoardSymmetrically(0, 0, 3, 1, board);
        addToBoardSymmetrically(0, 7, 3, 1, board);
        addToBoardSymmetrically(1, 1, 2, 1, board);
        addToBoardSymmetrically(2, 2, 2, 1, board);
        addToBoardSymmetrically(3, 3, 2, 1, board);
        addToBoardSymmetrically(4, 4, 2, 1, board);

        addToBoardSymmetrically(0, 3, 1, 2, board);
        addToBoardSymmetrically(2, 6, 1, 2, board);
        addToBoardSymmetrically(3, 7, 1, 2, board);
        addToBoardSymmetrically(6, 6, 1, 2, board);
        addToBoardSymmetrically(2, 5, 1, 3, board);
        addToBoardSymmetrically(5, 5, 1, 3, board);

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

    public Tile setCell(int x, int y, Letter letter) {
        return this.board[x][y].setLetter(letter);
    }

    public Tile getCell(int x, int y) {
        return this.board[x][y];
    }

    public boolean confirm(int x, int y) {
        if (this.board[x][y].isEmpty()) {
            return false;
        }
        this.board[x][y].confirm();
        return true;
    }

    public void setAndConfirm(int x, int y, Letter letter) {
        this.board[x][y].setAndConfirm(letter);
    }

    public boolean isConfirmed(int x, int y) {
        return this.board[x][y].isConfirmed();
    }
}
