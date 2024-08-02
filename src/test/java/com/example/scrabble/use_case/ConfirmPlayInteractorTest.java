package com.example.scrabble.use_case;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.*;
import com.example.scrabble.use_case.confirm_play.ConfirmPlayInputData;
import com.example.scrabble.use_case.confirm_play.ConfirmPlayInteractor;

class ConfirmPlayInteractorTest {

  private static final Letter A = new Letter('A', 1);
  private static final Letter B = new Letter('B', 2);

  @Mock
  private GameDataAccess gameDao;

  private ConfirmPlayInteractor interactor;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    interactor = new ConfirmPlayInteractor(gameDao);
  }

  private Game makeGame() {
    Game game = new Game();
    game.addPlayer();
    game.addPlayer();
    game.startTurn();
    return game;
  }

  private void addMoves(Play play, List<Move> moves) {
    for (Move move : moves) {
      play.addMove(move);
    }
  }

  @Test
  void testInvalidFirstPlay() {
    Game game = makeGame();
    when(gameDao.get(anyInt())).thenReturn(game);

    List<Move> moves = List.of(new Move(7, 7, A));
  }

  @Test
  void testValidFirstPlay() {
    Game game = makeGame();
    when(gameDao.get(anyInt())).thenReturn(game);

    List<Move> moves = List.of(new Move(0, 0, new Letter('A', 1)));
    game.getCurrentPlay().setMoves(moves);

    InvalidPlayException exception = assertThrows(InvalidPlayException.class,
        () -> interactor.execute(new ConfirmPlayInputData(1)));
    assertEquals(ConfirmPlayInteractor.CENTER_MSG, exception.getMessage());
  }

  @Test
  void testInvalidPlayNotInline() {
    Game game = mockGame(false);
    when(gameDao.get(anyString())).thenReturn(game);

    List<Move> moves = List.of(
        new Move(0, 0, new Letter('A', 1)),
        new Move(1, 1, new Letter('B', 1)));
    game.getCurrentPlay().setMoves(moves);

    InvalidPlayException exception = assertThrows(InvalidPlayException.class,
        () -> interactor.execute(new ConfirmPlayInputData("game1")));
    assertEquals(ConfirmPlayInteractor.INLINE_MSG, exception.getMessage());
  }

  @Test
  void testInvalidPlayNotContinuous() {
    Game game = mockGame(false);
    when(gameDao.get(anyString())).thenReturn(game);

    List<Move> moves = List.of(
        new Move(0, 0, new Letter('A', 1)),
        new Move(0, 2, new Letter('B', 1)));
    game.getCurrentPlay().setMoves(moves);

    InvalidPlayException exception = assertThrows(InvalidPlayException.class,
        () -> interactor.execute(new ConfirmPlayInputData("game1")));
    assertEquals(ConfirmPlayInteractor.CONTINUOUS_MSG, exception.getMessage());
  }

  @Test
  void testInvalidPlayNotConnected() {
    Game game = mockGame(false);
    when(gameDao.get(anyString())).thenReturn(game);

    List<Move> moves = List.of(
        new Move(5, 5, new Letter('A', 1)),
        new Move(5, 6, new Letter('B', 1)));
    game.getCurrentPlay().setMoves(moves);

    InvalidPlayException exception = assertThrows(InvalidPlayException.class,
        () -> interactor.execute(new ConfirmPlayInputData("game1")));
    assertEquals(ConfirmPlayInteractor.CONNECTED_MSG, exception.getMessage());
  }
}