package use_case.end_game;

import entity.Player;

public class EndGameOutputData {
    private Player winner;

    public EndGameOutputData(Player winner) {
        this.winner = winner;
    }
}
