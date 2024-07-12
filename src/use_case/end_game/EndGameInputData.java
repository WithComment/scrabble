package use_case.end_game;

import entity.Game;
import entity.Player;

import java.util.ArrayList;

public class EndGameInputData {
    private final Game game;
    private final ArrayList<Player> players;

    public EndGameInputData(Game game, ArrayList<Player> players) {
        this.game = game;
        this.players = players;
    }

    Game getGame() {
        return game;
    }
    ArrayList<Player> getPlayers() {
        return players;
    }

}
