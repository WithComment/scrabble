package main.java.com.example.scrabble.interface_adapter.redraw_letters;

import entity.Letter;
import entity.LetterBag;
import entity.Player;
import use_case.redraw_letters.RedrawInputBoundary;
import use_case.redraw_letters.RedrawInputData;

import java.util.List;

public class RedrawController {
    private RedrawInputBoundary interactor;

    public RedrawController(RedrawInputBoundary interactor) {this.interactor = interactor;}

    public void execute(List<Letter> letters, Player player, LetterBag letterBag) {
        RedrawInputData redrawInputData = new RedrawInputData(letters, player, letterBag);
        interactor.execute(redrawInputData);
    }
}
