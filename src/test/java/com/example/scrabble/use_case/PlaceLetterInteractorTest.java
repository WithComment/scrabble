package com.example.scrabble.use_case;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyChar;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.*;
import com.example.scrabble.use_case.place_letter.PlaceLetterInputData;
import com.example.scrabble.use_case.place_letter.PlaceLetterInteractor;
import com.example.scrabble.use_case.place_letter.PlaceLetterOutputData;

public class PlaceLetterInteractorTest {

  @Mock
  private GameDataAccess gameDao;

  @InjectMocks
  private PlaceLetterInteractor interactor;

  private static final Letter a = new Letter('a', 1);
  private static final Letter b = new Letter('b', 3);
  private Game game;
  private Board board;
  private Player player;
  private Play play;

  @Mock
  private Tile tile;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    game = new Game(2);
    board = game.getBoard();
    game.startGame();
    player = game.getCurrentPlayer();
    play = game.getCurrentPlay();
    player.addLetter(a);

    when(gameDao.get(anyInt())).thenReturn(game);
  }

  private void addMoves() {
    for (Move move : play.getMoves()) {
      board.setCell(move.getX(), move.getY(), move.getLetter());
    }
  }

  private void testForSuccess(PlaceLetterInputData inputData, int expectedScore, List<String> expectedWords) {
    PlaceLetterOutputData actual = interactor.execute(inputData);
    assertIterableEquals(expectedWords, actual.getWords());
    assertEquals(expectedScore, actual.getTempScore());
    verify(gameDao, times(1)).update(game);
  }

  private void setAndConfirm(int x, int y, Letter letter) {
    board.setCell(x, y, letter);
    board.confirm(x, y);
  }

  @Test
  void testGetWords() {
    setAndConfirm(5, 2, a);
    setAndConfirm(6, 2, b);
    setAndConfirm(6, 3, b);
    setAndConfirm(8, 3, b);
    setAndConfirm(8, 4, b);
    setAndConfirm(9, 4, a);

    for (int i = 2; i < 7; i++) {
      play.addMove(new Move(7, i, a));
    }
    addMoves();
    List<String> expected = Arrays.asList("aaaaaa", "aba", "bab", "aba");
    testForSuccess(new PlaceLetterInputData(1, 7, 7, a.getLetter()), 35, expected);
  }

  @Test
  void testCalcScore() {
    for (int i = 1; i < 7; i++) {
      play.addMove(new Move(7, i, a));
    };
    addMoves();
    testForSuccess(new PlaceLetterInputData(1, 7, 7, a.getLetter()), 66, Arrays.asList("aaaaaaa"));
  }

  private void testForFailure(PlaceLetterInputData inputData, String expectedMsg) {
    InvalidPlayException ex = assertThrows(InvalidPlayException.class, () -> {
      interactor.execute(inputData);
    });
    assertEquals(expectedMsg, ex.getMessage());
    verify(gameDao, never()).update(game);
  }

  @Test
  public void testOccupied() {
    board.setCell(1, 1, a);
    testForFailure(
      new PlaceLetterInputData(1, 1, 1, b.getLetter()),
      PlaceLetterInteractor.OCCUPIED
    );
  }

  @Test
  public void testNoLetter() {
    testForFailure(
      new PlaceLetterInputData(1, 1, 1, 'Z'),
      PlaceLetterInteractor.NO_LETTER
    );
  }

  @Test
  public void testSuccessSimple() {
    testForSuccess(
      new PlaceLetterInputData(1, 0, 0, a.getLetter()),
      3, Arrays.asList("a")
    );
  }
}