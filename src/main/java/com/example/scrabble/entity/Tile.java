package com.example.scrabble.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.json.JSONObject;

import java.io.*;

/**
 * Represents a cell on the board.
 * A tile has a word multiplier, a letter multiplier, a letter, and a boolean indicating whether the tile is confirmed.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tile implements Serializable {
    private int wordMult;
    private int letterMult;
    private Letter letter;
    private boolean isConfirmed;

    public Tile(){
    }

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
  
    public Tile(JSONObject json) {
        this.parseJSON(json);
    }

    private void parseJSON(JSONObject json) {
        this.wordMult = json.getInt("wordMult");
        this.letterMult = json.getInt("letterMult");
        this.letter = json.has("letter") ? new Letter(json.getJSONObject("letter")) : null;
        this.isConfirmed = json.getBoolean("isConfirmed");
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return (
            wordMult == tile.wordMult 
            && letterMult == tile.letterMult 
            && isConfirmed == tile.isConfirmed 
            && letter.equals(tile.letter)
        );
    }

    @Serial
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeChars(new JSONObject(this).toString());
    }

    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        JSONObject json = new JSONObject(in.readUTF());
        this.parseJSON(json);
    }
}
