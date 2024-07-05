package use_case.redraw_letters;

import entity.Letter;
import entity.LetterBag;
import entity.Player;
import java.util.ArrayList;

public class RedrawInputData {
    private final ArrayList<Letter> letters;
    private final Player player;
    private final LetterBag letterBag;

    public RedrawInputData(ArrayList<Letter> letters, Player player, LetterBag letterBag) {
        this.letters = letters;
        this.player = player;
        this.letterBag = letterBag;
    }
    ArrayList<Letter> getLetters() {
        return letters;
    }
    Player getPlayer() {
        return player;
    }
    LetterBag getLetterBag(){
        return letterBag;
    }
}
