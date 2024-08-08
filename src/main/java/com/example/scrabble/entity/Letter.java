package com.example.scrabble.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.json.JSONObject;

import java.io.*;

/**
 * Represents a playable letter.
 *
 */
public class Letter implements Serializable {
    private char letter;
    private int points;

    /**
     * Constructs a new {@code Letter} with the specified character and points.
     *
     * @param letter the character representing the letter.
     * @param points the point value associated with the letter.
     */

    @JsonCreator
    public Letter(
            @JsonProperty("letter") char letter,
            @JsonProperty("points") int points
    ) {
        this.letter = letter;
        this.points = points;
    }

    /**
     * Constructs a new {@code Letter} from a JSON object.
     * The JSON object should have the keys "letter" and "points".
     *
     * @param json the JSON object containing the letter and points.
     */

    public Letter(JSONObject json) {
        this.parseJSON(json);
    }

    /**
     * Parses the JSON object to set the letter and points.
     *
     * @param json the JSON object containing the letter and points.
     */

    private void parseJSON(JSONObject json) {
        this.letter = json.getString("letter").charAt(0);
        this.points = json.getInt("points");
    }

    public char getLetter() {
        return letter;
    }

    public int getPoints() {
        return points;
    }

    /**
     * Two Letter are equal if they have the same letter.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Letter letter1 = (Letter) o;
        return letter == letter1.letter && points == letter1.points;
    }

    /**
     * Returns a string representation of the letter.
     * The string representation consists of the letter's character.
     *
     * @return a string representation of the letter.
     */
    @Override
    public String toString() {
        return Character.toString(getLetter());
    }

    /**
     * Creates and returns a copy (clone) of this letter.
     * The clone will have the same character and point value as this letter.
     *
     * @return a clone of this letter.
     */
    @Override
    public Letter clone() {
        return new Letter(this.getLetter(), this.getPoints());
    }

    /**
     * Serializes this {@code Letter} object.
     * The letter is serialized as a JSON string.
     *
     * @param out the output stream to which the object is to be written.
     * @throws IOException if an I/O error occurs while writing the object.
     */

    @Serial
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeChars(new JSONObject(this).toString());
    }

    /**
     * Deserializes a {@code Letter} object from the specified input stream.
     * The letter is expected to be stored as a JSON string.
     *
     * @param in the input stream from which the object is to be read.
     * @throws IOException if an I/O error occurs while reading the object.
     * @throws ClassNotFoundException if the class of a serialized object cannot be found.
     */

    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        String json = in.readUTF();
        System.out.println(json);
        this.parseJSON(new JSONObject(json));
    }
}
