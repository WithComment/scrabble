package com.example.scrabble.use_case.start_game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.Game;

@Service
public class StartGameInteractor implements StartGameInputBoundary {

  private final GameDataAccess gameDao;

  @Autowired
  public StartGameInteractor(GameDataAccess gameDao) {
    this.gameDao = gameDao;
  }

  @Override
  public StartGameOutputData execute(StartGameInputData inputData) {
    Game game = gameDao.get(inputData.getGameId());
    game.startGame();
    game.startTurn();

    gameDao.update(game);

    return new StartGameOutputData(true);
  }
}
