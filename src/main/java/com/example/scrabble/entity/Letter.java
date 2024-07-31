package com.example.scrabble.entity;

import org.json.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Represents a playable letter.
 *
 * @param letter The letter.
 * @param points The points the letter is worth.
 */
public class Letter implements Serializable {
    private char letter;
    private int points;

    public Letter(){}
    public Letter(
            char letter,
            int points
    ) {
        this.letter = letter;
        this.points = points;
    }

    public Letter(JSONObject json) {
        this.parseJSON(json);
    }

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

    @Override
    public String toString() {
        return Character.toString(getLetter());
    }

    @Override
    public Letter clone() {
        return new Letter(this.getLetter(), this.getPoints());
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeChars(new JSONObject(this).toString());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        String json = in.readUTF();
        System.out.println(json);
        this.parseJSON(new JSONObject(json));
    }
}
