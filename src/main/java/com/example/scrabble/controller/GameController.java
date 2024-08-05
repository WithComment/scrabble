package com.example.scrabble.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.Game;
import com.example.scrabble.use_case.confirm_play.ConfirmPlayInputData;
import com.example.scrabble.use_case.confirm_play.ConfirmPlayInputBoundary;
import com.example.scrabble.use_case.confirm_play.ConfirmPlayOutputData;
import com.example.scrabble.use_case.contest.ContestInputData;
import com.example.scrabble.use_case.contest.ContestInputBoundary;
import com.example.scrabble.use_case.contest.ContestOutputData;
import com.example.scrabble.use_case.create_game.CreateGameInputData;
import com.example.scrabble.use_case.create_game.CreateGameInputBoundary;
import com.example.scrabble.use_case.create_game.CreateGameOutputData;
import com.example.scrabble.use_case.end_game.EndGameInputData;
import com.example.scrabble.use_case.end_game.EndGameInputBoundary;
import com.example.scrabble.use_case.end_game.EndGameOutputData;
import com.example.scrabble.use_case.end_turn.EndTurnInputBoundary;
import com.example.scrabble.use_case.end_turn.EndTurnInputData;
import com.example.scrabble.use_case.end_turn.EndTurnOutputData;
import com.example.scrabble.use_case.get_leaderboard.GetLeaderboardInputData;
import com.example.scrabble.use_case.get_leaderboard.GetLeaderboardInputBoundary;
import com.example.scrabble.use_case.get_leaderboard.GetLeaderboardOutputData;
import com.example.scrabble.use_case.join_game.JoinGameInputData;
import com.example.scrabble.use_case.join_game.JoinGameInputBoundary;
import com.example.scrabble.use_case.join_game.JoinGameOutputData;
import com.example.scrabble.use_case.place_letter.PlaceLetterInputData;
import com.example.scrabble.use_case.place_letter.PlaceLetterInputBoundary;
import com.example.scrabble.use_case.place_letter.PlaceLetterOutputData;
import com.example.scrabble.use_case.start_game.StartGameInputBoundary;
import com.example.scrabble.use_case.start_game.StartGameInputData;
import com.example.scrabble.use_case.start_game.StartGameInteractor;
import com.example.scrabble.use_case.start_game.StartGameOutputData;

@RestController
@RequestMapping("/game")
public class GameController {

  private final SimpMessagingTemplate template;
  private final GameDataAccess gameDao;
  private final CreateGameInputBoundary createGameInteractor;
  private final JoinGameInputBoundary joinGameInteractor;
  private final StartGameInputBoundary startGameInteractor;
  private final PlaceLetterInputBoundary placeLetterInteractor;
  private final ConfirmPlayInputBoundary confirmPlayInteractor;
  private final EndTurnInputBoundary endTurnInteractor;
  private final GetLeaderboardInputBoundary getLeaderboardInteractor;
  private final ContestInputBoundary contestInteractor;
  private final EndGameInputBoundary endGameInteractor;
  private Logger logger = LoggerFactory.getLogger(GameController.class);

  @Autowired
  public GameController(
      SimpMessagingTemplate template,
      GameDataAccess gameDao,
      CreateGameInputBoundary createGameInteractor,
      JoinGameInputBoundary joinGameInteractor,
      StartGameInputBoundary startGameInteractor,
      PlaceLetterInputBoundary placeLetterInteractor,
      ConfirmPlayInputBoundary confirmPlayInteractor,
      EndTurnInputBoundary endTurnInteractor,
      GetLeaderboardInputBoundary getLeaderboardInteractor,
      ContestInputBoundary contestInteractor,
      EndGameInputBoundary endGameInteractor
) {
    this.template = template;
    this.gameDao = gameDao;
    this.createGameInteractor = createGameInteractor;
    this.joinGameInteractor = joinGameInteractor;
    this.startGameInteractor = startGameInteractor;
    this.placeLetterInteractor = placeLetterInteractor;
    this.confirmPlayInteractor = confirmPlayInteractor;
    this.endTurnInteractor = endTurnInteractor;
    this.getLeaderboardInteractor = getLeaderboardInteractor;
    this.contestInteractor = contestInteractor;
    this.endGameInteractor = endGameInteractor;
  }

  private void notifyFrontend(int gameId) {
    logger.info("Notifying frontend of game ID: " + gameId);
    template.convertAndSend("topic/game/" + gameId, gameDao.get(gameId));
  }

    @GetMapping("/{gameId}/")
    public ResponseEntity<EntityModel<Game>> getGame(@PathVariable int gameId) {
        logger.info("Retrieving game with ID: " + gameId);
        Game output = gameDao.get(gameId);
        EntityModel<Game> entityModel = EntityModel.of(output,
            linkTo(methodOn(GameController.class).getGame(gameId)).withSelfRel());
        return ResponseEntity.ok(entityModel);
    }

