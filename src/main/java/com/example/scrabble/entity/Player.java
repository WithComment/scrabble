package com.example.scrabble.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the game.
 * A player has an ID, an inventory of letters, a score, and an unstable score.
 */
public class Player implements Serializable, Comparable<Player> {
    private int id;
    private String name;
    private List<Letter> inventory;
    private int score;
    public int tempScore;

    /**
     * Constructs a Player with a specified ID.
     *
     * @param id the ID of the player
     */
    public Player(int id) {
        this.id = id;
        this.name = "Player " + id;
        this.inventory = new ArrayList<>();
        this.score = 0;
        this.tempScore = 0;
    }

    /**
     * Constructs a Player with a specified name and ID.
     *
     * @param name the name of the player
     * @param id the ID of the player
     */
    public Player(String name, int id) {
        this.name = name;
        this.id = id;
        this.inventory = new ArrayList<>();
        this.score = 0;
        this.tempScore = 0;
    }

    /**
     * Constructs a Player with specified properties.
     *
     * @param id the ID of the player
     * @param name the name of the player
     * @param inventory the inventory of letters
     * @param score the score of the player
     * @param tempScore the unstable score of the player
     */
    @JsonCreator
    public Player(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("inventory") List<Letter> inventory, @JsonProperty("score") int score, @JsonProperty("tempScore") int tempScore) {
        this.id = id;
        this.name = name;
        this.inventory = inventory;
        this.score = score;
        this.tempScore = tempScore;
    }

    /**
     * Constructs a Player from a JSON object.
     *
     * @param json the JSON object containing player data
     */
    public Player(JSONObject json){
        this.parseJSON(json);
    }

    /**
     * Parses the JSON object to set the player's properties.
     *
     * @param json the JSON object containing player data
     */
    private void parseJSON(JSONObject json) {
        this.name = json.getString("name");
        this.id = json.getInt("id");
        this.inventory = new ArrayList<>();
        this.score = 0;
        this.tempScore = 0;
    }

    /**
     * Serializes this Player object.
     *
     * @param out the output stream to which the object is to be written
     * @throws IOException if an I/O error occurs while writing the object
     */
    @Serial
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        JSONObject jsonObject = new JSONObject(this);
        out.writeChars(jsonObject.toString());
    }

    /**
     * Deserializes a Player object from the specified input stream.
     *
     * @param in the input stream from which the object is to be read
     * @throws IOException if an I/O error occurs while reading the object
     * @throws ClassNotFoundException if the class of a serialized object cannot be found
     */
    @Serial
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        JSONObject json = new JSONObject(in.readUTF());
        this.parseJSON(json);
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
     * Adds a specified score to the player's total score.
     *
     * @param score the score to be added
     */
    public void addScore(int score) {
        this.score += score;
    }

    /**
     * Removes a specified letter from the player's inventory based on the character.
     *
     * @param letter the character of the letter to be removed
     * @return the removed letter, or null if not found
     */
    public Letter removeLetter(char letter) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getLetter() == letter) {
                return inventory.remove(i);
            }
        }
        return null;
    }

    /**
     * Adds a letter to the player's inventory.
     *
     * @param letter the letter to be added
     */
    public void addLetter(Letter letter) {
        this.inventory.add(letter);
    }

    /**
     * Adds a list of letters to the player's inventory.
     *
     * @param tiles the list of letters to be added
     */
    public void addLetter(List<Letter> tiles) {
        this.inventory.addAll(tiles);
    }

    /**
     * Sets the player's unstable score.
     *
     * @param score the unstable score to be set
     */
    public void setTempScore(int score) {
        this.tempScore = score;
    }

    /**
     * Confirms the player's unstable score by adding it to the main score and resetting the unstable score to 0.
     */
    public void confirmTempScore() {
        this.score += this.tempScore;
        this.tempScore = 0;
    }

    /**
     * Resets the player's unstable score to 0.
     */
    public void resetTempScore() {
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

    /**
     * Returns the player's unstable score.
     *
     * @return the player's unstable score
     */
    public int getTempScore() {
        return tempScore;
    }

    /**
     * Returns the player's username.
     *
     * @return the player's username
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the hash code for this player.
     *
     * @return the hash code for this player
     */
    @Override
    public int hashCode() {
        return id;
    }

    /**
     * Compares this Player with another based on their scores.
     *
     * @param o the other Player to compare to
     * @return a negative integer, zero, or a positive integer as this object
     *         is less than, equal to, or greater than the specified object
     */
    public int compareTo(Player o) {
        return this.score - o.getScore();
    }

    /**
     * Erases the player's unstable score.
     */
    public void eraseTempScore() {
    }

    /**
     * Compares this Player to another object for equality.
     *
     * @param obj the object to compare to
     * @return true if the players are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj){return true;}

        if (obj == null || getClass() != obj.getClass()){return false;}

        Player other = (Player) obj;

        return  (id == other.id) &&
                (name.equals(other.name)) &&
                (inventory.equals(other.inventory)) &&
                (score == other.score) &&
                (tempScore == other.tempScore);
    }
}