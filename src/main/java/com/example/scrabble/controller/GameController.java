package com.example.scrabble.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.Game;
import com.example.scrabble.use_case.confirm_play.ConfirmPlayInputBoundary;
import com.example.scrabble.use_case.confirm_play.ConfirmPlayInputData;
import com.example.scrabble.use_case.create_game.CreateGameInputBoundary;
import com.example.scrabble.use_case.create_game.CreateGameInputData;
import com.example.scrabble.use_case.place_letter.PlaceLetterInputBoundary;
import com.example.scrabble.use_case.place_letter.PlaceLetterInputData;
import com.example.scrabble.use_case.end_turn.GetEndTurnInputData;
import com.example.scrabble.use_case.end_turn.GetEndTurnInputBoundary;


@RestController
@RequestMapping("/game")
public class GameController {

  private final GameDataAccess gameDao;
  private final CreateGameInputBoundary createGameInteractor;
  private final PlaceLetterInputBoundary placeLetterInteractor;
  private final ConfirmPlayInputBoundary confirmPlayInteractor;
  private final GetEndTurnInputBoundary getEndTurnInteractor;
  private static final Logger log = LoggerFactory.getLogger(GameController.class);

  @Autowired
  public GameController(
    GameDataAccess gameDao, 
    CreateGameInputBoundary createGameInteractor, 
    PlaceLetterInputBoundary placeLetterInteractor,
    ConfirmPlayInputBoundary confirmPlayInteractor,
    GetEndTurnInputBoundary getEndTurnInteractor
  ) {
    this.gameDao = gameDao;
    this.createGameInteractor = createGameInteractor;
    this.placeLetterInteractor = placeLetterInteractor;
    this.confirmPlayInteractor = confirmPlayInteractor;
    this.getEndTurnInteractor = getEndTurnInteractor;
  }
  
  @PostMapping("/create/")
  public Game createGame(@RequestBody CreateGameInputData data) throws IOException, ClassNotFoundException {
    log.info("Creating game with players:" + data.getPlayerNames());
    return createGameInteractor.execute(data);
  }

  @GetMapping("/{gameId}/")
  public Game getGame(@PathVariable("gameId") int gameId) throws IOException, ClassNotFoundException {
    log.info("Getting game with ID:" + gameId);
    return gameDao.get(gameId);
  }

  @PostMapping("/place_letter/")
  public Game placeLetter(@RequestBody PlaceLetterInputData data) throws IOException, ClassNotFoundException {
    log.info("Game ID: " + data.getGameId() + " Placing letter: " + data.getLetter() + " at position: " + data.getX() + "," + data.getY());
    return placeLetterInteractor.execute(data);
  }

  @PostMapping("/confirm_play/")
  public Game confirmPlay(@RequestBody ConfirmPlayInputData data) throws IOException, ClassNotFoundException {
    log.info("Game ID: " + data.getGameId() + " Confirming play");
    return confirmPlayInteractor.execute(data);
  }

  @PostMapping("/<end_turn>/")
  public Game EndTurn(@RequestBody GetEndTurnInputData data) throws IOException, ClassNotFoundException {
    // Add a logging message that contains enough information to identify the game, the use case, and the input data,
    log.info("Game ID: " + data.getGameId() + " Ending turn");
    return getEndTurnInteractor.execute(data);
  }
}
