package use_case.redraw_letters;

import entity.Letter;
import entity.LetterBag;
import entity.Player;

import java.util.ArrayList;
import java.util.List;

public class RedrawInteractor implements RedrawInputBoundary{
    final RedrawOutputBoundary playerPresenter;

    public RedrawInteractor(RedrawOutputBoundary redrawOutputBoundary){
        this.playerPresenter = redrawOutputBoundary;
    }

    @Override
    public void execute(RedrawInputData redrawInputData){
        boolean drawSuccessful = false;
        Player player = redrawInputData.getPlayer();
        List<Letter> letters = redrawInputData.getLetters();
        LetterBag letterBag = redrawInputData.getLetterBag();
        if(redrawInputData.getLetterBag().getLength() > 6) {
            int numToRedraw = letters.size();

            letterBag.addLetters(letters);
            player.removeLetter((ArrayList<Letter>) ((ArrayList<Letter>)letters).clone());
            List<Letter> newLetters = letterBag.drawLetters(numToRedraw);
            player.addLetter(newLetters);
            drawSuccessful = true;
        }
        if (drawSuccessful) {
            List<Letter> hand = player.getInventory();
            playerPresenter.prepareSuccessView(new RedrawOutputData(true, hand));
        } else{
            playerPresenter.prepareFailView("There are less than 7 letters in the bag");
        }
    }
}
