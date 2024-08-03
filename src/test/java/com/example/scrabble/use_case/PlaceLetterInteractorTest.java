package com.example.scrabble.use_case;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.*;
import com.example.scrabble.use_case.place_letter.PlaceLetterInputData;
import com.example.scrabble.use_case.place_letter.PlaceLetterInteractor;

public class PlaceLetterInteractorTest {

  @Mock
  private GameDataAccess gameDao;

  @Mock
  private Game game;

  @Mock
  private Board board;

  @Mock
  private Player currentPlayer;

  @Mock
  private Play play;

  @Mock
  private Tile tile;

  @Mock
  private Letter A;

  @Mock
  private Letter B;

  @InjectMocks
  private PlaceLetterInteractor interactor;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    when(gameDao.get(anyInt())).thenReturn(game);
    when(game.getBoard()).thenReturn(board);
    when(game.getCurrentPlay()).thenReturn(play);
    when(game.getCurrentPlayer()).thenReturn(currentPlayer);
    when(board.getCell(anyInt(), anyInt())).thenReturn(tile);
    when(A.getLetter()).thenReturn('A');
    when(B.getLetter()).thenReturn('B');
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
    when(tile.isEmpty()).thenReturn(false);
    testForFailure(
      new PlaceLetterInputData(1, 1, 1, B.getLetter()),
      PlaceLetterInteractor.OCCUPIED
    );
  }

  @Test
  public void testNoLetter() {
    when(currentPlayer.getInventory()).thenReturn(Arrays.asList(A));
    when(tile.isEmpty()).thenReturn(true);
    testForFailure(
      new PlaceLetterInputData(1, 1, 1, 'Z'),
      PlaceLetterInteractor.NO_LETTER
    );
  }

  @Test
  public void testSuccess() {
    ArrayList<Letter> inventory = new ArrayList<Letter>(Arrays.asList(A));
    when(currentPlayer.getInventory()).thenReturn(inventory);
    when(tile.isEmpty()).thenReturn(true);

    interactor.execute(new PlaceLetterInputData(1, 1, 1, A.getLetter()));

    verify(currentPlayer).removeLetter(A);
    verify(board).setCell(1, 1, A);
    verify(play).addMove(new Move(1, 1, A));
    verify(gameDao).update(game);
  }
}