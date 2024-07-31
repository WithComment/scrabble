package com.example.scrabble.use_case.remove_letter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.Board;
import com.example.scrabble.entity.Game;
import com.example.scrabble.entity.Play;
import com.example.scrabble.entity.Player;
import com.example.scrabble.entity.Tile;
import com.example.scrabble.use_case.InvalidPlayException;
import com.example.scrabble.entity.Move;

@Service
public class RemoveLetterInteractor implements RemoveLetterInputBoundary{
    
    private final GameDataAccess gameDao;

    @Autowired
    public RemoveLetterInteractor(GameDataAccess gameDao) {
        this.gameDao = gameDao;
    }

    public Game execute(RemoveLetterInputData data) {
        Game game = gameDao.get(data.getGameId());
        Play play = game.getCurrentPlay();
        Player player = play.getPlayer();
        Board board = game.getBoard();
        Tile selectedTile = board.getCell(data.getX(), data.getY());
        int x = data.getX();
        int y = data.getY();
        boolean isValidClick = false;
        for (Move move : play.getMoves()){
            if (move.getLetter() == selectedTile.getLetter()
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
            player.addLetter(selectedTile.getLetter());
            selectedTile.removeLetter();
            gameDao.update(game);
            return game;
        }
    }
}
