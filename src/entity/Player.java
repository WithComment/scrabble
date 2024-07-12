package entity;

import java.util.ArrayList;

/**
 * Represents a player in the game.
 * A player has an ID, an inventory of letters, a score, and an unstable score.
 */
public class Player {
    private final int id;
    private ArrayList<Letter> inventory;
    private int score;
    private int unstableScore;

    /**
     * Constructs a Player with a specified ID.
     *
     * @param id the ID of the player
     */
    public Player(int id) {
        this.id = id;
        this.inventory = new ArrayList<>();
        this.score = 0;
        this.unstableScore = 0;
    }

    /**
     * Removes specified letters from the player's inventory.
     *
     * @param tiles an Iterable of letters to be removed
     */
    public void removeLetter(Iterable<Letter> tiles) {
        for (Letter letter : tiles) {
            this.inventory.remove(letter);
        }
    }

    /**
     * Removes a specified letter from the player's inventory.
     *
     * @param letter the letter to be removed
     */
    public void removeLetter(Letter letter) {
        this.inventory.remove(letter);
    }

    /**
     * Adds a list of letters to the player's inventory.
     *
     * @param tiles an ArrayList of letters to be added
     */
    public void addLetter(ArrayList<Letter> tiles) {
        this.inventory.addAll(tiles);
    }

    /**
     * Updates the player's score by adding the scores of words played.
     * The scores are added to both the main score and the unstable score.
     *
     * @param ScoresOfWords an ArrayList of scores to be added to the player's score
     */
    public void updateScore(ArrayList<Integer> ScoresOfWords) {
        // Add scores to player
        for (Integer score : ScoresOfWords) {
            this.score += score;
            unstableScore += score;
        }
    }

    /**
     * Resets the player's unstable score to 0, indicating the scores were not contested.
     */
    public void NotContested() {
        this.unstableScore = 0;
    }

    /**
     * Adjusts the player's score by subtracting the unstable score and then resets the unstable score to 0.
     * This indicates the scores were contested and invalidated.
     */
    public void BeContested() {
        this.score = this.score - this.unstableScore;
        this.unstableScore = 0;
    }

    /**
     * Returns the player's ID.
     *
     * @return the player's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the player's inventory of letters.
     *
     * @return the player's inventory of letters
     */
    public ArrayList<Letter> getInventory() {
        return inventory;
    }

    /**
     * Returns the player's score.
     *
     * @return the player's score
     */
    public int getScore() {
        return score;
    }
}


