package com.example.scrabble.use_case.create_game;

import java.io.FileNotFoundException;
import java.io.IOException;
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
  public Game execute(CreateGameInputData data) throws FileNotFoundException, IOException {
    // Game game = new Game();
    // for (String playerName : data.getPlayerNames()) {
    //   game.addPlayer(new Player(playerName));
    // }
    // return gameDao.create(game);

    List<Player> players = new LinkedList<>();
    players.add(new Player(0));
    players.add(new Player(1));
    Game game = new Game(players);
    return game;
  }
}
