import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
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

  @Mock
  private GameDataAccess gameDao;

  private ConfirmPlayInteractor interactor;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    interactor = new ConfirmPlayInteractor(gameDao);
  }

  @Test
  void testValidFirstPlay() {
    
  }

  @Test
  void testInvalidFirstPlayNotCoveredCenter() {
    Game game = mockGame(true);
    when(gameDao.get(anyString())).thenReturn(game);

    List<Move> moves = List.of(new Move(0, 0, new Letter('A', 1)));
    game.getCurrentPlay().setMoves(moves);

    InvalidPlayException exception = assertThrows(InvalidPlayException.class,
        () -> interactor.execute(new ConfirmPlayInputData("game1")));
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

  private Game mockGame(boolean isFirstPlay) {
    Game game = mock(Game.class);
    Board board = mock(Board.class);
    Play play = mock(Play.class);
    Player player = mock(Player.class);

    when(game.getBoard()).thenReturn(board);
    when(game.getCurrentPlay()).thenReturn(play);
    when(game.getCurrentPlayer()).thenReturn(player);

    return game;
  }
}