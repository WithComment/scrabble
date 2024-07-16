package test.use_case.confirm_play;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entity.Board;
import entity.Letter;
import entity.Move;
import entity.Play;
import entity.Player;
import entity.Tile;
import use_case.confirm_play.ConfirmPlayInputData;
import use_case.confirm_play.ConfirmPlayInteractor;
import use_case.confirm_play.ConfirmPlayOutputBoundary;
import use_case.confirm_play.ConfirmPlayOutputData;

public class ConfirmPlayInteractorTest {
  private Board board;
  private Player player = new Player(0);
  private Play play;
  private static final Letter a = new Letter('a', 1);

  @BeforeEach
  void setUp() {
    board = new Board();
    play = new Play(player);
  }

  private void addMoves() {
    for (Move move : play.getMoves()) {
      board.setCell(move.getX(), move.getY(), move.getLetter());
    }
  }

  private ConfirmPlayOutputBoundary getFailureTester(String msg) {
    return new ConfirmPlayOutputBoundary() {
      @Override
      public void prepareSuccessView(ConfirmPlayOutputData data) {
        fail("Success is not expected.");
      }

      @Override
      public void prepareFailView(String error) {
        assertEquals(msg, error);
      }
    };
  }

  private ConfirmPlayOutputBoundary getSuccessTester(int expectedScore) {
    return new ConfirmPlayOutputBoundary() {
      @Override
      public void prepareSuccessView(ConfirmPlayOutputData data) {
        assertEquals(expectedScore, data.getPlayer().getScore());
      }

      @Override
      public void prepareFailView(String error) {
        fail(error);
      }
    };
  }

  @Test
  void testIsNotInline() {
    play.addMove(new Move(0, 0, a));
    play.addMove(new Move(1, 1, a));
    addMoves();
    ConfirmPlayOutputBoundary tester = getFailureTester(ConfirmPlayInteractor.INLINE_MSG);
    ConfirmPlayInteractor interactor = new ConfirmPlayInteractor(tester);
    interactor.execute(new ConfirmPlayInputData(play, board, false));
  }

  @Test
  void testHasGap() {
    play.addMove(new Move(0, 0, a));
    play.addMove(new Move(0, 2, a));
    addMoves();
    ConfirmPlayOutputBoundary tester = getFailureTester(ConfirmPlayInteractor.CONTINUOUS_MSG);
    ConfirmPlayInteractor interactor = new ConfirmPlayInteractor(tester);
    interactor.execute(new ConfirmPlayInputData(play, board, false));
  }

  @Test
  void testFirstPlayNotCenter() {
    play.addMove(new Move(0, 0, a));
    addMoves();
    ConfirmPlayOutputBoundary tester = getFailureTester(ConfirmPlayInteractor.CENTER_MSG);
    ConfirmPlayInteractor interactor = new ConfirmPlayInteractor(tester);
    interactor.execute(new ConfirmPlayInputData(play, board, true));
  }

  @Test
  void testGetWords() {
    for (int i = 0; i < board.getHeight(); i++) {
      for (int j = 0; j < board.getWidth(); j++) {
        board.setCell(i, j, new Tile(1, 1, null));
      }
    }
    board.setCell(5, 2, a);
    board.setCell(6, 2, a);
    board.setCell(6, 3, a);
    board.setCell(8, 3, a);
    board.setCell(8, 4, a);
    board.setCell(9, 4, a);
    for (int i = 2; i < 8; i++) {
      play.addMove(new Move(7, i, a));
    }
    addMoves();
    ConfirmPlayOutputBoundary tester = getSuccessTester(15);
    ConfirmPlayInteractor interactor = new ConfirmPlayInteractor(tester);
    interactor.execute(new ConfirmPlayInputData(play, board, true));
  }

  @Test
  void testCalcScore() {
    for (int i = 0; i < 7; i++) {
      play.addMove(new Move(0, i, a));
    };
    addMoves();
    ConfirmPlayOutputBoundary tester = getSuccessTester(74);
    ConfirmPlayInteractor interactor = new ConfirmPlayInteractor(tester);
    interactor.execute(new ConfirmPlayInputData(play, board, false));
  }

}
