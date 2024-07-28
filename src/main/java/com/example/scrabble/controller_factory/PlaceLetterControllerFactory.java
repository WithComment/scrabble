package main.java.com.example.scrabble.controller_factory;

import interface_adapter.GameViewModel;
import interface_adapter.place_letter.PlaceLetterController;
import interface_adapter.place_letter.PlaceLetterPresenter;
import use_case.place_letter.PlaceLetterInteractor;
import use_case.place_letter.PlaceLetterOutputBoundary;

/**
 * Factory class for creating instances of PlaceLetterController.
 * This class provides a static method to create a PlaceLetterController
 * with the necessary dependencies.
 */
public class PlaceLetterControllerFactory {
  
  private PlaceLetterControllerFactory() {
    // Prevents instantiation
  }

  /**
   * Creates a PlaceLetterController with the specified GameViewModel.
   * @param gameViewModel The GameViewModel to be used by the presenter.
   * @return A new instance of PlaceLetterController.
   */
  public static PlaceLetterController create(GameViewModel gameViewModel) {
    PlaceLetterOutputBoundary presenter = new PlaceLetterPresenter(gameViewModel);
    PlaceLetterInteractor interactor = new PlaceLetterInteractor(presenter);
    return new PlaceLetterController(interactor);
  }
}
