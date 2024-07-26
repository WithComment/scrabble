package controller_factory;

import entity.TurnManager;
import interface_adapter.GameViewModel;
import interface_adapter.end_turn.EndTurnController;
import interface_adapter.end_turn.EndTurnPresenter;
import use_case.EndTurn.EndTurnInteractor;
import use_case.EndTurn.GetEndTurnOutputBoundary;

public class EndTurnControllerFactory {
    private EndTurnControllerFactory() {
        // This class should not be instantiated
    }

    public static EndTurnController create(GameViewModel viewModel, TurnManager turnManager) {
        GetEndTurnOutputBoundary presenter = new EndTurnPresenter(viewModel);
        EndTurnInteractor endTurnInteractor = new EndTurnInteractor(presenter);
        return new EndTurnController(endTurnInteractor);
    }
}
