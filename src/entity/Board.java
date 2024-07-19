package entity;

public class Board {
    private final Tile[][] board;
    private final int height;
    private final int width;

    /**
     * Constructs a new Board with an initial state.
     */
    public Board() {
        height = 15;
        width = 15;
        board = getBlankBoard();
    }

    /**
     * Constructs a new Board with a specified state.
     *
     * @return a 2D array of Tiles representing the board
     */
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

    /**
     * Adds a tile to the board and its 7 other symmetric positions.
     *
     * @param x
     * @param y
     * @param wordMult
     * @param letterMult
     * @param board
     */
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

    /**
     * Returns the height of the board.
     *
     * @return the height of the board
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Returns the width of the board.
     *
     * @return the width of the board
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Sets the tile at the specified position on the board.
     *
     * @param x    the x-coordinate of the tile
     * @param y    the y-coordinate of the tile
     * @param tile the tile to set
     */
    public void setCell(int x, int y, Tile tile) {
        this.board[x][y] = tile;
    }

    /**
     * Sets the letter at the specified position on the board.
     *
     * @param x
     * @param y
     * @param letter
     * @return
     */
    public Tile setCell(int x, int y, Letter letter) {
        return this.board[x][y].setLetter(letter);
    }

    /**
     * Returns the tile at the specified position on the board.
     *
     * @param x the x-coordinate of the tile
     * @param y the y-coordinate of the tile
     * @return the tile at the specified position
     */
    public Tile getCell(int x, int y) {
        return this.board[x][y];
    }

    /**
     * Returns the letter at the specified position on the board.
     *
     * @param x
     * @param y
     * @return
     */
    public boolean confirm(int x, int y) {
        if (this.board[x][y].isEmpty()) {
            return false;
        }
        this.board[x][y].confirm();
        return true;
    }

    /**
     * Removes the letter at the specified position on the board.
     *
     * @param x
     * @param y
     */
    public void setAndConfirm(int x, int y, Letter letter) {
        this.board[x][y].setAndConfirm(letter);
    }

    /**
     * Removes the letter at the specified position on the board.
     *
     * @param x
     * @param y
     */
    public boolean isConfirmed(int x, int y) {
        return this.board[x][y].isConfirmed();
    }
}
