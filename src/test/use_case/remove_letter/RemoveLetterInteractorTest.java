package test.use_case.remove_letter;

import entity.*;
import org.junit.jupiter.api.BeforeEach;
import use_case.remove_letter.RemoveLetterInputData;
import use_case.remove_letter.RemoveLetterInteractor;
import use_case.remove_letter.RemoveLetterOutputBoundary;
import use_case.remove_letter.RemoveLetterOutputData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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
    /**
     * Testing success
     */
    @org.junit.jupiter.api.Test
    void testSuccess() {
        RemoveLetterOutputBoundary testerPresenter = new RemoveLetterOutputBoundary() {
            @Override
            public void prepareSuccessView(RemoveLetterOutputData removeLetterOutputData) {
                assertEquals(play.getMoves().size(), 0);
                assertEquals(board.getCell(0, 0).getLetter(), null);
                assertEquals(player.getInventory().get(0).getLetter(), 'A');
            }

            @Override
            public void prepareFailureView(RemoveLetterOutputData removeLetterOutputData) {
                fail("error");
            }
        };

        Letter letter = new Letter('A', 4);
        play.addMove(new Move(0, 0, letter));
        board.setCell(0, 0, letter);
        RemoveLetterInputData testInputData = new RemoveLetterInputData(play, board,0, 0);
        RemoveLetterInteractor testerInteractor = new RemoveLetterInteractor(testerPresenter);
        testerInteractor.execute(testInputData);
    }

    /**
     * Testing failure when the chosen cell is empty
     */
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
        RemoveLetterInputData testInputData = new RemoveLetterInputData(play, board, 0, 0);
        RemoveLetterInteractor testerInteractor = new RemoveLetterInteractor(testerPresenter);
        testerInteractor.execute(testInputData);

    }

    /**
     * Testing failure when the chosen cell has a tile but not one played by the player
     */
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
        RemoveLetterInputData testInputData = new RemoveLetterInputData(play, board, 0, 0);
        RemoveLetterInteractor testerInteractor = new RemoveLetterInteractor(testerPresenter);
        testerInteractor.execute(testInputData);

    }
}
