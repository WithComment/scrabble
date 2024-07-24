package controller_factory;

import interface_adapter.GameOverViewModel;
import interface_adapter.get_winner.*;
import use_case.get_winner.*;

public class GetWinnerControllerFactory {

    private GetWinnerControllerFactory() {
        // This class should not be instantiated
    }

    public static GetWinnerController create(GameOverViewModel viewModel) {
        GetWinnerOutputBoundary presenter = new GetWinnerPresenter(viewModel);
        GetWinnerInteractor interactor = new GetWinnerInteractor(presenter);
        return new GetWinnerController(interactor);
    }

}

