package com.example.scrabble.controller_factory;

import com.example.scrabble.entity.Game;
import com.example.scrabble.entity.TurnManager;
import com.example.scrabble.interface_adapter.end_turn.EndTurnController;
import com.example.scrabble.interface_adapter.end_turn.EndTurnPresenter;
import com.example.scrabble.use_case.end_turn.EndTurnInteractor;
import com.example.scrabble.use_case.end_turn.GetEndTurnOutputBoundary;
import com.example.scrabble.data_access.GameDataAccess;
//import interface_adapter.GameViewModel;

import java.io.FileNotFoundException;
import java.io.IOException;

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
    public static EndTurnController create(TurnManager turnManager) {
        GetEndTurnOutputBoundary presenter = new EndTurnPresenter();
        EndTurnInteractor endTurnInteractor = new EndTurnInteractor(new GameDataAccess() {
            @Override
            public Game create(Game game) throws FileNotFoundException, IOException {
                return null;
            }

            @Override
            public Game get(int gameId) throws FileNotFoundException, IOException, ClassNotFoundException {
                return null;
            }

            @Override
            public void update(Game game) throws FileNotFoundException, IOException {

            }

            @Override
            public void delete(int gameId) throws FileNotFoundException, IOException {

            }
        });
        return new EndTurnController(endTurnInteractor);
    }
}
