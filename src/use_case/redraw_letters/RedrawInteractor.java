package use_case.redraw_letters;

import entity.Letter;
import entity.LetterBag;
import entity.Player;

import java.util.ArrayList;

public class RedrawInteractor implements RedrawInputBoundary{
    final RedrawOutputBoundary playerPresenter;

    public RedrawInteractor(RedrawOutputBoundary redrawOutputBoundary){
        this.playerPresenter = redrawOutputBoundary;
    }

    @Override
    public void execute(RedrawInputData redrawInputData){
        boolean drawSuccessful = false;
        if(redrawInputData.getLetterBag().getLength() > 6) {
            Player player = redrawInputData.getPlayer();
            ArrayList<Letter> letters = redrawInputData.getLetters();
            LetterBag letterBag = redrawInputData.getLetterBag();

            player.removeLetter(letters);
            letterBag.addLetters(letters);
            ArrayList<Letter> newLetters = letterBag.drawLetters(letters.size());
            player.addLetter(newLetters);
            drawSuccessful = true;
        }

        if (drawSuccessful) {
            playerPresenter.prepareSuccessView(new RedrawOutputData(true));
        } else{
            playerPresenter.prepareFailedView("There are less than 7 letters in the bag");
        }
    }
}
