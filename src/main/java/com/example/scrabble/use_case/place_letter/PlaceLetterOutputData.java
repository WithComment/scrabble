package com.example.scrabble.use_case.place_letter;

import java.util.List;

import com.example.scrabble.entity.Board;
import com.example.scrabble.entity.Letter;

public class PlaceLetterOutputData {
    private final Board board;
    private final List<Letter> hand;
    private final int tempScore;
    private final List<String> words;

    public PlaceLetterOutputData(Board board, List<Letter> hand, int tempScore, List<String> words) {
        this.board = board;
        this.hand = hand;
        this.tempScore = tempScore;
        this.words = words;
    }

    public Board getBoard() {
        return board;
    }

    public List<Letter> getHand() {
        return hand;
    }

    public int getTempScore() {
      return tempScore;
    }

    public List<String> getWords() {
      return words;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PlaceLetterOutputData other = (PlaceLetterOutputData) obj;
        return board.equals(other.board) && hand.equals(other.hand) && tempScore == other.tempScore;
    }
}