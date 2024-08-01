package com.example.scrabble.controller;

import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.Game;
import com.example.scrabble.use_case.confirm_play.ConfirmPlayInputBoundary;
import com.example.scrabble.use_case.contest.ContestInputData;
import com.example.scrabble.use_case.contest.ContestInteractor;
import com.example.scrabble.use_case.create_game.CreateGameInputBoundary;
import com.example.scrabble.use_case.create_game.CreateGameInputData;
import com.example.scrabble.use_case.end_turn.GetEndTurnInputBoundary;
import com.example.scrabble.use_case.get_leaderboard.GetLeaderboardInputBoundary;
import com.example.scrabble.use_case.place_letter.PlaceLetterInputBoundary;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GameController.class)
public class GameControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private GameDataAccess gameDao;

  @MockBean
  private CreateGameInputBoundary createGameInteractor;

  @MockBean
  private PlaceLetterInputBoundary placeLetterInteractor;

  @MockBean
  private ConfirmPlayInputBoundary confirmPlayInteractor;

  @MockBean
  private GetEndTurnInputBoundary getEndTurnInteractor;

  @MockBean
  private GetLeaderboardInputBoundary getLeaderboardInteractor;

  @MockBean
  private ContestInteractor contestInteractor;

  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    objectMapper = new ObjectMapper();
  }

  @Test
  void testCreateGame() throws Exception {
    CreateGameInputData inputData = new CreateGameInputData(Arrays.asList("Player1", "Player2"));
    Game game = new Game(); // Assume Game has a no-args constructor

    when(createGameInteractor.execute(any(CreateGameInputData.class))).thenReturn(game);

    mockMvc.perform(post("/game/create/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(inputData)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1));
  }

  @Test
  void testGetGame() throws Exception {
    Game game = new Game();

    when(gameDao.get(1)).thenReturn(game);

    mockMvc.perform(get("/game/1/"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1));
  }

  @Test
  void testPlaceLetter() throws Exception {
    Game game = new Game();

    when(placeLetterInteractor.execute(any())).thenReturn(game);

    mockMvc.perform(post("/game/place_letter/")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"gameId\":1,\"letter\":\"A\",\"x\":0,\"y\":0}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1));
  }

  @Test
  void testConfirmPlay() throws Exception {
    Game game = new Game();

    when(confirmPlayInteractor.execute(any())).thenReturn(game);

    mockMvc.perform(post("/game/confirm_play/")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"gameId\":1}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1));
  }

  @Test
  void testEndTurn() throws Exception {
    Game game = new Game();

    when(getEndTurnInteractor.execute(any())).thenReturn(game);

    mockMvc.perform(post("/game/end_turn/")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"gameId\":1}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1));
  }

  @Test
  void testGetLeaderboard() throws Exception {
    Game game = new Game();

    when(getLeaderboardInteractor.execute(any())).thenReturn(game);

    mockMvc.perform(get("/game/leaderboard/")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"gameId\":1,\"players\":[\"Player1\",\"Player2\"]}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1));
  }

  @Test
  void testContest() throws Exception {
    Game game = new Game();

    when(contestInteractor.execute(any(ContestInputData.class))).thenReturn(game);

    mockMvc.perform(post("/game/contest/")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"gameId\":1,\"playerId\":1}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1));
  }
}