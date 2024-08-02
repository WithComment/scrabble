package com.example.scrabble.entity;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * The {@code LetterBag} class represents a bag of letters used in a game.
 * It allows initializing the bag from a file, drawing letters, and adding letters back to the bag.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LetterBag implements Serializable {
  private List<Letter> bag;

  /**
   * Constructs a {@code LetterBag} and initializes it with letters from a specified file.
   */
  public LetterBag() {
    bag = new ArrayList<>();
    initializeBag(Paths.get("static", "letters.txt"));
  }

  @JsonCreator
  public LetterBag(@JsonProperty("bag") List<Letter> bag){
    this.bag = bag;
  }

  /**
   * Initializes the bag with letters from a specified file.
   * Each line in the file should contain a letter, its points, and its count separated by spaces.
   *
   * @param filePath the name of the file containing the letters information
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
   * @param letter the letter to be added
   * @param points the points for the letter
   * @param count the number of times the letter should be added
   */
  private void addLetters(char letter, int points, int count) {
    for (int i = 0; i < count; i++) {
      bag.add(new Letter(letter, points));
    }
  }

  /**
   * Draws a single letter from the bag. The letters in the bag are shuffled before drawing.
   *
   * @return the drawn {@code Letter}, or {@code null} if the bag is empty
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
   * @param letters the list of {@code Letter} objects to be added
   */
  public void addLetters(List<Letter> letters) {
    for (Letter letter : letters) {
      bag.add(letter);
    }
  }

  /**
   * Draws a single letter from the bag and returns it in an {@code List}.
   *
   * @return an {@code List} containing the drawn {@code Letter}
   */
  public List<Letter> drawLetters() {
    List<Letter> draws = new ArrayList<>();
    draws.add(drawLetter());
    return draws;
  }

  /**
   * Draws the specified number of letters from the bag and returns them in an {@code List}.
   *
   * @param num the number of letters to be drawn
   * @return an {@code List} containing the drawn letters
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
   * @return the number of letters in the letter bag
   */
  @JsonIgnore
  public int getLength() {
    return this.bag.size();
  }

  public List<Letter> getBag() {
    return bag;
  }

  // Custom serialization method
  private void writeObject(ObjectOutputStream out) throws IOException {
    out.defaultWriteObject();
  }

  // Custom deserialization method
  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
    in.defaultReadObject();
  }

  // Custom method for when no data is found during deserialization
  private void readObjectNoData() throws ObjectStreamException {
    bag = new ArrayList<>();
    initializeBag(Paths.get("static", "letters.txt"));
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj){return true;}

    if (obj == null || getClass() != obj.getClass()){return false;}

    LetterBag other = (LetterBag) obj;

    if (bag.size() != other.bag.size()) {
      return false;
    }

    Map<Character, Integer> map1 = new HashMap<>();
    Map<Character, Integer> map2 = new HashMap<>();

    boolean flag = true;
    for(int i = 0; i < bag.size(); i++){
      map1.put(bag.get(i).getLetter(), map1.getOrDefault(bag.get(i).getLetter(), 0) + 1);
      map2.put(other.bag.get(i).getLetter(), map2.getOrDefault(other.bag.get(i).getLetter(), 0) + 1);
      if(!bag.get(i).equals(other.bag.get(i))){
        flag = false;
      }
    }
    return flag;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("LetterBag contains:\n");
    for (Letter letter : bag) {
      sb.append(letter.toString()).append("\n");
    }
    return sb.toString();
  }
}
