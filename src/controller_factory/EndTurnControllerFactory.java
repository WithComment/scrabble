package controller_factory;

import entity.TurnManager;
import interface_adapter.GameViewModel;
import interface_adapter.end_turn.EndTurnController;
import interface_adapter.end_turn.EndTurnPresenter;
import use_case.EndTurn.EndTurnInteractor;
import use_case.EndTurn.GetEndTurnOutputBoundary;

/**
 * Factory class for creating instances of EndTurnController.
 * This class provides a static method to create an EndTurnController
 * with the necessary dependencies.
 */
public class EndTurnControllerFactory {
    private EndTurnControllerFactory() {
        // This class should not be instantiated
    }

    /**
     * Creates an EndTurnController with the specified GameViewModel and TurnManager.
     * @param viewModel The GameViewModel to be used by the presenter.
     * @param turnManager The TurnManager to be used by the interactor.
     * @return A new instance of EndTurnController.
     */
    public static EndTurnController create(GameViewModel viewModel, TurnManager turnManager) {
        GetEndTurnOutputBoundary presenter = new EndTurnPresenter(viewModel);
        EndTurnInteractor endTurnInteractor = new EndTurnInteractor(presenter);
        return new EndTurnController(endTurnInteractor);
    }
}
