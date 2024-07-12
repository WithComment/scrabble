package entity;

public class Tile {
  private final int wordMult;
  private final int letterMult;
  private Letter letter;

  public Tile(int wordMult, int letterMult, Letter letter) {
    this.wordMult = wordMult;
    this.letterMult = letterMult;
    this.letter = letter;
  }

  public int getWordMult() {
    return wordMult;
  }

  public int getLetterMult() {
    return letterMult;
  }

  public Letter getLetter() {
    return letter;
  }

  public void setLetter(Letter letter) {
    this.letter = letter;
  }

  public boolean isEmpty() {
    return letter == null;
  }
}
