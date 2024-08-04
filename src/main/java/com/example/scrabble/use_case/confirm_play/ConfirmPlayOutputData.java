package com.example.scrabble.use_case.confirm_play;

public class ConfirmPlayOutputData {
    private final boolean isValid;

    public ConfirmPlayOutputData(boolean isValid) {
        this.isValid = isValid;
    }

    public boolean isValid() {
        return isValid;
    }
}