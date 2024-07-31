package com.example.scrabble.use_case.end_game;


import java.util.List;

public class EndGameOutputData {
    private final List<Integer> winners;

    public EndGameOutputData(List<Integer> winners) {
        this.winners = winners;
    }

    public List<Integer> getWinners() {
        return winners;
    }
}
