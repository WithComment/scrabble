package com.example.scrabble.use_case.end_game;

import java.util.List;

/**
 * Represents the output data for ending a game.
 * Contains the list of winner IDs.
 */
public class EndGameOutputData {
    private final List<Integer> winners;

    /**
     * Constructs an EndGameOutputData instance with the specified list of winner IDs.
     *
     * @param winners the list of winner IDs
     */
    public EndGameOutputData(List<Integer> winners) {
        this.winners = winners;
    }

    /**
     * Returns the list of winner IDs.
     *
     * @return the list of winner IDs
     */
    public List<Integer> getWinners() {
        return winners;
    }
}