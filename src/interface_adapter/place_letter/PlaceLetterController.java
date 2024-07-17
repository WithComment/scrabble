package interface_adapter.place_letter;

import entity.Letter;
import use_case.place_letter.PlaceLetterInputBoundary;
import use_case.place_letter.PlaceLetterInputData;

public class PlaceLetterController {
  private PlaceLetterInputBoundary interactor;

  public PlaceLetterController(PlaceLetterInputBoundary interactor) {
    this.interactor = interactor;
  }

  public void execute(int x, int y, Letter letter) {
    PlaceLetterInputData data = new PlaceLetterInputData(x, y, letter);
    interactor.execute(data);
  }
}
