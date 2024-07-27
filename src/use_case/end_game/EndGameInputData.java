package use_case.end_game;

import entity.Game;

public class EndGameInputData {
    private final Game game;

    public EndGameInputData(Game game) {
        this.game = game;
    }

    Game getGame() {
        return game;
    }

}
