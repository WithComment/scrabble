package com.example.scrabble.use_case;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.*;
import com.example.scrabble.use_case.InvalidPlayException;
import com.example.scrabble.use_case.confirm_play.ConfirmPlayInputData;
import com.example.scrabble.use_case.confirm_play.ConfirmPlayInteractor;

class ConfirmPlayInteractorTest {

  private static final Letter A = new Letter('A', 1);
  private static final Letter B = new Letter('B', 2);

  @Mock
  private GameDataAccess gameDao;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  private void mockGame(List<Move> moves) {
    Game game = mock(Game.class);
    Board board = new Board();
    Play play = mock(Play.class);
    Player player = mock(Player.class);

    when(gameDao.get(anyInt())).thenReturn(game);
    when(game.getBoard()).thenReturn(board);
    when(game.getCurrentPlayer()).thenReturn(player);
    when(game.getCurrentPlay()).thenReturn(play);
    when(play.getMoves()).thenReturn(moves);
  }

  @Test
  void testInvalidFirstMove() {
    List<Move> moves = new ArrayList<>(
        Arrays.asList(new Move(0, 0, A)));

    mockGame(moves);

    ConfirmPlayInputData inputData = new ConfirmPlayInputData(1);
    ConfirmPlayInteractor confirmPlayInteractor = new ConfirmPlayInteractor(gameDao);

    InvalidPlayException exception = assertThrows(InvalidPlayException.class, () -> {
      confirmPlayInteractor.execute(inputData);
    });
    assertEquals(ConfirmPlayInteractor.CENTER_MSG, exception.getMessage());
  }

  @Test
  void testValidFirstMove() {

  }
}