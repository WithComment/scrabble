package com.example.scrabble.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * The {@code LetterBag} class represents a bag of letters used in a Scrabble game.
 * It allows initializing the bag from a file, drawing letters, and adding letters back to the bag.
 * The class is serializable, making it possible to save and restore the state of the letter bag.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LetterBag implements Serializable {
  private List<Letter> bag;

  /**
   * Constructs a {@code LetterBag} and initializes it with letters from a default file.
   * The file is expected to be located in the "static" directory and named "letters.txt".
   */
  public LetterBag() {
    bag = new ArrayList<>();
    initializeBag(Paths.get("static", "letters.txt"));
  }

  /**
   * Constructs a {@code LetterBag} with a specified list of letters.
   *
   * @param bag the list of letters to initialize the bag with.
   */
  @JsonCreator
  public LetterBag(@JsonProperty("bag") List<Letter> bag) {
    this.bag = bag;
  }

  /**
   * Initializes the bag with letters from a specified file.
   * Each line in the file should contain a letter, its points, and its count separated by spaces.
   *
   * @param filePath the path of the file containing the letters information.
   */
  private void initializeBag(Path filePath) {
    try (BufferedReader br = Files.newBufferedReader(filePath)) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] parts = line.split(" ");
        char letter = parts[0].charAt(0);
        int points = Integer.parseInt(parts[1]);
        int count = Integer.parseInt(parts[2]);
        addLetters(letter, points, count);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Adds the specified number of letters with the given points to the bag.
   *
   * @param letter the letter to be added.
   * @param points the points associated with the letter.
   * @param count the number of times the letter should be added.
   */
  private void addLetters(char letter, int points, int count) {
    for (int i = 0; i < count; i++) {
      bag.add(new Letter(letter, points));
    }
  }

  /**
   * Draws a single letter from the bag. The letters in the bag are shuffled before drawing.
   *
   * @return the drawn {@code Letter}, or {@code null} if the bag is empty.
   */
  private Letter drawLetter() {
    if (bag.isEmpty()) {
      return null;
    } else {
      Collections.shuffle(bag);
      return bag.remove(0);
    }
  }

  /**
   * Adds a list of letters to the bag.
   *
   * @param letters the list of {@code Letter} objects to be added.
   */
  public void addLetters(List<Letter> letters) {
    bag.addAll(letters);
  }

  /**
   * Draws a single letter from the bag and returns it in a {@code List}.
   *
   * @return a {@code List} containing the drawn {@code Letter}.
   */
  public List<Letter> drawLetters() {
    List<Letter> draws = new ArrayList<>();
    draws.add(drawLetter());
    return draws;
  }

  /**
   * Draws the specified number of letters from the bag and returns them in a {@code List}.
   *
   * @param num the number of letters to be drawn.
   * @return a {@code List} containing the drawn letters.
   */
  public List<Letter> drawLetters(int num) {
    List<Letter> draws = new ArrayList<>();
    for (int i = 0; i < num; i++) {
      draws.add(drawLetter());
    }
    return draws;
  }

  /**
   * Returns the number of letters currently in the bag.
   *
   * @return the number of letters in the letter bag.
   */
  @JsonIgnore
  public int getLength() {
    return this.bag.size();
  }

  /**
   * Returns the list of letters currently in the bag.
   *
   * @return the list of {@code Letter} objects in the bag.
   */
  public List<Letter> getBag() {
    return bag;
  }

  /**
   * Custom serialization method. This method is used to serialize the {@code LetterBag} object.
   *
   * @param out the output stream to which the object is to be written.
   * @throws IOException if an I/O error occurs while writing the object.
   */
  @Serial
  private void writeObject(ObjectOutputStream out) throws IOException {
    out.defaultWriteObject();
  }

  /**
   * Custom deserialization method. This method is used to deserialize the {@code LetterBag} object.
   *
   * @param in the input stream from which the object is to be read.
   * @throws IOException if an I/O error occurs while reading the object.
   * @throws ClassNotFoundException if the class of a serialized object cannot be found.
   */
  @Serial
  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
    in.defaultReadObject();
  }

  /**
   * Custom method for when no data is found during deserialization.
   * Initializes the bag with letters from the default file.
   *
   * @throws ObjectStreamException if an error occurs during deserialization.
   */
  @Serial
  private void readObjectNoData() throws ObjectStreamException {
    bag = new ArrayList<>();
    initializeBag(Paths.get("static", "letters.txt"));
  }

  /**
   * Compares this {@code LetterBag} to the specified object for equality.
   * Two {@code LetterBag} objects are considered equal if they contain the same letters in the same frequency.
   *
   * @param obj the object to be compared for equality with this letter bag.
   * @return {@code true} if the specified object is equal to this letter bag, {@code false} otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    LetterBag other = (LetterBag) obj;

    if (bag.size() != other.bag.size()) {
      return false;
    }

    Map<Character, Integer> map1 = new HashMap<>();
    Map<Character, Integer> map2 = new HashMap<>();

    boolean flag = true;
    for (int i = 0; i < bag.size(); i++) {
      map1.put(bag.get(i).getLetter(), map1.getOrDefault(bag.get(i).getLetter(), 0) + 1);
      map2.put(other.bag.get(i).getLetter(), map2.getOrDefault(other.bag.get(i).getLetter(), 0) + 1);
      if (!bag.get(i).equals(other.bag.get(i))) {
        flag = false;
      }
    }
    return flag;
  }

  /**
   * Returns a string representation of the {@code LetterBag}.
   * The string representation consists of the letters currently in the bag.
   *
   * @return a string representation of the letter bag.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("LetterBag contains:\n");
    for (Letter letter : bag) {
      sb.append(letter.toString()).append("\n");
    }
    return sb.toString();
  }
}