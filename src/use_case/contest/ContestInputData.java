package use_case.contest;

import entity.Game;
import entity.Player;

public class ContestInputData {
    final private Game game;
    final private Player player;

    public ContestInputData(Game game, Player player) {
        this.game = game;
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public Player getPlayer() {
        return player;
    }
}
