package test.use_case.place_letter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
  @Test
  void testSuccess() {
    Board board = new Board();
    Player player = new Player(0);
    Play play = new Play(player);
    Letter a = new Letter('a', 0);
    Letter b = new Letter('b', 0);
    Letter c = new Letter('c', 0);
    Letter d = new Letter('d', 0);
    int ax = 0;
    int ay = 0;
    int bx = board.getHeight() - 1;
    int by = board.getWidth() - 1;
    int cx = 2;
    int cy = 13;
    int dx = 11;
    int dy = 4;

    PlaceLetterOutputBoundary dummyPresenter = new PlaceLetterOutputBoundary() {

      @Override
      public void prepareSuccessView(PlaceLetterOutputData data) {
        return;
      }

      @Override
      public void prepareFailView(String error) {
        return;
      }
    };

    PlaceLetterOutputBoundary presenter = new PlaceLetterOutputBoundary() {

      @Override
      public void prepareSuccessView(PlaceLetterOutputData data) {
        assertEquals(a, board.getCell(ax, ay));
        assertEquals(b, board.getCell(bx, by));
        assertEquals(c, board.getCell(cx, cy));
        assertEquals(d, board.getCell(dx, dy));
      }

      @Override
      public void prepareFailView(String error) {
        fail();
      }
    };

    PlaceLetterInputBoundary dummyInteractor = new PlaceLetterInteractor(dummyPresenter, board, play);
    dummyInteractor.execute(new PlaceLetterInputData(ax, ay, a));
    dummyInteractor.execute(new PlaceLetterInputData(bx, by, b));
    dummyInteractor.execute(new PlaceLetterInputData(cx, cy, c));

    PlaceLetterInputBoundary interactor = new PlaceLetterInteractor(presenter, board, play);
    interactor.execute(new PlaceLetterInputData(dx, dy, d));
  }
}
