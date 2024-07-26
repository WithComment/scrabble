package controller_factory;

import interface_adapter.GameViewModel;
import interface_adapter.remove_piece.RemoveLetterController;
import interface_adapter.remove_piece.RemoveLetterPresenter;
import use_case.remove_letter.RemoveLetterInteractor;
import use_case.remove_letter.RemoveLetterOutputBoundary;

public class RemoveLetterControllerFactory {
    public static RemoveLetterController createRemoveLetterController(GameViewModel viewModel){
        RemoveLetterOutputBoundary presenter = new RemoveLetterPresenter(viewModel);
        RemoveLetterInteractor interactor = new RemoveLetterInteractor(presenter);
        return new RemoveLetterController(interactor);
    }
}
