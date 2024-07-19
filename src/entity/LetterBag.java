package entity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * The {@code LetterBag} class represents a bag of letters used in a game.
 * It allows initializing the bag from a file, drawing letters, and adding letters back to the bag.
 */
public class LetterBag {
  private ArrayList<Letter> bag;

  /**
   * Constructs a {@code LetterBag} and initializes it with letters from a specified file.
   */
  public LetterBag() {
    bag = new ArrayList<>();
    initializeBag("static/letters.txt");
  }

  /**
   * Initializes the bag with letters from a specified file.
   * Each line in the file should contain a letter, its points, and its count separated by spaces.
   *
   * @param filename the name of the file containing the letters information
   */
  private void initializeBag(String filename) {
    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
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
  public void addLetters(ArrayList<Letter> letters) {
    for (Letter letter : letters) {
      bag.add(letter);
    }
  }

  /**
   * Draws a single letter from the bag and returns it in an {@code ArrayList}.
   *
   * @return an {@code ArrayList} containing the drawn {@code Letter}
   */
  public ArrayList<Letter> drawLetters() {
    ArrayList<Letter> draws = new ArrayList<>();
    draws.add(drawLetter());
    return draws;
  }

  /**
   * Draws the specified number of letters from the bag and returns them in an {@code ArrayList}.
   *
   * @param num the number of letters to be drawn
   * @return an {@code ArrayList} containing the drawn letters
   */
  public ArrayList<Letter> drawLetters(int num) {
    ArrayList<Letter> draws = new ArrayList<>();
    for (int i = 0; i < num; i++) {
      draws.add(drawLetter());
    }
    return draws;
  }

  /**
   * Returns the number of letters currently in the bag.
   *
   * @return the number of letters in the bag
   */
  public int getLength() {
    return bag.size();
  }
}
