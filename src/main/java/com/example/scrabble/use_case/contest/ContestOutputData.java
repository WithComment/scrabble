package com.example.scrabble.use_case.contest;

import java.util.List;

public class ContestOutputData {
    private final List<String> invalidWords;
    private final boolean goToNextTurn;

    public ContestOutputData(List<String> invalidWords, boolean goToNextTurn) {
        this.invalidWords = invalidWords;
        this.goToNextTurn = goToNextTurn;
    }

    public List<String> getInvalidWords() {
        return this.invalidWords;
    }

    public boolean getGoToNextTurn() {
        return this.goToNextTurn;
    }
}