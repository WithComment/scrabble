package com.example.scrabble.controller;

import com.example.scrabble.use_case.contest.ContestInputData;
import com.example.scrabble.use_case.contest.ContestInteractor;
import com.example.scrabble.use_case.contest.ContestOutputData;
import com.example.scrabble.use_case.get_leaderboard.GetLeaderboardInputBoundary;
import com.example.scrabble.use_case.get_leaderboard.GetLeaderboardInputData;
import com.example.scrabble.use_case.get_leaderboard.GetLeaderboardInteractor;
import com.example.scrabble.use_case.get_leaderboard.GetLeaderboardOutputData;

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
import com.example.scrabble.use_case.confirm_play.ConfirmPlayOutputData;
import com.example.scrabble.use_case.create_game.CreateGameInputBoundary;
import com.example.scrabble.use_case.create_game.CreateGameInputData;
import com.example.scrabble.use_case.place_letter.PlaceLetterInputBoundary;
import com.example.scrabble.use_case.place_letter.PlaceLetterInputData;
import com.example.scrabble.use_case.place_letter.PlaceLetterOutputData;
import com.example.scrabble.use_case.end_turn.GetEndTurnInputData;
import com.example.scrabble.use_case.end_turn.GetEndTurnOutputData;
import com.example.scrabble.use_case.end_turn.GetEndTurnInputBoundary;


@RestController
@RequestMapping("/game")
public class GameController {

  private final GameDataAccess gameDao;
  private final CreateGameInputBoundary createGameInteractor;
  private final PlaceLetterInputBoundary placeLetterInteractor;
  private final ConfirmPlayInputBoundary confirmPlayInteractor;
  private final GetEndTurnInputBoundary getEndTurnInteractor;
  private final GetLeaderboardInputBoundary getLeaderboardInteractor;
  private static final Logger log = LoggerFactory.getLogger(GameController.class);
  private final ContestInteractor contestInteractor;

  @Autowired
  public GameController(
    GameDataAccess gameDao, 
    CreateGameInputBoundary createGameInteractor, 
    PlaceLetterInputBoundary placeLetterInteractor,
    ConfirmPlayInputBoundary confirmPlayInteractor,
    GetEndTurnInputBoundary getEndTurnInteractor,
    GetLeaderboardInteractor getLeaderboardInteractor,
    ContestInteractor contestInteractor
  ) {
    this.gameDao = gameDao;
    this.createGameInteractor = createGameInteractor;
    this.placeLetterInteractor = placeLetterInteractor;
    this.confirmPlayInteractor = confirmPlayInteractor;
    this.getEndTurnInteractor = getEndTurnInteractor;
    this.getLeaderboardInteractor = getLeaderboardInteractor;
    this.contestInteractor = contestInteractor;
  }
  
  @PostMapping("/create/")
  public Game createGame(@RequestBody CreateGameInputData data) {
    log.info("Creating game with players:" + data.getPlayerNames());
    return createGameInteractor.execute(data);
  }

  @GetMapping("/{gameId}/")
  public Game getGame(@PathVariable("gameId") int gameId) {
    log.info("Getting game with ID:" + gameId);
    return gameDao.get(gameId);
  }

  @PostMapping("/place_letter/")
  public PlaceLetterOutputData placeLetter(@RequestBody PlaceLetterInputData data) {
    log.info("Game ID: " + data.getGameId() + " Placing letter: " + data.getLetter() + " at position: " + data.getX() + "," + data.getY());
    return placeLetterInteractor.execute(data);
  }

  @PostMapping("/confirm_play/")
  public ConfirmPlayOutputData confirmPlay(@RequestBody ConfirmPlayInputData data) {
    log.info("Game ID: " + data.getGameId() + " Confirming play");
    return confirmPlayInteractor.execute(data);
  }

  @PostMapping("/end_turn/")
  public GetEndTurnOutputData EndTurn(@RequestBody GetEndTurnInputData data) {
    // Add a logging message that contains enough information to identify the game, the use case, and the input data,
    log.info("Game ID: " + data.getGameId() + " Ending turn");
    return getEndTurnInteractor.execute(data);
  }

  @GetMapping("/leaderboard/")
  public GetLeaderboardOutputData getLeaderboard(@RequestBody GetLeaderboardInputData data) {
    int gameId = data.getGameId();
    log.info("Getting leaderboard for game ID: " + gameId);
    return getLeaderboardInteractor.execute(new GetLeaderboardInputData(gameId, data.getPlayers()));
  }

  @PostMapping("/contest/")
  public ContestOutputData contest(@RequestBody ContestInputData data) {
    log.info("Player with ID:" + data.getPlayerId() + "is contesting game with ID: " + data.getGameId());
    return contestInteractor.execute(data);
  }
}
