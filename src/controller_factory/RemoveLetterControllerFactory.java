package controller_factory;

import interface_adapter.GameViewModel;
import interface_adapter.remove_piece.RemoveLetterController;
import interface_adapter.remove_piece.RemoveLetterPresenter;
import use_case.remove_letter.RemoveLetterInteractor;
import use_case.remove_letter.RemoveLetterOutputBoundary;

/**
 * Factory class for creating instances of RemoveLetterController.
 * This class provides a static method to create a RemoveLetterController
 * with the necessary dependencies.
 */
public class RemoveLetterControllerFactory {

    /**
     * Creates a RemoveLetterController with the specified GameViewModel.
     * @param viewModel The GameViewModel to be used by the presenter.
     * @return A new instance of RemoveLetterController.
     */
    public static RemoveLetterController createRemoveLetterController(GameViewModel viewModel){
        RemoveLetterOutputBoundary presenter = new RemoveLetterPresenter(viewModel);
        RemoveLetterInteractor interactor = new RemoveLetterInteractor(presenter);
        return new RemoveLetterController(interactor);
    }
}
