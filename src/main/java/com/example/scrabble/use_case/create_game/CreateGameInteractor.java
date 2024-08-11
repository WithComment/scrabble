package com.example.scrabble.use_case.create_game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.Game;

/**
 * Handle the create game use case.
 */
@Service
public class CreateGameInteractor implements CreateGameInputBoundary {

  private final GameDataAccess gameDao;

  /**
   * Constructs a CreateGameInteractor instance with the specified GameDataAccess.
   *
   * @param gameDao the data access object for game data
   */
  @Autowired
  public CreateGameInteractor(GameDataAccess gameDao) {
    this.gameDao = gameDao;
  }

  /**
   * Executes the create game use case.
   *
   * @param data the input data for creating a game
   * @return the output data containing the list of players and the game ID
   */
  @Override
  public CreateGameOutputData execute(CreateGameInputData data) {
    Game game = new Game(data.getPlayerNames());
    gameDao.create(game);
    int gameId = game.getId();
    return new CreateGameOutputData(game.getPlayers(), gameId);
  }
}