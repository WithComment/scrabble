package com.example.scrabble.use_case.redraw_letters;

import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.Game;
import com.example.scrabble.entity.Letter;
import com.example.scrabble.entity.LetterBag;
import com.example.scrabble.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RedrawInteractor implements RedrawInputBoundary{
    private final GameDataAccess gameDao;

    @Autowired
    public RedrawInteractor(GameDataAccess gameDao) {
        this.gameDao = gameDao;
    }

    private List<Letter> getLetters(List<Letter> inventory, List<String> characters) {
        List<Letter> letters = new ArrayList<>();
        List<Letter> toRemove = new ArrayList<>();

        for (String character : characters) {
            for (Letter letter : inventory) {
                if (letter.getLetter() == character.charAt(0)) {
                    letters.add(letter);
                    toRemove.add(letter);
                    break; // Stop after finding the first matching letter
                }
            }
        }

        // Ensure inventory is mutable
        List<Letter> mutableInventory = new ArrayList<>(inventory);
        mutableInventory.removeAll(toRemove);

        return letters;
    }



    @Override
    public RedrawOutputData execute(RedrawInputData redrawInputData) {
        Game game = gameDao.get(redrawInputData.getGameId());
        Player player = game.getCurrentPlayer();
        List<Letter> letters = getLetters(player.getInventory(), redrawInputData.getCharacters());
        LetterBag letterBag = game.getLetterBag();
        boolean drawSuccessful = false;

        List<Letter> newLetters = new ArrayList<>();

        if(letterBag.getLength() > 6) {
            int numToRedraw = letters.size();
            letterBag.addLetters(letters);
            player.removeLetter((ArrayList<Letter>) ((ArrayList<Letter>)letters).clone());
            newLetters = letterBag.drawLetters(numToRedraw);
            player.addLetter(newLetters);
            drawSuccessful = true;
        }

        /*
        if (drawSuccessful) {
            List<Letter> hand = player.getInventory();
            playerPresenter.prepareSuccessView(new RedrawOutputData(true, hand));
        } else{
            playerPresenter.prepareFailView("There are less than 7 letters in the bag");
        }
         */
        gameDao.update(game);
        return new RedrawOutputData(drawSuccessful, newLetters);
    }
}
