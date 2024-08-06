package com.example.scrabble.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Arrays;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.Game;
import com.example.scrabble.entity.Player;
import com.example.scrabble.use_case.confirm_play.ConfirmPlayInteractor;
import com.example.scrabble.use_case.confirm_play.ConfirmPlayOutputData;
import com.example.scrabble.use_case.contest.ContestInputData;
import com.example.scrabble.use_case.contest.ContestInteractor;
import com.example.scrabble.use_case.contest.ContestOutputData;
import com.example.scrabble.use_case.create_game.CreateGameInteractor;
import com.example.scrabble.use_case.create_game.CreateGameOutputData;
import com.example.scrabble.use_case.end_game.EndGameInteractor;
import com.example.scrabble.use_case.end_game.EndGameOutputData;
import com.example.scrabble.use_case.end_turn.EndTurnInteractor;
import com.example.scrabble.use_case.end_turn.EndTurnOutputData;
import com.example.scrabble.use_case.get_leaderboard.GetLeaderboardInteractor;
import com.example.scrabble.use_case.get_leaderboard.GetLeaderboardOutputData;
import com.example.scrabble.use_case.join_game.JoinGameInteractor;
import com.example.scrabble.use_case.join_game.JoinGameOutputData;
import com.example.scrabble.use_case.place_letter.PlaceLetterInteractor;
import com.example.scrabble.use_case.place_letter.PlaceLetterOutputData;
import com.example.scrabble.use_case.redraw_letters.RedrawInputBoundary;
import com.example.scrabble.use_case.redraw_letters.RedrawOutputData;
import com.example.scrabble.use_case.remove_letter.RemoveLetterInputBoundary;
import com.example.scrabble.use_case.remove_letter.RemoveLetterInputData;
import com.example.scrabble.use_case.remove_letter.RemoveLetterOutputData;
import com.example.scrabble.use_case.start_game.StartGameInteractor;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(GameController.class)
@AutoConfigureMockMvc(addFilters = false)
public class GameControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private SimpMessagingTemplate template;

  @MockBean
  private GameDataAccess gameDao;

  @MockBean
  private CreateGameInteractor createGameInteractor;

  @MockBean
  private JoinGameInteractor joinGameInteractor;

  @MockBean
  private StartGameInteractor startGameInteractor;

  @MockBean
  private PlaceLetterInteractor placeLetterInteractor;

  @MockBean
  private RemoveLetterInputBoundary removeLetterInteractor;

  @MockBean
  private ConfirmPlayInteractor confirmPlayInteractor;

  @MockBean
  private RedrawInputBoundary redrawInteractor;

  @MockBean
  private EndTurnInteractor endTurnInteractor;

  @MockBean
  private GetLeaderboardInteractor getLeaderboardInteractor;

  @MockBean
  private ContestInteractor contestInteractor;

  @MockBean
  private EndGameInteractor endGameInteractor;

  private Game game;

  private ObjectMapper objectMapper = new ObjectMapper();

  @BeforeEach
  void setUp() {
    game = new Game();
    when(gameDao.get(anyInt())).thenReturn(game);
  }

  private void testNotifyFrontend(int gameId, String type) {
    verify(template).convertAndSend("/topic/game/" + gameId, new Message(game, type));
  }

  @Test
  void testGetGame() throws Exception {
    int gameId = 1;

    mockMvc.perform(get("/game/{gameId}/", gameId))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id").exists());
  }

  @Test
  void testCreateGame() throws Exception {
    int gameId = 1;
    CreateGameOutputData outputData = new CreateGameOutputData(null, gameId);
    when(createGameInteractor.execute(any())).thenReturn(outputData);

    mockMvc.perform(post("/game/create/")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(new HashMap<String, Object>() {
        {
          put("playerNames", Arrays.asList("Player1", "Player2"));
        }
      })))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.gameId").value(1));

    testNotifyFrontend(gameId, "create");
  }

  @Test
  void testJoinGame() throws Exception {
    int gameId = 1;
    JoinGameOutputData outputData = new JoinGameOutputData("Player3");
    when(joinGameInteractor.execute(any())).thenReturn(outputData);

    mockMvc.perform(post("/game/{gameId}/join/", gameId)
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(new HashMap<String, Object>() {
        {
          put("gameId", 1);
          put("playerName", "Player3");
        }
      })))
      .andExpect(status().isOk());

    testNotifyFrontend(gameId, "join");
  }

  @Test
  void testPlaceLetter() throws Exception {
    int gameId = 1;
    PlaceLetterOutputData outputData = new PlaceLetterOutputData(null, null, 0, null);
    when(placeLetterInteractor.execute(any())).thenReturn(outputData);

    mockMvc.perform(post("/game/{gameId}/place_letter/", gameId)
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(new HashMap<String, Object>() {
        {
          put("x", 0);
          put("y", 0);
          put("letter", 'A');
        }
      })))
      .andExpect(status().isOk());

    testNotifyFrontend(gameId, "place_letter");
  }

  @Test
  void testRemoveLetter() throws Exception {
    int gameId = 1;
    RemoveLetterOutputData outputData = new RemoveLetterOutputData(true, null, null);
    when(removeLetterInteractor.execute(any())).thenReturn(outputData);

    mockMvc.perform(post("/game/{gameId}/remove_letter/", gameId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(new HashMap<String, Object>() {
          {
            put("x", 0);
            put("y", 0);
          }
        })))
        .andExpect(status().isOk());

    testNotifyFrontend(gameId, "remove_letter");
  }

  @Test
  void testRedraw() throws Exception {
    int gameId = 1;
    RedrawOutputData outputData = new RedrawOutputData(false, null);
    when(redrawInteractor.execute(any())).thenReturn(outputData);

    mockMvc.perform(post("/game/{gameId}/redraw_letters/", gameId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(new HashMap<String, Object>() {
          {
            put("gameId", 1);
            put("characters", null);
          }
        })))
        .andExpect(status().isOk());

    testNotifyFrontend(gameId, "redraw_letters");
  }

  @Test
  void testConfirmPlay() throws Exception {
    int gameId = 1;
    ConfirmPlayOutputData outputData = new ConfirmPlayOutputData(true);
    when(confirmPlayInteractor.execute(any())).thenReturn(outputData);

    mockMvc.perform(post("/game/{gameId}/confirm_play/", gameId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(new HashMap<String, Object>() {
          {
            put("gameId", 1);
          }
        })))
        .andExpect(status().isOk());

    testNotifyFrontend(gameId, "confirm_play");
  }

  @Test
  void testEndTurn() throws Exception {
    int gameId = 1;
    EndTurnOutputData outputData = new EndTurnOutputData(gameId);
    when(endTurnInteractor.execute(any())).thenReturn(outputData);

    mockMvc.perform(post("/game/{gameId}/end_turn/", gameId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(new HashMap<String, Object>() {
          {
            put("gameId", 1);
            put("wordsToBeConfirmed", null);
          }
        })))
        .andExpect(status().isOk());

    testNotifyFrontend(gameId, "end_turn");
  }

  @Test
  void testGetLeaderboard() throws Exception {
    int gameId = 1;
    Player player1 = mock(Player.class);
    Player player2 = mock(Player.class);
    GetLeaderboardOutputData outputData = new GetLeaderboardOutputData(Arrays.asList(player1, player2));
    when(getLeaderboardInteractor.execute(any())).thenReturn(outputData);

    mockMvc.perform(get("/game/{gameId}/leaderboard/", gameId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(new HashMap<String, Object>() {
          {
            put("gameId", 1);
            put("players", Arrays.asList(0, 1));
          }
        })))
        .andExpect(status().isOk());

    testNotifyFrontend(gameId, "get_leaderboard");
  }

  @Test
  void testContest() throws Exception {
    int gameId = 1;
    ContestOutputData outputData = new ContestOutputData(null, true);
    when(contestInteractor.execute(any())).thenReturn(outputData);

    mockMvc.perform(post("/game/{gameId}/contest/", gameId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(new ContestInputData(1, 0, true))))
        .andExpect(status().isOk());

    testNotifyFrontend(gameId, "contest");
  }

  @Test
  void testEndGame() throws Exception {
    int gameId = 1;
    EndGameOutputData outputData = new EndGameOutputData(null);
    when(endGameInteractor.execute(any())).thenReturn(outputData);

    mockMvc.perform(post("/game/{gameId}/end_game/", gameId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(new HashMap<String, Object>() {
          {
            put("gameId", 1);
          }
        })))
        .andExpect(status().isOk());

    testNotifyFrontend(gameId, "end_game");
  }
}