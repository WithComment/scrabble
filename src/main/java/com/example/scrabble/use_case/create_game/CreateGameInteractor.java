package com.example.scrabble.use_case.create_game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.Game;
@Service
public class CreateGameInteractor implements CreateGameInputBoundary {
  
  private final GameDataAccess gameDao;

  @Autowired
  public CreateGameInteractor(GameDataAccess gameDao) {
    this.gameDao = gameDao;
  }

  @Override
  public CreateGameOutputData execute(CreateGameInputData data) {
    Game game = new Game(data.getPlayerNames());
    gameDao.create(game);
    int gameId = game.getId();
    return new CreateGameOutputData(game.getPlayers(), gameId);
  }
}
