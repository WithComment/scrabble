package com.example.scrabble.use_case.remove_letter;

import com.example.scrabble.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.use_case.InvalidPlayException;

@Service
public class RemoveLetterInteractor implements RemoveLetterInputBoundary{
    
    private final GameDataAccess gameDao;

    @Autowired
    public RemoveLetterInteractor(GameDataAccess gameDao) {
        this.gameDao = gameDao;
    }

    public RemoveLetterOutputData execute(RemoveLetterInputData data) {
        Game game = gameDao.get(data.getGameId());
        Play play = game.getCurrentPlay();
        Player player = play.getPlayer();
        Board board = game.getBoard();
        Tile selectedTile = board.getCell(data.getX(), data.getY());
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
            System.out.println("Before adding");
            for (Letter letter : player.getInventory()){
                System.out.println(letter);
            };
            System.out.println("Adding " + selectedTile.getLetter());
            player.addLetter(selectedTile.getLetter());
            System.out.println("After adding " + selectedTile.getLetter());
            for (Letter letter : player.getInventory()) {
                System.out.println(letter);
            };
            selectedTile.removeLetter();
            gameDao.update(game);
            return new RemoveLetterOutputData(true, game.getBoard(), player.getInventory());
        }
    }
}
