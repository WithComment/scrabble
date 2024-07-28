package use_case.remove_letter;
import entity.Board;
import entity.Letter;

import java.util.ArrayList;
import java.util.List;

public class RemoveLetterOutputData {
    private boolean removeSuccessful;
    private Board board;
    private List<Letter> hand;

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
