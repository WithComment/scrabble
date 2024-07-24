package use_case.end_game;

import entity.Player;

import java.util.ArrayList;

public class EndGameOutputData {
    private ArrayList<Player> winners;

    public EndGameOutputData(ArrayList<Player> winners) {
        this.winners = winners;
    }

    public ArrayList<Player> getWinners() {
        return winners;
    }
}
