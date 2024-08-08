package com.example.scrabble.use_case.place_letter;

import java.util.List;

import com.example.scrabble.entity.Board;
import com.example.scrabble.entity.Letter;

/**
 * Represents the output data for placing a letter on the board.
 * Contains the updated board, the player's hand, the temporary score, and the words formed.
 */
public class PlaceLetterOutputData {
    private final Board board;
    private final List<Letter> hand;
    private final int tempScore;
    private final List<String> words;

    /**
     * Constructs a PlaceLetterOutputData instance with the specified parameters.
     *
     * @param board the updated game board
     * @param hand the player's current hand of letters
     * @param tempScore the temporary score after placing the letter
     * @param words the list of words formed by the move
     */
    public PlaceLetterOutputData(Board board, List<Letter> hand, int tempScore, List<String> words) {
        this.board = board;
        this.hand = hand;
        this.tempScore = tempScore;
        this.words = words;
    }

    /**
     * Returns the updated game board.
     *
     * @return the updated game board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Returns the player's current hand of letters.
     *
     * @return the player's current hand of letters
     */
    public List<Letter> getHand() {
        return hand;
    }

    /**
     * Returns the temporary score after placing the letter.
     *
     * @return the temporary score
     */
    public int getTempScore() {
      return tempScore;
    }

    /**
     * Returns the list of words formed by the move.
     *
     * @return the list of words formed by the move
     */
    public List<String> getWords() {
      return words;
    }
}