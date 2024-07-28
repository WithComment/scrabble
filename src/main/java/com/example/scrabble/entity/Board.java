package com.example.scrabble.entity;

import org.json.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Board implements Serializable {
    private Tile[][] board;
    private int height;
    private int width;


    /**
     * Constructs a new Board with an initial state.
     */
    public Board() {
        height = 15;
        width = 15;
        board = getBlankBoard();
    }

    public Board(JSONObject jsonObject) {
        this.height = jsonObject.getInt("height");
        this.width = jsonObject.getInt("width");
        this.board = new Tile[15][15];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                JSONObject tile = jsonObject.getJSONObject("board").getJSONObject(i + "," + j);
                this.board[i][j] = new Tile(jsonObject.getJSONObject("board").getJSONObject(i + "," + j));
            }
        }
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

    public Tile[][] getBoard() {
        return board;
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
        this.board[y][x] = tile;
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
        return this.board[y][x].setLetter(letter);
    }

    /**
     * Returns the tile at the specified position on the board.
     *
     * @param x the x-coordinate of the tile
     * @param y the y-coordinate of the tile
     * @return the tile at the specified position
     */
    public Tile getCell(int x, int y) {
        return this.board[y][x];
    }

    /**
     * Returns the letter at the specified position on the board.
     *
     * @param x
     * @param y
     * @return
     */
    public boolean confirm(int x, int y) {
        if (this.board[y][x].isEmpty()) {
            return false;
        }
        this.board[y][x].confirm();
        return true;
    }

    /**
     * Removes the letter at the specified position on the board.
     *
     * @param x
     * @param y
     */
    public void setAndConfirm(int x, int y, Letter letter) {
        this.board[y][x].setAndConfirm(letter);
    }

    /**
     * Removes the letter at the specified position on the board.
     *
     * @param x
     * @param y
     */
    public boolean isConfirmed(int x, int y) {
        return this.board[y][x].isConfirmed();
    }

    /**
     * Returns a string representation of the board.
     *
     * @return a string representation of the board
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                sb.append(board[i][j].toString());
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Board) {
            Board otherBoard = (Board) other;
            if (this.height != otherBoard.getHeight() || this.width != otherBoard.getWidth()) {
                return false;
            }
            for (int i = 0; i < 15; i++) {
                for (int j = 0; j < 15; j++) {
                    if (!board[i][j].equals(otherBoard.getCell(j, i))) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        JSONObject jsonObject = new JSONObject(this);
        out.writeChars(jsonObject.toString());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        String json = in.readLine();
        JSONObject jsonObject = new JSONObject(json);
        this.height = jsonObject.getInt("height");
        this.width = jsonObject.getInt("width");
        this.board = new Tile[15][15];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                this.board[i][j] = new Tile(jsonObject.getJSONObject("board").getJSONObject(i + "," + j));
            }
        }
    }
}
