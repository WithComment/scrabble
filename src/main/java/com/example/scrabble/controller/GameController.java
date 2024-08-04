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
import com.example.scrabble.use_case.confirm_play.ConfirmPlayInteractor;
import com.example.scrabble.use_case.confirm_play.ConfirmPlayOutputData;
import com.example.scrabble.use_case.contest.ContestInputData;
import com.example.scrabble.use_case.contest.ContestInteractor;
import com.example.scrabble.use_case.contest.ContestOutputData;
import com.example.scrabble.use_case.create_game.CreateGameInputData;
import com.example.scrabble.use_case.create_game.CreateGameInteractor;
import com.example.scrabble.use_case.create_game.CreateGameOutputData;
import com.example.scrabble.use_case.end_turn.EndTurnInteractor;
import com.example.scrabble.use_case.end_turn.GetEndTurnInputData;
import com.example.scrabble.use_case.end_turn.EndTurnOutputData;
import com.example.scrabble.use_case.get_leaderboard.GetLeaderboardInputData;
import com.example.scrabble.use_case.get_leaderboard.GetLeaderboardInteractor;
import com.example.scrabble.use_case.get_leaderboard.GetLeaderboardOutputData;
import com.example.scrabble.use_case.join_game.JoinGameInputData;
import com.example.scrabble.use_case.join_game.JoinGameInteractor;
import com.example.scrabble.use_case.join_game.JoinGameOutputData;
import com.example.scrabble.use_case.place_letter.PlaceLetterInputData;
import com.example.scrabble.use_case.place_letter.PlaceLetterInteractor;
import com.example.scrabble.use_case.place_letter.PlaceLetterOutputData;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/game")
public class GameController {

  private final SimpMessagingTemplate template;
  private final GameDataAccess gameDao;
  private final CreateGameInteractor createGameInteractor;
  private final JoinGameInteractor joinGameInteractor;
  private final PlaceLetterInteractor placeLetterInteractor;
  private final ConfirmPlayInteractor confirmPlayInteractor;
  private final EndTurnInteractor endTurnInteractor;
  private final GetLeaderboardInteractor getLeaderboardInteractor;
  private final ContestInteractor contestInteractor;
  private final GameModelAssembler assembler = new GameModelAssembler();
  private Logger logger = LoggerFactory.getLogger(GameController.class);

  @Autowired
  public GameController(
      SimpMessagingTemplate template,
      GameDataAccess gameDao,
      JoinGameInteractor joinGameInteractor,
      CreateGameInteractor createGameInteractor,
      PlaceLetterInteractor placeLetterInteractor,
      ConfirmPlayInteractor confirmPlayInteractor,
      EndTurnInteractor endTurnInteractor,
      GetLeaderboardInteractor getLeaderboardInteractor,
      ContestInteractor contestInteractor) {
    this.template = template;
    this.gameDao = gameDao;
    this.joinGameInteractor = joinGameInteractor;
    this.createGameInteractor = createGameInteractor;
    this.placeLetterInteractor = placeLetterInteractor;
    this.confirmPlayInteractor = confirmPlayInteractor;
    this.endTurnInteractor = endTurnInteractor;
    this.getLeaderboardInteractor = getLeaderboardInteractor;
    this.contestInteractor = contestInteractor;
  }

  private void notifyFrontend(int gameId) {
    logger.info("Notifying frontend of game ID: " + gameId);
    template.convertAndSend("/topic/game/" + gameId, gameDao.get(gameId));
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
    public ResponseEntity<EntityModel<JoinGameOutputData>> join(@PathVariable int gameId, @RequestBody HashMap<String, String> body) {
        logger.info("Joining game with ID: " + gameId);
        String name = body.get("name");
        JoinGameInputData input = new JoinGameInputData(name, gameId);
        JoinGameOutputData output = joinGameInteractor.execute(input);
        notifyFrontend(gameId);
        EntityModel<JoinGameOutputData> entityModel = EntityModel.of(output,
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
    public ResponseEntity<EntityModel<EndTurnOutputData>> endTurn(@PathVariable int gameId, @RequestBody GetEndTurnInputData input) {
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
}
