package com.example.scrabble.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.*;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Represents the Scrabble board.
 * Implements Serializable and Iterable\<Tile\>.
 */
public class Board implements Serializable, Iterable<Tile> {
    private final Tile[][] board;
    private final int height = 15;
    private final int width = 15;

    /**
     * Constructs a new Board with a blank state.
     */
    public Board() {
        board = getBlankBoard();
    }

    /**
     * Constructs a new Board with a specified state.
     *
     * @param board the 2D array of Tiles representing the board
     */
    @JsonCreator
    public Board(@JsonProperty("board") Tile[][] board) {
        this.board = board;
    }

    /**
     * Returns a blank Scrabble board with predefined multipliers.
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
        addToBoardSymmetrically(1, 5, 1, 3, board);
        addToBoardSymmetrically(5, 5, 1, 3, board);

        board[7][7] = new Tile(2, 1, null);
        return board;
    }

    /**
     * Adds a tile to the board and its 7 other symmetric positions.
     *
     * @param x the x-coordinate of the tile
     * @param y the y-coordinate of the tile
     * @param wordMult the word multiplier of the tile
     * @param letterMult the letter multiplier of the tile
     * @param board the board to add the tile to
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
     * Returns the 2D array of Tiles representing the board.
     *
     * @return the 2D array of Tiles
     */
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
     * Sets the letter at the specified position on the board.
     *
     * @param x the x-coordinate of the letter
     * @param y the y-coordinate of the letter
     * @param letter the letter to set
     */
    public void setCell(int x, int y, Letter letter) {
        this.board[y][x].setLetter(letter);
    }

    /**
     * Returns the tile at the specified position on the board.
     *
     * @param x the x-coordinate of the tile
     * @param y the y-coordinate of the tile
     * @return the tile at the specified position
     */
    public Tile getTile(int x, int y) {
        return this.board[y][x];
    }

    /**
     * Sets the state of the tile at the specified position to confirmed.
     *
     * @param x the x-coordinate of the tile
     * @param y the y-coordinate of the tile
     */
    public void confirm(int x, int y) {
        this.board[y][x].confirm();
    }

    /**
     * Returns whether the tile at a specified position on the board is confirmed.
     *
     * @param x the x-coordinate of the tile
     * @param y the y-coordinate of the tile
     * @return true if the tile is confirmed, false otherwise
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

    /**
     * Compares this board to another object for equality.
     *
     * @param other the object to compare to
     * @return true if the boards are equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Board otherBoard) {
            if (this.height != otherBoard.getHeight() || this.width != otherBoard.getWidth()) {
                return false;
            }
            Iterator<Tile> iterator1 = this.iterator();
            Iterator<Tile> iterator2 = otherBoard.iterator();
            while (iterator1.hasNext() && iterator2.hasNext()) {
                if (!Objects.equals(iterator1.next(), iterator2.next())) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns an iterator over the tiles in the board.
     *
     * @return an iterator over the tiles
     */
    @Override
    public Iterator<Tile> iterator() {
        return new BoardIterator(this);
    }
}

/**
 * Iterator for the Board class.
 */
class BoardIterator implements Iterator<Tile> {
    private final int h, w;
    private int count;
    private final Tile[][] board;

    /**
     * Constructs a BoardIterator for the specified board.
     *
     * @param board the board to iterate over
     */
    public BoardIterator(Board board) {
        this.count = 0;
        this.h = board.getHeight();
        this.w = board.getWidth();
        this.board = board.getBoard();
    }

    /**
     * Returns whether there are more tiles to iterate over.
     *
     * @return true if there are more tiles, false otherwise
     */
    @Override
    public boolean hasNext() {
        return count < h * w;
    }

    /**
     * Returns the next tile in the iteration.
     *
     * @return the next tile
     * @throws NoSuchElementException if there are no more tiles
     */
    @Override
    public Tile next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        Tile tile = board[count / w][count % w];
        count++;
        return tile;
    }
}