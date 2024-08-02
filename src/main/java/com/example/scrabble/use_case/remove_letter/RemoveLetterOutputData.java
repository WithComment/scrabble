package com.example.scrabble.use_case.remove_letter;
import com.example.scrabble.entity.Board;
import com.example.scrabble.entity.Letter;

import java.util.ArrayList;
import java.util.List;

public class RemoveLetterOutputData {
    private final boolean removeSuccessful;
    private final Board board;
    private final List<Letter> hand;

    public RemoveLetterOutputData(boolean removeSuccessful, Board board, List<Letter> hand){
        this.removeSuccessful = removeSuccessful;
        this.board = board;
        this.hand = hand;
    }
    public boolean isRemoveSuccessful(){
        return removeSuccessful;
    }

    public Board getBoard(){
        return board;
    }

    public List<Letter> getHand(){
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
