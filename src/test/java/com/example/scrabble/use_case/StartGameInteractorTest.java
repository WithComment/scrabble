package com.example.scrabble.use_case;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.Game;
import com.example.scrabble.use_case.start_game.StartGameInputData;
import com.example.scrabble.use_case.start_game.StartGameInteractor;

public class StartGameInteractorTest {

  @Mock
  private GameDataAccess gameDataAccess;
  
  @InjectMocks
  public StartGameInteractor startGameInteractor;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testStartGame() {
    Game game = mock(Game.class);
    when(gameDataAccess.get(anyInt())).thenReturn(game);

    startGameInteractor.execute(new StartGameInputData(1));

    verify(game).startGame();
    verify(gameDataAccess).update(game);
  }
}
