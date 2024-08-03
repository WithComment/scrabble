package com.example.scrabble.use_case;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.*;
import com.example.scrabble.use_case.confirm_play.ConfirmPlayInputData;
import com.example.scrabble.use_case.confirm_play.ConfirmPlayInteractor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedList;

public class ConfirmPlayInteractorTest {
  private static final Letter a = new Letter('a', 1);
  private static final Letter b = new Letter('b', 2);
  private Game game;
  private Board board;
  private Player player;
  private Play play;

  @Mock
  private GameDataAccess gameDao;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    game = new Game(1);
    board = game.getBoard();
    game.startGame();
    player = game.getCurrentPlayer();
    play = game.getCurrentPlay();

    when(gameDao.get(anyInt())).thenReturn(game);
  }

  private void addMoves() {
    for (Move move : play.getMoves()) {
      board.setCell(move.getX(), move.getY(), move.getLetter());
    }
  }

  private int getConfirmedCount() {
    int count = 0;
    for (Tile tile : board) {
      if (tile.isConfirmed()) {
        count++;
      }
    }
    return count;
  }

  private void testForError(String expectedMsg) {
    ConfirmPlayInteractor interactor = new ConfirmPlayInteractor(gameDao);
    InvalidPlayException ex = assertThrows(InvalidPlayException.class, () -> {
      interactor.execute(new ConfirmPlayInputData(game.getId()));
    });
    assertEquals(expectedMsg, ex.getMessage());
    verify(gameDao, never()).update(any());
  }

  private void testForSuccess(int expectedConfirmed, int expectedScore) {
    ConfirmPlayInteractor interactor = new ConfirmPlayInteractor(gameDao);
    interactor.execute(new ConfirmPlayInputData(game.getId()));
    assertEquals(expectedConfirmed, getConfirmedCount());
    assertEquals(expectedScore, player.getScore());
    verify(gameDao, times(1)).update(game);
  }

  private void setAndConfirm(int x, int y, Letter letter) {
    board.setCell(x, y, letter);
    board.confirm(x, y);
  }

  @Test
  void testIsNotInline() {
    play.addMove(new Move(0, 0, a));
    play.addMove(new Move(0, 1, a));
    play.addMove(new Move(1, 1, a));
    addMoves();
    testForError(ConfirmPlayInteractor.INLINE_MSG);
  }

  @Test
  void testHasGap() {
    play.addMove(new Move(0, 0, a));
    play.addMove(new Move(0, 2, a));
    addMoves();
    testForError(ConfirmPlayInteractor.CONTINUOUS_MSG);
  }

  @Test
  void testFirstPlayNotCenter() {
    play.addMove(new Move(0, 0, a));
    addMoves();
    testForError(ConfirmPlayInteractor.CENTER_MSG);
  }

  @Test
  void testIsolated() {
    play.addMove(new Move(0, 0, a));
    board.setCell(7, 7, a);
    board.confirm(7, 7);
    addMoves();
    testForError(ConfirmPlayInteractor.CONNECTED_MSG);
  }

  @Test
  void testGetWords() {
    setAndConfirm(5, 2, a);
    setAndConfirm(6, 2, b);
    setAndConfirm(6, 3, b);
    setAndConfirm(8, 3, b);
    setAndConfirm(8, 4, b);
    setAndConfirm(9, 4, a);
    for (int i = 2; i < 8; i++) {
      play.addMove(new Move(7, i, a));
    }
    addMoves();
    testForSuccess(6, 30);
    assertEquals(new LinkedList<String>() {
      {
        add("aaaaaa");
        add("aba");
        add("bab");
        add("aba");
      }
    }, play.getWords());
  }

  @Test
  void testCalcScore() {
    for (int i = 0; i < 7; i++) {
      play.addMove(new Move(0, i, a));
    }
    ;
    addMoves();
    setAndConfirm(1, 0, new Letter('a', 0));
    testForSuccess(1, 77);
  }
}
