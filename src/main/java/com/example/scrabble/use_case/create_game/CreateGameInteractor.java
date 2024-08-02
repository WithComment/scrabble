package com.example.scrabble.use_case.create_game;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.Game;
import com.example.scrabble.entity.Player;

@Service
public class CreateGameInteractor implements CreateGameInputBoundary {
  
  private final GameDataAccess gameDao;

  @Autowired
  public CreateGameInteractor(GameDataAccess gameDao) {
    this.gameDao = gameDao;
  }

  @Override
  public Game execute(CreateGameInputData data) {
    Game game = new Game(data.getPlayerNames());
    game.startGame();
    return gameDao.create(game);
  }
}
