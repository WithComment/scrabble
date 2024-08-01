package com.example.scrabble.use_case.confirm_play;

public class ConfirmPlayOutputData {
    private final int tempScore;

    public ConfirmPlayOutputData(int tempScore) {
        this.tempScore = tempScore;
    }

    public int getTempScore() {
        return tempScore;
    }
}