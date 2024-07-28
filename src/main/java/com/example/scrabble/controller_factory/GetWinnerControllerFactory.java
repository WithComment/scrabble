package main.java.com.example.scrabble.controller_factory;

import interface_adapter.GameOverViewModel;
import interface_adapter.get_winner.*;
import use_case.get_winner.*;

/**
 * Factory class for creating instances of GetWinnerController.
 * This class provides a static method to create a GetWinnerController
 * with the necessary dependencies.
 */
public class GetWinnerControllerFactory {

    private GetWinnerControllerFactory() {
        // This class should not be instantiated
    }

    /**
     * Creates a GetWinnerController with the specified GameOverViewModel.
     * @param viewModel The GameOverViewModel to be used by the presenter.
     * @return A new instance of GetWinnerController.
     */
    public static GetWinnerController create(GameOverViewModel viewModel) {
        GetWinnerOutputBoundary presenter = new GetWinnerPresenter(viewModel);
        GetWinnerInteractor interactor = new GetWinnerInteractor(presenter);
        return new GetWinnerController(interactor);
    }

}

