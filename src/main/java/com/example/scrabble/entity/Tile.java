package com.example.scrabble.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.json.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
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

    @JsonProperty("isConfirmed")
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
     * Constructs a Tile with specified multipliers, letter, and confirmation status.
     * @param wordMult The word multiplier for the tile.
     * @param letterMult The letter multiplier for the tile.
     * @param letter The letter placed on the tile.
     * @param isConfirmed Whether the tile is confirmed.
     */
    @JsonCreator
    public Tile(@JsonProperty("wordMult") int wordMult, @JsonProperty("letterMult") int letterMult, @JsonProperty("letter") Letter letter, @JsonProperty("isConfirmed") boolean isConfirmed) {
        this.wordMult = wordMult;
        this.letterMult = letterMult;
        this.letter = letter;
        this.isConfirmed = isConfirmed;
    }

    /**
     * Constructs a Tile from a JSON object.
     * @param json The JSON object containing tile data.
     */
    public Tile(JSONObject json) {
        this.parseJSON(json);
    }

    /**
     * Parses the JSON object to set the tile's properties.
     * @param json The JSON object containing tile data.
     */
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
    protected Tile setLetter(Letter letter) {
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
     * Sets the confirmation status of the tile.
     * @param confirmed The confirmation status to set.
     */
    @JsonProperty("isConfirmed")
    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    /**
     * Set the tile to confirmed.
     */
    protected void confirm() {
        this.isConfirmed = true;
    }

    /**
     * Checks if the tile is empty.
     * @return True if the tile is empty, false otherwise.
     */
    @JsonIgnore
    public boolean isEmpty() {
        return letter == null;
    }

    /**
     * Checks if the tile is confirmed.
     * @return True if the tile is confirmed, false otherwise.
     */
    @JsonProperty("isConfirmed")
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

    /**
     * Serializes this Tile object.
     * @param out The output stream to which the object is to be written.
     * @throws IOException If an I/O error occurs while writing the object.
     */
    @Serial
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeChars(new JSONObject(this).toString());
    }

    /**
     * Deserializes a Tile object from the specified input stream.
     * @param in The input stream from which the object is to be read.
     * @throws IOException If an I/O error occurs while reading the object.
     * @throws ClassNotFoundException If the class of a serialized object cannot be found.
     */
    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        JSONObject json = new JSONObject(in.readUTF());
        this.parseJSON(json);
    }

    /**
     * Compares this Tile to another object for equality.
     * @param o The object to compare to.
     * @return True if the tiles are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return wordMult == tile.wordMult && letterMult == tile.letterMult && isConfirmed == tile.isConfirmed && (Objects.equals(letter, tile.letter));
    }
}