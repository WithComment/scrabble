package com.example.scrabble.use_case.place_letter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.Board;
import com.example.scrabble.entity.Game;
import com.example.scrabble.entity.Letter;
import com.example.scrabble.entity.LetterBag;
import com.example.scrabble.entity.Move;
import com.example.scrabble.entity.Play;
import com.example.scrabble.entity.Player;
import com.example.scrabble.use_case.InvalidPlayException;

@Service
public class PlaceLetterInteractor implements PlaceLetterInputBoundary {

  public static final String OCCUPIED = "This grid is occupied!";
  public static final String NO_LETTER = "You don't have the letter in your inventory!";

  private final GameDataAccess gameDao;

  @Autowired
  public PlaceLetterInteractor(
    GameDataAccess gameDao
  ) {
    this.gameDao = gameDao;
  }

  @Override
  public Game execute(PlaceLetterInputData data) {
    int x = data.getX();
    int y = data.getY();

    Game game = gameDao.get(data.getGameId());

    Letter letter = LetterBag.getLetter(data.getLetter());
    Play play = game.getCurrentPlay();
    Board board = game.getBoard();
    Player player = play.getPlayer();
    
    if (!board.getCell(x, y).isEmpty()) {
      throw new InvalidPlayException(OCCUPIED);
    } else if (!player.getInventory().contains(letter)) {
      throw new InvalidPlayException(NO_LETTER);
    } else {
      play.addMove(new Move(x, y, letter));
      board.setCell(x, y, letter);
      player.removeLetter(letter);
      return game;
    }
  }
}
