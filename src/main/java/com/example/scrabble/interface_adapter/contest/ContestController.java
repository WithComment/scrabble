package main.java.com.example.scrabble.interface_adapter.contest;

import entity.Game;
import entity.Player;
import use_case.contest.ContestInputBoundary;
import use_case.contest.ContestInputData;

public class ContestController {
    private ContestInputBoundary interactor;

    public ContestController(ContestInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(Game game, Player player) {
        ContestInputData contestInputData = new ContestInputData(game, player);
        interactor.contest(contestInputData);
    }
}
