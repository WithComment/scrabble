package entity;

/**
 * Represents a cell on the board.
 *
 * @param wordMult    The word multiplier.
 * @param letterMult  The letter multiplier.
 * @param letter      The letter on the tile, null if empty.
 * @param isConfirmed Whether the letter on the tile has been confirmed.
 */
public class Tile {
    private final int wordMult;
    private final int letterMult;
    private Letter letter;
    private boolean isConfirmed;

    public Tile(int wordMult, int letterMult, Letter letter) {
        this.wordMult = wordMult;
        this.letterMult = letterMult;
        this.letter = letter;
        this.isConfirmed = false;
    }

    public int getWordMult() {
        return wordMult;
    }

    public int getLetterMult() {
        return letterMult;
    }

    public Letter getLetter() {
        return letter;
    }

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


    public void setAndConfirm(Letter letter) {
        this.letter = letter;
        isConfirmed = true;
    }

    public boolean isEmpty() {
        return letter == null;
    }

    public boolean isConfirmed() {
        return letter != null && isConfirmed;
    }

    public String toString() {
        return letter == null ? " " : letter.toString();
    }
}
