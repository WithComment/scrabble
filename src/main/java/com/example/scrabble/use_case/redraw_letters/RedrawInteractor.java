package com.example.scrabble.use_case.redraw_letters;

import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.Game;
import com.example.scrabble.entity.Letter;
import com.example.scrabble.entity.LetterBag;
import com.example.scrabble.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RedrawInteractor implements RedrawInputBoundary{
    private final GameDataAccess gameDao;

    @Autowired
    public RedrawInteractor(GameDataAccess gameDao) {
        this.gameDao = gameDao;
    }

    private List<Letter> getLetters(List<Letter> inventory, List<Character> characters) {
        List<Letter> letters = new ArrayList<>();
        for(char character : characters) {
            for (int i = 0; i < inventory.size(); i++) {
                if (inventory.get(i).getLetter() == character) {
                    letters.add(inventory.remove(i));
                }
            }
        }
        return letters;
    }

    @Override
    public Game execute(RedrawInputData redrawInputData) throws IOException, ClassNotFoundException {
        Game game = gameDao.get(redrawInputData.getGameId());
        Player player = game.getCurrentPlayer();
        List<Letter> letters = getLetters(player.getInventory(), redrawInputData.getCharacters());
        LetterBag letterBag = game.getLetterBag();
        boolean drawSuccessful = false;

        if(letterBag.getLength() > 6) {
            int numToRedraw = letters.size();
            letterBag.addLetters(letters);
            player.removeLetter((ArrayList<Letter>) ((ArrayList<Letter>)letters).clone());
            List<Letter> newLetters = letterBag.drawLetters(numToRedraw);
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
        return game;
    }
}
