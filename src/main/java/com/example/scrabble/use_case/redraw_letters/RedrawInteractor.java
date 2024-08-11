package com.example.scrabble.use_case.redraw_letters;

import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.Game;
import com.example.scrabble.entity.Letter;
import com.example.scrabble.entity.LetterBag;
import com.example.scrabble.entity.Player;
import com.example.scrabble.use_case.end_turn.EndTurnInputData;
import com.example.scrabble.use_case.end_turn.EndTurnInteractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles the logic for redrawing letters.
 * Implements the RedrawInputBoundary interface.
 */
@Service
public class RedrawInteractor implements RedrawInputBoundary {
    private final GameDataAccess gameDao;

    /**
     * Constructs a RedrawInteractor with the specified GameDataAccess.
     *
     * @param gameDao the data access object for game entities
     */
    @Autowired
    public RedrawInteractor(GameDataAccess gameDao) {
        this.gameDao = gameDao;
    }

    /**
     * Retrieves letters from the player's inventory that match the specified characters.
     *
     * @param inventory the player's current inventory of letters
     * @param characters the list of characters to match against the inventory
     * @return a list of letters that match the specified characters
     */
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

    /**
     * Executes the use case to redraw letters.
     * Validates the redraw, updates the game state, and returns the output data.
     *
     * @param redrawInputData the input data containing the game ID and characters to redraw
     * @return the output data indicating whether the redraw was successful and the new letters
     */
    @Override
    public RedrawOutputData execute(RedrawInputData redrawInputData) {
        Game game = gameDao.get(redrawInputData.getGameId());
        Player player = game.getCurrentPlayer();
        List<Letter> letters = getLetters(player.getInventory(), redrawInputData.getCharacters());
        LetterBag letterBag = game.getLetterBag();
        boolean drawSuccessful = false;

        List<Letter> newLetters = new ArrayList<>();

        if (letterBag.getLength() > 6) {
            int numToRedraw = letters.size();
            letterBag.addLetters(letters);
            player.removeLetter((ArrayList<Letter>) ((ArrayList<Letter>) letters).clone());
            newLetters = letterBag.drawLetters(numToRedraw);
            player.addLetter(newLetters);
            drawSuccessful = true;
        }
        gameDao.update(game);
        EndTurnInteractor endTurnInteractor = new EndTurnInteractor(gameDao);
        endTurnInteractor.execute(new EndTurnInputData(game.getId()));
        return new RedrawOutputData(drawSuccessful, newLetters);
    }
}