package use_case.contest;

import entity.Game;

public class ContestOutputData {
    private final Game game;

    public ContestOutputData(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return this.game;
    }
}
