package main.java.com.example.scrabble.interface_adapter.end_game;

import entity.Game;
import use_case.end_game.EndGameInputBoundary;
import use_case.end_game.EndGameInputData;

public class EndGameController {
    private EndGameInputBoundary interactor;

    public EndGameController(EndGameInputBoundary interactor) {this.interactor = interactor;}

    public void execute(Game game) throws Exception {
        EndGameInputData endGameInputData = new EndGameInputData(game);
        interactor.execute(endGameInputData);
    }
}
