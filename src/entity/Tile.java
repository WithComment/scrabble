package entity;

/**
 * Represents a cell on the board.
 * A tile has a word multiplier, a letter multiplier, a letter, and a boolean indicating whether the tile is confirmed.
 */
public class Tile {
    private final int wordMult;
    private final int letterMult;
    private Letter letter;
    private boolean isConfirmed;

    /**
     * Constructs a Tile with specified multipliers and letter.
     * @param wordMult The word multiplier for the tile.
     * @param letterMult The letter multiplier for the tile.
     * @param letter The letter placed on the tile.
     */
    public Tile(int wordMult, int letterMult, Letter letter) {
        this.wordMult = wordMult;
        this.letterMult = letterMult;
        this.letter = letter;
        this.isConfirmed = false;
    }

    /**
     * Gets the word multiplier of the tile.
     * @return The word multiplier.
     */
    public int getWordMult() {
        return wordMult;
    }

    /**
     * Gets the letter multiplier of the tile.
     * @return The letter multiplier.
     */
    public int getLetterMult() {
        return letterMult;
    }

    /**
     * Gets the letter placed on the tile.
     * @return The letter on the tile.
     */
    public Letter getLetter() {
        return letter;
    }

    /**
     * Sets the letter on the tile.
     * @param letter The letter to place on the tile.
     * @return The updated tile.
     */
    public Tile setLetter(Letter letter) {
        this.letter = letter;
        return this;
    }

    /**
     * Set the letter on the tile to null. Un-confirm the tile.
     */
    public void removeLetter() {
        letter = null;
        isConfirmed = false;
    }

    /**
     * Set the tile to confirmed.
     */
    public void confirm() {
        isConfirmed = true;
    }

    /**
     * Sets the letter on the tile and confirms the tile.
     * @param letter The letter to place on the tile.
     */
    public void setAndConfirm(Letter letter) {
        this.letter = letter;
        isConfirmed = true;
    }

    /**
     * Checks if the tile is empty.
     * @return True if the tile is empty, false otherwise.
     */
    public boolean isEmpty() {
        return letter == null;
    }

    /**
     * Checks if the tile is confirmed.
     * @return True if the tile is confirmed, false otherwise.
     */
    public boolean isConfirmed() {
        return letter != null && isConfirmed;
    }

    /**
     * Returns a string representation of the tile.
     * @return A string representation of the tile.
     */
    public String toString() {
        return letter == null ? " " : letter.toString();
    }
}
