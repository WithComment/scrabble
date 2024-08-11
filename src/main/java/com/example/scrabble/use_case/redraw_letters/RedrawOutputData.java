package com.example.scrabble.use_case.redraw_letters;

import java.util.List;

import com.example.scrabble.entity.Letter;

/**
 * Represents the output data for the redraw letters use case.
 * Contains information about whether the redraw was successful and the new letters.
 */
public class RedrawOutputData {

  private final boolean isSuccessful;
  private final List<Letter> newLetters;

  /**
   * Constructs a RedrawOutputData instance with the specified parameters.
   *
   * @param isSuccessful a boolean indicating whether the redraw was successful
   * @param newLetters the list of new letters obtained from the redraw
   */
  public RedrawOutputData(boolean isSuccessful, List<Letter> newLetters) {
    this.isSuccessful = isSuccessful;
    this.newLetters = newLetters;
  }

  /**
   * Returns whether the redraw was successful.
   *
   * @return true if the redraw was successful, false otherwise
   */
  public boolean isSuccessful() {
    return isSuccessful;
  }

  /**
   * Returns the list of new letters obtained from the redraw.
   *
   * @return the list of new letters
   */
  public List<Letter> getNewLetters() {
    return newLetters;
  }
}