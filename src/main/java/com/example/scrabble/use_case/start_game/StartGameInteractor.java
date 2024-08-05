package com.example.scrabble.use_case.start_game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.scrabble.data_access.GameDataAccess;

@Service
public class StartGameInteractor implements StartGameInputBoundary {

  private final GameDataAccess gameDao;

  @Autowired
  public StartGameInteractor(GameDataAccess gameDao) {
    this.gameDao = gameDao;
  }

  @Override
  public StartGameOutputData execute(StartGameInputData inputData) {
    gameDao.get(inputData.getGameId()).startGame();

    return new StartGameOutputData(true);
  }
}
