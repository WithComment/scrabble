package test.use_case.place_letter;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import entity.Board;
import entity.Letter;
import use_case.place_letter.PlaceLetterOutputBoundary;
import use_case.place_letter.PlaceLetterOutputData;

public class TestPresenter implements PlaceLetterOutputBoundary {
  private final int x, y;
  private final Letter l;
  private final Board board;

  public TestPresenter(int x, int y, Letter l, Board board) {
    this.x = x;
    this.y = y;
    this.l = l;
    this.board = board;
  }

  @Override
  public void prepareFailView(String error) {
    fail("Failure is not expected");    
  }

  @Override
  public void prepareSuccessView(PlaceLetterOutputData data) {
    assertEquals(board.getCell(x, y), l);
  }
  
}
