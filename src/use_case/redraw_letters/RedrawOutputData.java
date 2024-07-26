package use_case.redraw_letters;

import entity.Letter;

import java.util.ArrayList;
import java.util.List;

public class RedrawOutputData {
    private final boolean drawSuccessful;
    private final List<Letter> hand;


    public RedrawOutputData(boolean drawSuccessful, List<Letter> hand) {
        this.drawSuccessful = drawSuccessful;
        this.hand = hand;
    }

    public boolean isDrawSuccessful() {
        return drawSuccessful;
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