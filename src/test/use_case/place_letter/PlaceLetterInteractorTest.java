package test.use_case.place_letter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entity.Board;
import entity.Letter;
import entity.Play;
import entity.Player;
import use_case.place_letter.PlaceLetterInputBoundary;
import use_case.place_letter.PlaceLetterInputData;
import use_case.place_letter.PlaceLetterInteractor;
import use_case.place_letter.PlaceLetterOutputBoundary;
import use_case.place_letter.PlaceLetterOutputData;

public class PlaceLetterInteractorTest {
    private static final Letter a = new Letter('a', 0);
    private static final Letter b = new Letter('b', 0);
    private static final Letter c = new Letter('c', 0);
    private static final Letter d = new Letter('d', 0);
    private static final PlaceLetterOutputBoundary dummyPresenter = new PlaceLetterOutputBoundary() {

        @Override
        public void prepareSuccessView(PlaceLetterOutputData data) {
            return;
        }

        @Override
        public void prepareFailView(String error) {
            return;
        }
    };
    private Board board;
    private Player player;
    private Play play;

    @BeforeEach
    void setUp() {
        board = new Board();
        player = new Player(0);
        play = new Play(player);
        player.addLetter(a);
        player.addLetter(b);
        player.addLetter(c);
        player.addLetter(d);
    }

    @Test
    void testSuccess() {
        int ax = 0;
        int ay = 0;
        int bx = board.getHeight() - 1;
        int by = board.getWidth() - 1;
        int cx = 2;
        int cy = 13;
        int dx = 11;
        int dy = 4;
        // TODO: refractor this PlaceLetterInputData to use Move entity
        PlaceLetterOutputBoundary tester = new PlaceLetterOutputBoundary() {

            @Override
            public void prepareSuccessView(PlaceLetterOutputData data) {
                assertEquals(a, board.getCell(ax, ay).getLetter());
                assertEquals(b, board.getCell(bx, by).getLetter());
                assertEquals(c, board.getCell(cx, cy).getLetter());
                assertEquals(d, board.getCell(dx, dy).getLetter());
            }

            @Override
            public void prepareFailView(String error) {
                fail(error);
            }
        };

        PlaceLetterInputBoundary dummyInteractor = new PlaceLetterInteractor(dummyPresenter);
        dummyInteractor.execute(new PlaceLetterInputData(ax, ay, 'a', board, play));
        dummyInteractor.execute(new PlaceLetterInputData(bx, by, 'b', board, play));
        dummyInteractor.execute(new PlaceLetterInputData(cx, cy, 'c', board, play));

        PlaceLetterInputBoundary interactor = new PlaceLetterInteractor(tester);
        interactor.execute(new PlaceLetterInputData(dx, dy, 'd', board, play));
    }

    @Test
    void testOccupiedFailure() {
        PlaceLetterInputBoundary dummyInteractor = new PlaceLetterInteractor(dummyPresenter);
        PlaceLetterOutputBoundary tester = new PlaceLetterOutputBoundary() {
            @Override
            public void prepareSuccessView(PlaceLetterOutputData data) {
                fail();
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("This grid is occupied!", error);
            }
        };
        player.addLetter(a.clone());
        PlaceLetterInputData data = new PlaceLetterInputData(7, 7, 'a', board, play);
        dummyInteractor.execute(data);
        PlaceLetterInputBoundary interactor = new PlaceLetterInteractor(tester);
        interactor.execute(data);
    }

    @Test
    void testNotInInventoryFailure() {
        PlaceLetterInputBoundary dummyInteractor = new PlaceLetterInteractor(dummyPresenter);
        PlaceLetterOutputBoundary tester = new PlaceLetterOutputBoundary() {

            @Override
            public void prepareSuccessView(PlaceLetterOutputData data) {
                fail();
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("You don't have the letter in your inventory!", error);
            }
        };
        dummyInteractor.execute(new PlaceLetterInputData(7, 7, 'a', board, play));
        PlaceLetterInputBoundary interactor = new PlaceLetterInteractor(tester);
        interactor.execute(new PlaceLetterInputData(0, 0, 'a', board, play));
    }
}
