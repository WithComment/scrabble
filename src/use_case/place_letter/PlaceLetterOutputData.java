package use_case.place_letter;

import java.util.ArrayList;
import java.util.List;

import entity.Board;
import entity.Letter;

public class PlaceLetterOutputData {
  private final Board board;
  private final List<Letter> hand;

  public PlaceLetterOutputData(Board board, List<Letter> hand) {
    this.board = board;
    this.hand = hand;
  }

  public Board getBoard() {
    return board;
  }

  public List<Letter> getHand() {
    return hand;
  }

  public List<Character> getHandCharacters() {
    List<Character> handChars = new ArrayList<>();
    for (Letter letter : hand) {
      handChars.add(letter.getLetter());
    }
    return handChars;
  }
}
