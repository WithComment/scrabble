package controller_factory;

import entity.Player;
import entity.TurnManager;
import interface_adapter.GameViewModel;
import interface_adapter.end_turn.EndTurnController;
import interface_adapter.end_turn.EndTurnPresenter;
import use_case.EndTurn.GetEndTurnInputBoundary;
import use_case.EndTurn.GetEndTurnOutputBoundary;
import use_case.EndTurn.TurnManagerInputData;
import use_case.EndTurn.TurnManagerInteractor;

import java.util.ArrayList;

public class EndTurnControllerFactory {
    private EndTurnControllerFactory() {
        // This class should not be instantiated
    }

    public static EndTurnController create(GameViewModel viewModel, TurnManager turnManager) {
        GetEndTurnOutputBoundary presenter = new EndTurnPresenter(viewModel);
        TurnManagerInteractor interactor = new TurnManagerInteractor(turnManager);
        return new EndTurnController(interactor);
    }
}
