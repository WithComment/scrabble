package controller_factory;

import interface_adapter.GameViewModel;
import interface_adapter.place_letter.PlaceLetterController;
import interface_adapter.place_letter.PlaceLetterPresenter;
import use_case.place_letter.PlaceLetterInteractor;
import use_case.place_letter.PlaceLetterOutputBoundary;

public class PlaceLetterControllerFactory {

  private static PlaceLetterController instance;
  
  private PlaceLetterControllerFactory() {
    // Prevents instantiation
  }

  public static PlaceLetterController get(GameViewModel viewModel) {
    if (instance == null) {
      PlaceLetterOutputBoundary presenter = new PlaceLetterPresenter(viewModel);
      PlaceLetterInteractor interactor = new PlaceLetterInteractor(presenter);
      instance = new PlaceLetterController(interactor);
    }
    return instance;
  }
}
