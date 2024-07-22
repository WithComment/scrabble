package controller_factory;

import interface_adapter.GameViewModel;
import interface_adapter.place_letter.PlaceLetterController;
import interface_adapter.place_letter.PlaceLetterPresenter;
import use_case.place_letter.PlaceLetterInteractor;
import use_case.place_letter.PlaceLetterOutputBoundary;

public class PlaceLetterControllerFactory {
  
  private PlaceLetterControllerFactory() {
    // Prevents instantiation
  }

  public static PlaceLetterController create(GameViewModel gameViewModel) {
    PlaceLetterOutputBoundary presenter = new PlaceLetterPresenter(gameViewModel);
    PlaceLetterInteractor interactor = new PlaceLetterInteractor(presenter);
    return new PlaceLetterController(interactor);
  }
}