    @PostMapping("/create/")
    public ResponseEntity<EntityModel<CreateGameOutputData>> create(@RequestBody CreateGameInputData input) {
        logger.info("Creating new game by players: " + input.getPlayerNames());
        CreateGameOutputData output = createGameInteractor.execute(input);
        int gameId = output.getGameId();
        logger.info("Created new game with ID: " + gameId);
        notifyFrontend(gameId);
        EntityModel<CreateGameOutputData> entityModel = EntityModel.of(output,
            linkTo(methodOn(GameController.class).getGame(gameId)).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(entityModel);
    }

    @PostMapping("/{gameId}/join/")
    public ResponseEntity<EntityModel<Game>> join(@PathVariable int gameId, @RequestBody HashMap<String, String> body) {
        logger.info("Joining game with ID: " + gameId);
        String name = body.get("name");
        JoinGameInputData input = new JoinGameInputData(name, gameId);
        joinGameInteractor.execute(input);
        notifyFrontend(gameId);
        EntityModel<Game> entityModel = EntityModel.of(gameDao.get(gameId),
            linkTo(methodOn(GameController.class).getGame(gameId)).withSelfRel());
        return ResponseEntity.ok(entityModel);
    }

    @PostMapping("/{gameId}/start_game/")
    public ResponseEntity<EntityModel<StartGameOutputData>> startGame(@PathVariable int gameId) {
        logger.info("Starting game with ID: " + gameId);
        StartGameOutputData output = startGameInteractor.execute(new StartGameInputData(gameId));
        notifyFrontend(gameId);
        EntityModel<StartGameOutputData> entityModel = EntityModel.of(output,
            linkTo(methodOn(GameController.class).getGame(gameId)).withSelfRel());
        return ResponseEntity.ok(entityModel);
    }

    @PostMapping("/{gameId}/place_letter/")
    public ResponseEntity<EntityModel<PlaceLetterOutputData>> placeLetter(@PathVariable int gameId,
            @RequestBody HashMap<String, Object> input) {
        int x = (int) input.get("x");
        int y = (int) input.get("y");
        char letter = ((String) input.get("letter")).charAt(0);
        logger.info("Game ID: {} Placing letter: {} at position: {},{}", gameId, letter, x, y);
        PlaceLetterOutputData output = placeLetterInteractor.execute(new PlaceLetterInputData(gameId, x, y, letter));
        notifyFrontend(gameId);
        EntityModel<PlaceLetterOutputData> entityModel = EntityModel.of(output,
            linkTo(methodOn(GameController.class).getGame(gameId)).withSelfRel());
        return ResponseEntity.ok(entityModel);
    }

    @PostMapping("/{gameId}/confirm_play/")
    public ResponseEntity<EntityModel<ConfirmPlayOutputData>> confirmPlay(@PathVariable int gameId,
            @RequestBody ConfirmPlayInputData input) {
        logger.info("Game ID: {} Confirming play", input.getGameId());
        ConfirmPlayOutputData output = confirmPlayInteractor.execute(input);
        notifyFrontend(gameId);
        EntityModel<ConfirmPlayOutputData> entityModel = EntityModel.of(output,
            linkTo(methodOn(GameController.class).getGame(gameId)).withSelfRel());
        return ResponseEntity.ok(entityModel);
    }

    @PostMapping("/{gameId}/end_turn/")
    public ResponseEntity<EntityModel<EndTurnOutputData>> endTurn(@PathVariable int gameId, @RequestBody EndTurnInputData input) {
        logger.info("Game ID: {} Ending turn", input.getGameId());
        EndTurnOutputData output = endTurnInteractor.execute(input);
        notifyFrontend(gameId);
        EntityModel<EndTurnOutputData> entityModel = EntityModel.of(output,
            linkTo(methodOn(GameController.class).getGame(gameId)).withSelfRel());
        return ResponseEntity.ok(entityModel);
    }

    @GetMapping("/{gameId}/leaderboard/")
    public ResponseEntity<EntityModel<GetLeaderboardOutputData>> getLeaderboard(@PathVariable int gameId,
            @RequestBody GetLeaderboardInputData input) {
        logger.info("Getting leaderboard for game ID: {}", gameId);
        GetLeaderboardOutputData output = getLeaderboardInteractor.execute(input);
        notifyFrontend(gameId);
        EntityModel<GetLeaderboardOutputData> entityModel = EntityModel.of(output,
            linkTo(methodOn(GameController.class).getGame(gameId)).withSelfRel());
        return ResponseEntity.ok(entityModel);
    }

    @PostMapping("/{gameId}/contest/")
    public ResponseEntity<EntityModel<Game>> contest(@PathVariable int gameId, @RequestBody ContestInputData input) {
        logger.info("Contesting game ID: {}", gameId);
        Game output = contestInteractor.execute(input);
        notifyFrontend(gameId);
        EntityModel<Game> entityModel = EntityModel.of(output,
            linkTo(methodOn(GameController.class).getGame(gameId)).withSelfRel());
        return ResponseEntity.ok(entityModel);
    }

    @PostMapping("/{gameId}/endgame/")
    public ResponseEntity<EntityModel<EndGameOutputData>> endGame(@PathVariable int gameId, @RequestBody EndGameInputData input) {
        logger.info("Ending game ID: {}", gameId);
        EndGameOutputData output = endGameInteractor.execute(input);
        notifyFrontend(gameId);
        EntityModel<EndGameOutputData> entityModel = EntityModel.of(output,
            linkTo(methodOn(GameController.class).getGame(gameId)).withSelfRel());
        return ResponseEntity.ok(entityModel);
    }
}