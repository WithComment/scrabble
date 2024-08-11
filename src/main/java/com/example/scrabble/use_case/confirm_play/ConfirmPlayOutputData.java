package com.example.scrabble.use_case.confirm_play;

/**
 * Represents the output data for confirming a play.
 * Contains a boolean indicating whether the play is valid.
 */
public class ConfirmPlayOutputData {
    private final boolean isValid;

    /**
     * Constructs a ConfirmPlayOutputData instance with the specified validity.
     *
     * @param isValid true if the play is valid, false otherwise
     */
    public ConfirmPlayOutputData(boolean isValid) {
        this.isValid = isValid;
    }

    /**
     * Returns whether the play is valid.
     *
     * @return true if the play is valid, false otherwise
     */
    public boolean isValid() {
        return isValid;
    }
}