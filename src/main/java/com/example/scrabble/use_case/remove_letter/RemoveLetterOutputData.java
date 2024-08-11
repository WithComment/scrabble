package com.example.scrabble.use_case.remove_letter;

import com.example.scrabble.entity.Board;
import com.example.scrabble.entity.Letter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the output data for removing a letter.
 * Contains information about whether the removal was successful,
 * the updated board state, and the player's hand.
 */
public class RemoveLetterOutputData {
    private final boolean removeSuccessful;
    private final Board board;
    private final List<Letter> hand;

    /**
     * Constructs a RemoveLetterOutputData instance with the specified parameters.
     *
     * @param removeSuccessful a boolean indicating whether the letter removal was successful
     * @param board the updated board state after the letter removal
     * @param hand the updated list of letters in the player's hand
     */
    public RemoveLetterOutputData(boolean removeSuccessful, Board board, List<Letter> hand){
        this.removeSuccessful = removeSuccessful;
        this.board = board;
        this.hand = hand;
    }

    /**
     * Returns whether the letter removal was successful.
     *
     * @return true if the letter removal was successful, false otherwise
     */
    public boolean isRemoveSuccessful(){
        return removeSuccessful;
    }

    /**
     * Returns the updated board state.
     *
     * @return the updated board state
     */
    public Board getBoard(){
        return board;
    }

    /**
     * Returns the updated list of letters in the player's hand.
     *
     * @return the updated list of letters in the player's hand
     */
    public List<Letter> getHand(){
        return hand;
    }

    /**
     * Returns a list of characters representing the letters in the player's hand.
     *
     * @return a list of characters representing the letters in the player's hand
     */
    public List<Character> getHandCharacters() {
        List<Character> handChars = new ArrayList<>();
        for (Letter letter : hand) {
            handChars.add(letter.getLetter());
        }
        return handChars;
    }
}