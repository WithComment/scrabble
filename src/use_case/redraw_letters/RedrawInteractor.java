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
        if(redrawInputData.getLetterBag().getLength() > 6) {
            Player player = redrawInputData.getPlayer();
            List<Letter> letters = redrawInputData.getLetters();
            LetterBag letterBag = redrawInputData.getLetterBag();
            int numToRedraw = letters.size();

            letterBag.addLetters(letters);
            player.removeLetter((ArrayList<Letter>) ((ArrayList<Letter>)letters).clone());
            List<Letter> newLetters = letterBag.drawLetters(numToRedraw);
            player.addLetter(newLetters);
            drawSuccessful = true;
        }

        if (drawSuccessful) {
            playerPresenter.prepareSuccessView(new RedrawOutputData(true));
        } else{
            playerPresenter.prepareFailView("There are less than 7 letters in the bag");
        }
    }
}
