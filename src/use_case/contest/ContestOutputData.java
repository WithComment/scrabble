package use_case.contest;

import entity.Game;

public class ContestOutputData {
    private final Game game;
    private final boolean success;

    public ContestOutputData(Game game, boolean success) {
        this.game = game;
        this.success = success;
    }

    public Game getGame() {
        return this.game;
    }

    public boolean isSuccess() {
        return this.success;
    }
}
