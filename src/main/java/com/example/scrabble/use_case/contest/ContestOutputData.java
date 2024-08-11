package com.example.scrabble.use_case.contest;

import java.util.List;

/**
 * Represents the output data for a contest.
 * Contains a list of invalid words and a boolean indicating whether to proceed to the next turn.
 */
public class ContestOutputData {
    private final List<String> invalidWords;
    private final boolean goToNextTurn;

    /**
     * Constructs a ContestOutputData instance with the specified invalid words and next turn status.
     *
     * @param invalidWords the list of invalid words
     * @param goToNextTurn true if the game should proceed to the next turn, false otherwise
     */
    public ContestOutputData(List<String> invalidWords, boolean goToNextTurn) {
        this.invalidWords = invalidWords;
        this.goToNextTurn = goToNextTurn;
    }

    /**
     * Returns the list of invalid words.
     *
     * @return the list of invalid words
     */
    public List<String> getInvalidWords() {
        return this.invalidWords;
    }

    /**
     * Returns whether the game should proceed to the next turn.
     *
     * @return true if the game should proceed to the next turn, false otherwise
     */
    public boolean getGoToNextTurn() {
        return this.goToNextTurn;
    }
}