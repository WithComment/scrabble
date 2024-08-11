package com.example.scrabble.use_case.remove_letter;

import com.example.scrabble.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.use_case.InvalidPlayException;

/**
 * Handles the logic for removing a letter from the board.
 * Implements the RemoveLetterInputBoundary interface.
 */
@Service
public class RemoveLetterInteractor implements RemoveLetterInputBoundary{

    private final GameDataAccess gameDao;

    /**
     * Constructs a RemoveLetterInteractor with the specified GameDataAccess.
     *
     * @param gameDao the data access object for game entities
     */
    @Autowired
    public RemoveLetterInteractor(GameDataAccess gameDao) {
        this.gameDao = gameDao;
    }

    /**
     * Executes the use case to remove a letter from the board.
     * Validates the move, updates the game state, and returns the output data.
     *
     * @param data the input data containing the game ID and coordinates of the letter to remove
     * @return the output data indicating whether the letter removal was successful
     * @throws InvalidPlayException if the removal is not valid
     */
    public RemoveLetterOutputData execute(RemoveLetterInputData data) {
        Game game = gameDao.get(data.getGameId());
        Play play = game.getCurrentPlay();
        Player player = play.getPlayer();
        Board board = game.getBoard();
        Tile selectedTile = board.getTile(data.getX(), data.getY());
        int x = data.getX();
        int y = data.getY();
        boolean isValidClick = false;
        for (Move move : play.getMoves()){
            if (move.getLetter().equals(selectedTile.getLetter())
                    && move.getX() == x
                    && move.getY() == y){
                isValidClick = true;
                break;
            }
        }
        if (!isValidClick) {
            throw new InvalidPlayException("Remove failed.");
        } else {
            play.removeMove(x, y);
            game.getPlayer(player.getId()).addLetter(selectedTile.getLetter());
            selectedTile.removeLetter();
            gameDao.update(game);
            return new RemoveLetterOutputData(true, game.getBoard(), player.getInventory());
        }
    }
}