package test.use_case.confirm_play;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ConfirmPlayInteractorTest {
    private static final Letter a = new Letter('a', 1);
    private Board board;
    private Player player = new Player(0);
    private Play play;

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

    private ConfirmPlayOutputBoundary getFailureTester(int expectedConfirmed, int expectedScore, String msg) {
        return new Tester(board, expectedConfirmed, expectedScore) {
            @Override
            public void prepareSuccessView(ConfirmPlayOutputData data) {
                fail("Success is not expected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals(msg, error);
                assert (checkConfirmed());
                assert (checkScore());
            }
        };
    }

    private ConfirmPlayOutputBoundary getSuccessTester(int expectedConfirmed, int expectedScore) {
        return new Tester(board, expectedConfirmed, expectedScore) {
            @Override
            public void prepareSuccessView(ConfirmPlayOutputData data) {
                assert (checkConfirmed());
                assert (checkScore());
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
        play.addMove(new Move(0, 1, a));
        play.addMove(new Move(1, 1, a));
        addMoves();
        ConfirmPlayOutputBoundary tester = getFailureTester(0, 0, ConfirmPlayInteractor.INLINE_MSG);
        ConfirmPlayInteractor interactor = new ConfirmPlayInteractor(tester);
        interactor.execute(new ConfirmPlayInputData(play, board, false));
    }

    @Test
    void testHasGap() {
        play.addMove(new Move(0, 0, a));
        play.addMove(new Move(0, 2, a));
        addMoves();
        ConfirmPlayOutputBoundary tester = getFailureTester(0, 0, ConfirmPlayInteractor.CONTINUOUS_MSG);
        ConfirmPlayInteractor interactor = new ConfirmPlayInteractor(tester);
        interactor.execute(new ConfirmPlayInputData(play, board, false));
    }

    @Test
    void testFirstPlayNotCenter() {
        play.addMove(new Move(0, 0, a));
        addMoves();
        ConfirmPlayOutputBoundary tester = getFailureTester(0, 0, ConfirmPlayInteractor.CENTER_MSG);
        ConfirmPlayInteractor interactor = new ConfirmPlayInteractor(tester);
        interactor.execute(new ConfirmPlayInputData(play, board, true));
    }

    @Test
    void testIsolated() {
        play.addMove(new Move(0, 0, a));
        board.setAndConfirm(7, 7, a);
        ;
        addMoves();
        ConfirmPlayOutputBoundary tester = getFailureTester(1, 0, ConfirmPlayInteractor.CONNECTED_MSG);
        ConfirmPlayInteractor interactor = new ConfirmPlayInteractor(tester);
        interactor.execute(new ConfirmPlayInputData(play, board, false));
    }

    @Test
    void testGetWords() {
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                board.setCell(i, j, new Tile(1, 1, null));
            }
        }
        board.setAndConfirm(5, 2, a);
        board.setAndConfirm(6, 2, a);
        board.setAndConfirm(6, 3, a);
        board.setAndConfirm(8, 3, a);
        board.setAndConfirm(8, 4, a);
        board.setAndConfirm(9, 4, a);
        for (int i = 2; i < 8; i++) {
            play.addMove(new Move(7, i, a));
        }
        addMoves();
        ConfirmPlayOutputBoundary tester = getSuccessTester(12, 15);
        ConfirmPlayInteractor interactor = new ConfirmPlayInteractor(tester);
        interactor.execute(new ConfirmPlayInputData(play, board, false));
    }

    @Test
    void testCalcScore() {
        for (int i = 0; i < 7; i++) {
            play.addMove(new Move(0, i, a));
        }
        ;
        addMoves();
        board.setAndConfirm(1, 0, new Letter('a', 0));
        ConfirmPlayOutputBoundary tester = getSuccessTester(8, 77);
        ConfirmPlayInteractor interactor = new ConfirmPlayInteractor(tester);
        interactor.execute(new ConfirmPlayInputData(play, board, false));
    }

    abstract class Tester implements ConfirmPlayOutputBoundary {
        private final Board board;
        private final int expectedConfirmed;
        private final int expectedScore;

        Tester(Board board, int expectedConfirmed, int expectedScore) {
            this.board = board;
            this.expectedConfirmed = expectedConfirmed;
            this.expectedScore = expectedScore;
        }

        protected boolean checkConfirmed() {
            int confirmed = 0;
            for (int i = 0; i < board.getHeight(); i++) {
                for (int j = 0; j < board.getWidth(); j++) {
                    if (board.isConfirmed(i, j)) {
                        confirmed++;
                    }
                }
            }
            return confirmed == expectedConfirmed;
        }

        protected boolean checkScore() {
            return player.getScore() == expectedScore;
        }
    }
}
