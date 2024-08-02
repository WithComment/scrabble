package com.example.scrabble.use_case;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.Game;
import com.example.scrabble.entity.Letter;
import com.example.scrabble.use_case.place_letter.PlaceLetterInputData;
import com.example.scrabble.use_case.place_letter.PlaceLetterInteractor;

public class PlaceLetterInteractorTest {

  private static final Letter A = new Letter('A', 1);
  private static final Letter B = new Letter('B', 2);
  
  @Mock
  private GameDataAccess gameDao;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    Game game = new Game(1);
    game.startGame();
    game.getCurrentPlayer().getInventory().clear();
    game.getCurrentPlayer().getInventory().add(A);
    when(gameDao.get(anyInt())).thenReturn(game);
  }

  private void testForFailure(PlaceLetterInputData inputData, String expectedMsg) {
    PlaceLetterInteractor interactor = new PlaceLetterInteractor(gameDao);
    InvalidPlayException ex = assertThrows(InvalidPlayException.class, () -> {
      interactor.execute(inputData);
    });
    assertEquals(expectedMsg, ex.getMessage());
  }

  @Test
  public void testOccupied() {
    gameDao.get(1).getBoard().getCell(1, 1).setLetter(B);
    testForFailure(
      new PlaceLetterInputData(1, 1, 1, B.getLetter()), 
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
  public void testSuccess() {
    PlaceLetterInteractor interactor = new PlaceLetterInteractor(gameDao);
    interactor.execute(new PlaceLetterInputData(1, 1, 1, A.getLetter()));
    verify(gameDao, times(1)).update(any());
    assertEquals(0, gameDao.get(1).getCurrentPlayer().getInventory().size());
    assertEquals(A, gameDao.get(1).getBoard().getCell(1, 1).getLetter());
  }
}
