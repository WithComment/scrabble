package com.example.scrabble.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.*;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class Board implements Serializable, Iterable<Tile> {
    private Tile[][] board;
    private int height = 15;
    private int width = 15;

    public Board() {
        board = getBlankBoard();
    }

    @JsonCreator
    public Board(@JsonProperty("board") Tile[][] board) {
        this.board = board;
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
        addToBoardSymmetrically(1, 5, 1, 3, board);
        addToBoardSymmetrically(5, 5, 1, 3, board);

        board[7][7] = new Tile(2, 1, null);
        return board;
    }

    /**
     * Adds a tile to the board and its 7 other symmetric positions.
     *
     * @param x        the x-coordinate of the tile
     * @param y       the y-coordinate of the tile
     * @param wordMult the word multiplier of the tile
     * @param letterMult the letter multiplier of the tile
     * @param board   the board to add the tile to
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

    public int getHeight() {
        return this.height;
    }

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

    public Tile getTile(int x, int y) {
        return this.board[y][x];
    }

    /**
     * Set the state of the tile at the specified position to confirmed.
     *
     * @param x the x-coordinate of the letter
     * @param y the y-coordinate of the letter
     */
    public void confirm(int x, int y) {
        this.board[y][x].confirm();
    }
    
    /**
     * Return whether the tile at a specified position on the board is confirmed.
     *
     * @param x the x-coordinate of the letter
     * @param y the y-coordinate of the letter
     */
    public boolean isConfirmed(int x, int y) {
        return this.board[y][x].isConfirmed();
    }

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

    

    @Override
    public Iterator<Tile> iterator() {
        return new BoardIterator(this);
    }
}

class BoardIterator implements Iterator<Tile> {
    private int h, w;
    private int count;
    private Tile[][] board;

    public BoardIterator(Board board) {
        this.count = 0;
        this.h = board.getHeight();
        this.w = board.getWidth();
        this.board = board.getBoard();
    }

    @Override
    public boolean hasNext() {
        return count < h * w;
    }

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