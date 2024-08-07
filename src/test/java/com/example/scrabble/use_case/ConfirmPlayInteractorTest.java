package com.example.scrabble.use_case;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.*;
import com.example.scrabble.use_case.confirm_play.ConfirmPlayInputData;
import com.example.scrabble.use_case.confirm_play.ConfirmPlayInteractor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

public class ConfirmPlayInteractorTest {
  private static final Letter a = new Letter('a', 1);

  @Mock
  private Game game;

  @Mock
  private Board board;

  @Mock
  private Player player;

  @Mock
  private Play play;

  @Mock
  private GameDataAccess gameDao;

  @InjectMocks
  private ConfirmPlayInteractor interactor;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    when(gameDao.get(anyInt())).thenReturn(game);
    when(game.getBoard()).thenReturn(board);
    when(board.getHeight()).thenReturn(15);
    when(board.getWidth()).thenReturn(15);
    when(game.getCurrentPlayer()).thenReturn(player);
    when(game.getCurrentPlay()).thenReturn(play);
  }

  private void placeMovesOfPlay() {
    int x, y;
    for (Move move : play.getMoves()) {
      Tile tile = mock(Tile.class);
      when(tile.getLetter()).thenReturn(move.getLetter());
      x = move.getX();
      y = move.getY();
      when(board.getTile(x, y)).thenReturn(tile);
      when(board.isConfirmed(x, y)).thenReturn(false);
    }
  }

  private void testForError(String expectedMsg) {
    ConfirmPlayInteractor interactor = new ConfirmPlayInteractor(gameDao);
    InvalidPlayException ex = assertThrows(InvalidPlayException.class, () -> {
      interactor.execute(new ConfirmPlayInputData(game.getId()));
    });
    assertEquals(expectedMsg, ex.getMessage());
  }

  private void testForSuccess() {
    ConfirmPlayInteractor interactor = new ConfirmPlayInteractor(gameDao);
    assertTrue(interactor.execute(new ConfirmPlayInputData(game.getId())).isValid());
  }

  @Test
  void testIsNotInline() {
    when(play.getMoves()).thenReturn(Arrays.asList(new Move(0, 0, a), new Move(0, 1, a), new Move(1, 1, a)));
    placeMovesOfPlay();
    testForError(ConfirmPlayInteractor.INLINE_MSG);
  }

  @Test
  void testHasGap() {
    Tile tile = mock(Tile.class);
    when(tile.isEmpty()).thenReturn(true);
    when(board.getTile(anyInt(), anyInt())).thenReturn(tile);

    when(play.getMoves()).thenReturn(Arrays.asList(new Move(0, 0, a), new Move(0, 2, a)));
    when(play.isVertical()).thenReturn(true);
    when(board.isConfirmed(7, 7)).thenReturn(true);
    placeMovesOfPlay();
    testForError(ConfirmPlayInteractor.CONTINUOUS_MSG);
  }

  @Test
  void testFirstPlayNotCenter() {
    when(play.getMoves()).thenReturn(Arrays.asList(new Move(0, 0, a)));
    placeMovesOfPlay();
    testForError(ConfirmPlayInteractor.CENTER_MSG);
  }

  @Test
  void testIsolated() {
    when(play.getMoves()).thenReturn(Arrays.asList(new Move(0, 0, a), new Move(0, 1, a)));
    when(game.getHistory()).thenReturn(Arrays.asList(new Play(mock(Player.class))));
    placeMovesOfPlay();
    testForError(ConfirmPlayInteractor.CONNECTED_MSG);
  }

  @Test
  void testValidFirstPlay() {
    when(play.getMoves()).thenReturn(Arrays.asList(new Move(7, 6, a), new Move(7, 7, a)));
  }
}
