package com.example.scrabble.use_case.start_game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.Game;

/**
 * Handles the logic for starting a game.
 * Implements the StartGameInputBoundary interface.
 */
@Service
public class StartGameInteractor implements StartGameInputBoundary {

  private final GameDataAccess gameDao;

  /**
   * Constructs a StartGameInteractor with the specified GameDataAccess.
   *
   * @param gameDao the data access object for game entities
   */
  @Autowired
  public StartGameInteractor(GameDataAccess gameDao) {
    this.gameDao = gameDao;
  }

  /**
   * Executes the use case to start a game.
   * Retrieves the game by ID, starts the game, updates the game in the data,
   * and returns the output data indicating success.
   *
   * @param inputData the input data containing the game ID
   * @return the output data indicating whether the game start was successful
   */
  @Override
  public StartGameOutputData execute(StartGameInputData inputData) {
    Game game = gameDao.get(inputData.getGameId());
    game.startGame();

    gameDao.update(game);

    return new StartGameOutputData(true);
  }
}