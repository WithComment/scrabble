package interface_adapter.place_letter;

import entity.Board;
import entity.Letter;
import entity.Play;
import use_case.place_letter.PlaceLetterInputBoundary;
import use_case.place_letter.PlaceLetterInputData;

public class PlaceLetterController {
  private PlaceLetterInputBoundary interactor;

  public PlaceLetterController(PlaceLetterInputBoundary interactor) {
    this.interactor = interactor;
  }

  public void execute(int x, int y, Letter letter, Board board, Play play) {
    PlaceLetterInputData data = new PlaceLetterInputData(x, y, letter, board, play);
    interactor.execute(data);
  }
}
