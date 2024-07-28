package com.example.scrabble.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.Game;
import com.example.scrabble.entity.Player;
import com.example.scrabble.use_case.create_game.CreateGameInputBoundary;
import com.example.scrabble.use_case.create_game.CreateGameInputData;
import com.example.scrabble.use_case.place_letter.PlaceLetterInputData;

@RestController
@RequestMapping("/game")
public class GameController {

  private final GameDataAccess gameDao;
  private final CreateGameInputBoundary createGameInteractor;
  private static final Logger log = LoggerFactory.getLogger(GameController.class);

  @Autowired
  public GameController(GameDataAccess gameDao, CreateGameInputBoundary createGameInteractor) {
    this.gameDao = gameDao;
    this.createGameInteractor = createGameInteractor;
  }
  
  @PostMapping("create/")
  public Game createGame(@RequestBody CreateGameInputData data) throws IOException, ClassNotFoundException {
    // return createGameInteractor.execute(data);
    log.info("Creating game with players:");
    return createGameInteractor.execute(new CreateGameInputData(new LinkedList<String>(){
      {
        add("Player 1");
        add("Player 2");
      }
    }));
  }

  @GetMapping("/{gameId}/")
  public Game getGame(@PathVariable("gameId") int gameId) throws IOException, ClassNotFoundException {
    // log.info("Getting game with ID:" + gameId);
    // return gameDao.get(gameId);
    return createGameInteractor.execute(new CreateGameInputData(new LinkedList<String>() {
      {
        add("Player 1");
        add("Player 2");
      }
    }));
  }

  @PostMapping("/{gameId}/place_letter/")
  public Game placeLetter(@PathVariable("gameId") int gameId, @RequestParam("data") PlaceLetterInputData data) throws IOException, ClassNotFoundException {
    // TODO: insert place letter logic
    throw new UnsupportedOperationException();
  }

  @PostMapping("/{gameId}/confirm_play/")
  public Game confirmPlay(@PathVariable("gameId") int gameId) throws IOException, ClassNotFoundException {
    // TODO: insert confirm play logic
    throw new UnsupportedOperationException();
  }
}
