package test.use_case.remove_letter;

import entity.*;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import use_case.remove_letter.RemoveLetterInputData;
import use_case.remove_letter.RemoveLetterInteractor;
import use_case.remove_letter.RemoveLetterOutputBoundary;
import use_case.remove_letter.RemoveLetterOutputData;

class RemoveLetterInteractorTest {
    public Player player;
    public Board board;
    public Play play;

    @BeforeEach
    void setUp() {
        player = new Player(0);
        board = new Board();
        play = new Play(player);
    }

    @org.junit.jupiter.api.Test
    void testSuccess() {
        RemoveLetterOutputBoundary testerPresenter = new RemoveLetterOutputBoundary() {
            @Override
            public void prepareSuccessView(RemoveLetterOutputData removeLetterOutputData) {
                Assert.assertEquals(play.getMoves().size(), 0);
                Assert.assertEquals(board.getCell(0, 0).getLetter(), null);
                Assert.assertEquals(player.getInventory().get(0).getLetter(), 'A');
            }

            @Override
            public void prepareFailureView(RemoveLetterOutputData removeLetterOutputData) {
                fail("error");
            }
        };

        Letter letter = new Letter('A', 4);
        play.addMove(new Move(0, 0, letter));
        board.setCell(0, 0, letter);
        Tile selectedtile = board.getCell(0, 0);
        RemoveLetterInputData testInputData = new RemoveLetterInputData(play, board, selectedtile, 0, 0);
        RemoveLetterInteractor testerInteractor = new RemoveLetterInteractor(testerPresenter);
        testerInteractor.execute(testInputData);
    }

    @org.junit.jupiter.api.Test
    void testEmptyCellFailure() {
        RemoveLetterOutputBoundary testerPresenter = new RemoveLetterOutputBoundary() {
            @Override
            public void prepareSuccessView(RemoveLetterOutputData removeLetterOutputData) {
                fail("error");
            }

            @Override
            public void prepareFailureView(RemoveLetterOutputData removeLetterOutputData) {
                assertEquals(removeLetterOutputData.isRemoveSuccessful(), false);
            }
        };
        Tile selectedtile = board.getCell(0, 0);
        RemoveLetterInputData testInputData = new RemoveLetterInputData(play, board, selectedtile, 0, 0);
        RemoveLetterInteractor testerInteractor = new RemoveLetterInteractor(testerPresenter);
        testerInteractor.execute(testInputData);

    }

    @org.junit.jupiter.api.Test
    void testNotPlayedFailure() {
        RemoveLetterOutputBoundary testerPresenter = new RemoveLetterOutputBoundary() {
            @Override
            public void prepareSuccessView(RemoveLetterOutputData removeLetterOutputData) {
                fail("error");
            }

            @Override
            public void prepareFailureView(RemoveLetterOutputData removeLetterOutputData) {
                assertEquals(removeLetterOutputData.isRemoveSuccessful(), false);
            }
        };
        board.setCell(0, 0, new Letter('A', 4));
        Tile selectedtile = board.getCell(0, 0);
        RemoveLetterInputData testInputData = new RemoveLetterInputData(play, board, selectedtile, 0, 0);
        RemoveLetterInteractor testerInteractor = new RemoveLetterInteractor(testerPresenter);
        testerInteractor.execute(testInputData);

    }
}
