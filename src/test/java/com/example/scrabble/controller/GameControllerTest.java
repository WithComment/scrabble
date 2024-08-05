package com.example.scrabble.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.web.servlet.MockMvc;

import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.Game;
import com.example.scrabble.entity.Player;
import com.example.scrabble.use_case.confirm_play.ConfirmPlayInteractor;
import com.example.scrabble.use_case.confirm_play.ConfirmPlayOutputData;
import com.example.scrabble.use_case.contest.ContestInteractor;
import com.example.scrabble.use_case.contest.ContestOutputData;
import com.example.scrabble.use_case.create_game.CreateGameInteractor;
import com.example.scrabble.use_case.create_game.CreateGameOutputData;
import com.example.scrabble.use_case.end_turn.EndTurnInteractor;
import com.example.scrabble.use_case.end_turn.EndTurnOutputData;
import com.example.scrabble.use_case.get_leaderboard.GetLeaderboardInteractor;
import com.example.scrabble.use_case.get_leaderboard.GetLeaderboardOutputData;
import com.example.scrabble.use_case.join_game.JoinGameInteractor;
import com.example.scrabble.use_case.join_game.JoinGameOutputData;
import com.example.scrabble.use_case.place_letter.PlaceLetterInteractor;
import com.example.scrabble.use_case.place_letter.PlaceLetterOutputData;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(GameController.class)
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
  private PlaceLetterInteractor placeLetterInteractor;

  @MockBean
  private ConfirmPlayInteractor confirmPlayInteractor;

  @MockBean
  private EndTurnInteractor endTurnInteractor;

  @MockBean
  private GetLeaderboardInteractor getLeaderboardInteractor;

  @MockBean
  private ContestInteractor contestInteractor;

  @Mock
  private Game game;

  private ObjectMapper objectMapper = new ObjectMapper();

  @BeforeEach
  void setUp() {
    this.game = new Game();
  }

  private void testNotifyFrontend(int gameId) {
    verify(template).convertAndSend("topic/game/" + gameId, game);
  }

  @Test
  void testGetGame() throws Exception {
    int gameId = 1;
    when(gameDao.get(gameId)).thenReturn(game);

    mockMvc.perform(get("/game/{gameId}", gameId))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id").exists());
  }

  @Test
  void testCreateGame() throws Exception {
    int gameId = 1;
    CreateGameOutputData outputData = new CreateGameOutputData(null, gameId);
    when(createGameInteractor.execute(any())).thenReturn(outputData);

    mockMvc.perform(post("/game/create")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(new HashMap<String, Object>() {
        {
          put("playerNames", Arrays.asList("Player1", "Player2"));
        }
      })))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.gameId").value(1));

    testNotifyFrontend(gameId);
  }

  @Test
  void testJoinGame() throws Exception {
    int gameId = 1;
    JoinGameOutputData outputData = new JoinGameOutputData("Player3");
    when(joinGameInteractor.execute(any())).thenReturn(outputData);

    mockMvc.perform(post("/game/{gameId}/join", gameId)
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(new HashMap<String, String>() {
        {
          put("name", "Player3");
        }
      })))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.playerName").value("Player3"));

    testNotifyFrontend(gameId);
  }

  @Test
  void testPlaceLetter() throws Exception {
    int gameId = 1;
    PlaceLetterOutputData outputData = new PlaceLetterOutputData(null, null, 0, null);
    when(placeLetterInteractor.execute(any())).thenReturn(outputData);

    mockMvc.perform(post("/game/{gameId}/place_letter", gameId)
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(new HashMap<String, Object>() {
        {
          put("x", 0);
          put("y", 0);
          put("letter", 'A');
        }
      })))
      .andExpect(status().isOk());

    testNotifyFrontend(gameId);
  }

  @Test
  void testConfirmPlay() throws Exception {
    int gameId = 1;
    ConfirmPlayOutputData outputData = new ConfirmPlayOutputData(true);
    when(confirmPlayInteractor.execute(any())).thenReturn(outputData);

    mockMvc.perform(post("/game/{gameId}/confirm_play", gameId)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    testNotifyFrontend(gameId);
  }

  @Test
  void testEndTurn() throws Exception {
    int gameId = 1;
    EndTurnOutputData outputData = new EndTurnOutputData(gameId);
    when(endTurnInteractor.execute(any())).thenReturn(outputData);

    mockMvc.perform(post("/game/{gameId}/end_turn", gameId)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void testGetLeaderboard() throws Exception {
    int gameId = 1;
    Player player1 = mock(Player.class);
    Player player2 = mock(Player.class);
    GetLeaderboardOutputData outputData = new GetLeaderboardOutputData(Arrays.asList(player1, player2));
    when(getLeaderboardInteractor.execute(any())).thenReturn(outputData);

    mockMvc.perform(get("/game/{gameId}/leaderboard", gameId)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void testContest() throws Exception {
    int gameId = 1;
    ContestOutputData outputData = new ContestOutputData(null);
    when(contestInteractor.execute(any())).thenReturn(null);

    mockMvc.perform(post("/game/{gameId}/contest", gameId)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }
}