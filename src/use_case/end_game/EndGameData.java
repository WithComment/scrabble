package use_case.end_game;

import entity.Game;

public class EndGameData {
    private Game game;

    public EndGameData(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
}
