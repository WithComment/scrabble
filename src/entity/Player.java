package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the game.
 * A player has an ID, an inventory of letters, a score, and an unstable score.
 */
public class    Player {
    private static int idCounter = 0;
    private final int id;
    private List<Letter> inventory;
    private int score;
    public int tempScore;

    /**
     * Constructs a Player with a specified ID.
     *
     * @param id the ID of the player
     */
    public Player(int id) {
        this.id = idCounter++;
        this.inventory = new ArrayList<>();
        this.score = 0;
        this.tempScore = 0;
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

    public void addLetter(Letter letter) {
        this.inventory.add(letter);
    }

    public void addLetter(List<Letter> tiles) {
        this.inventory.addAll(tiles);
    }

    /**
     * Updates the player's score by adding the scores of words played.
     * The scores are added to both the main score and the unstable score.
     *
     * @param ScoresOfWords an List of scores to be added to the player's score
     */
    public void updateScore(List<Integer> ScoresOfWords) {
        // Add scores to player
        for (Integer score : ScoresOfWords) {
            this.score += score;
            tempScore += score;
        }
    }

    public void addScore(int score) {
        this.score += score;
        tempScore += score;
    }

    public void confirmTempScore() {
        this.score += this.tempScore;
        this.tempScore = 0;
    }

    public void eraseTempScore() {
        this.tempScore = 0;
    }

    /**
     * Resets the player's unstable score to 0, indicating the scores were not contested.
     */
    public void NotContested() {
        this.tempScore = 0;
    }

    /**
     * Adjusts the player's score by subtracting the unstable score and then resets the unstable score to 0.
     * This indicates the scores were contested and invalidated.
     */
    public void BeContested() {
        this.score = this.score - this.tempScore;
        this.tempScore = 0;
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
    public List<Letter> getInventory() {
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

    public String getUsername() {
        // TODO: Add username to player.
        return "Player " + id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}


