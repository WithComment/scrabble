package com.example.scrabble.use_case.redraw_letters;

import java.util.List;

import com.example.scrabble.entity.Letter;

public class RedrawOutputData {

  private boolean isSuccessful;
  private List<Letter> newLetters;

  public RedrawOutputData(boolean isSuccessful, List<Letter> newLetters) {
    this.isSuccessful = isSuccessful;
    this.newLetters = newLetters;
  }

  public boolean isSuccessful() {
    return isSuccessful;
  }

  public List<Letter> getNewLetters() {
    return newLetters;
  }
}
